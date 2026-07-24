from deeplearning.baseline.baseline import DataExtractor
import torch
import torch.nn as nn
import torch.optim as optim
from sklearn.model_selection import train_test_split
import time


class MLPModule(nn.Module):
    """MLP built with torch.nn components — Linear, LeakyReLU, Sigmoid.

    Uses the same architecture and hyperparameters as the numpy version
    but leverages PyTorch's autograd and optimizer infrastructure.
    """

    def __init__(self, architecture):
        super().__init__()
        self.architecture = architecture
        layers = []
        for i in range(1, len(architecture) - 1):
            layers.append(nn.Linear(architecture[i - 1], architecture[i]))
            layers.append(nn.LeakyReLU(negative_slope=0.01))
        # Output layer (no activation here — we use BCEWithLogitsLoss)
        layers.append(nn.Linear(architecture[-2], architecture[-1]))
        # NOTE: Sigmoid is applied in forward() for prediction;
        #       BCEWithLogitsLoss handles it internally during training.
        self.net = nn.Sequential(*layers)

        self._init_parameters()

    def _init_parameters(self):
        """Xavier uniform init matching the numpy version's formula."""
        for name, param in self.named_parameters():
            if "weight" in name:
                fan_in, fan_out = param.shape[1], param.shape[0]
                limit = 6 / (fan_in + fan_out)
                nn.init.uniform_(param, -limit, limit)
            elif "bias" in name:
                nn.init.zeros_(param)

        print("Initial parameters:")
        for name, param in self.named_parameters():
            print(f"  {name}: shape={param.shape}")

    def forward(self, X):
        return self.net(X)

    def predict_proba(self, X):
        """Return probabilities in [0, 1]."""
        if not isinstance(X, torch.Tensor):
            X = torch.tensor(X, dtype=torch.float32)
        return torch.sigmoid(self.forward(X))

    def predict(self, X):
        """Return binary predictions {0, 1}."""
        return (self.predict_proba(X) >= 0.5).to(torch.int32)


def fit_mlp(
    model,
    X,
    y,
    learning_rate=0.01,
    n_iterations=1000,
    batch_size=1,
    enable_early_stopping=True,
    verbose=True,
):
    """Train an MLPModule using autograd + optimizer.

    Uses SGD with the same per-sample (batch_size=1) training loop
    as the numpy reference, or mini-batch for larger workloads.
    """
    # Convert to tensors — match nn.Linear default dtype (float32)
    if not isinstance(X, torch.Tensor):
        X_t = torch.tensor(X, dtype=torch.float32)
    else:
        X_t = X.clone().detach().float()

    if not isinstance(y, torch.Tensor):
        y_t = torch.tensor(y, dtype=torch.float32).reshape(-1, 1)
    else:
        y_t = y.clone().detach().float().reshape(-1, 1)

    model.train()
    criterion = nn.BCEWithLogitsLoss()
    optimizer = optim.SGD(model.parameters(), lr=learning_rate)

    patience = 10
    min_delta = 1e-5
    best_loss = float("inf")
    patience_counter = 0
    loss_history = []

    n_samples = X_t.shape[0]

    for epoch in range(n_iterations):
        epoch_loss = 0.0

        # Shuffle
        indices = torch.randperm(n_samples)
        X_shuffled = X_t[indices]
        y_shuffled = y_t[indices]

        for i in range(0, n_samples, batch_size):
            batch_X = X_shuffled[i : i + batch_size]
            batch_y = y_shuffled[i : i + batch_size]

            optimizer.zero_grad()
            logits = model(batch_X)
            loss = criterion(logits, batch_y)
            loss.backward()
            optimizer.step()

            epoch_loss += loss.item() * batch_X.shape[0]

        epoch_loss /= n_samples
        loss_history.append(epoch_loss)

        # Early stopping
        if enable_early_stopping:
            if epoch_loss < best_loss - min_delta:
                best_loss = epoch_loss
                patience_counter = 0
                best_state = {
                    k: v.clone() for k, v in model.state_dict().items()
                }
            else:
                patience_counter += 1

            if patience_counter >= patience:
                if verbose:
                    print(f"Early stopping at epoch {epoch}  (loss={epoch_loss:.6f})")
                model.load_state_dict(best_state)
                break

    model.loss_history = loss_history
    return model


def main():
    FILE_PATH = "./deeplearning/data/wisconsin_original.csv"
    COLUMN_NAMES = [
        "id",
        "clump_thickness",
        "cell_size_uniformity",
        "cell_shape_uniformity",
        "marginal_adhesion",
        "epithelial_cell_size",
        "bare_nuclei",
        "bland_chromatin",
        "normal_nucleoli",
        "mitoses",
        "class",
    ]
    data_extractor = DataExtractor(FILE_PATH, COLUMN_NAMES)
    df = data_extractor.read_csv()
    X, y = data_extractor.preprocess(df)

    X_train, X_test, y_train, y_test = train_test_split(
        X, y, test_size=0.2, random_state=42
    )

    architecture = [X_train.shape[1], 16, 8, 1]

    print(f"Using device: {'cuda' if torch.cuda.is_available() else 'cpu'}")
    print(f"Training set size: {X_train.shape[0]} samples")
    print(f"Architecture: {architecture}")
    print()

    # --- Train with torch.nn ---
    print("Training MLP (torch.nn + autograd)...")
    torch.manual_seed(42)
    model = MLPModule(architecture)

    mlp_start = time.time()
    model = fit_mlp(
        model,
        X_train,
        y_train,
        learning_rate=0.01,
        batch_size=1,  # pure SGD, same as numpy version
        enable_early_stopping=True,
    )
    training_time = time.time() - mlp_start

    print(f"\n✓ MLP training completed in {training_time:.2f}s")
    print(
        f"✓ Loss decreased from {model.loss_history[0]:.4f} "
        f"to {model.loss_history[-1]:.4f}"
    )

    model.eval()
    with torch.no_grad():
        y_pred = model.predict(X_test)
    y_pred_np = y_pred.numpy().ravel()
    y_test_np = y_test.ravel() if hasattr(y_test, "ravel") else y_test

    accuracy = (y_pred_np == y_test_np).mean()
    print(f"✓ Test accuracy: {accuracy:.4f}")


if __name__ == "__main__":
    main()
