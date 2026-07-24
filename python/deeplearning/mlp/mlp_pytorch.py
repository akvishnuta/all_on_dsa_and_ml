from deeplearning.baseline.baseline import DataExtractor
import torch

from sklearn.model_selection import train_test_split
import time

torch.set_default_dtype(torch.float64)


class MLPPyTorch:
    """A from-scratch MLP built with PyTorch tensors (no nn.Module/Linear).

    Mirrors the numpy MLP implementation but uses PyTorch for GPU support
    and autograd-free manual backprop (to stay pedagogically equivalent).
    """

    def __init__(self, architecture, learning_rate=0.01, n_iterations=1000):
        self.architecture = architecture
        self.lr = learning_rate
        self.n_iterations = n_iterations
        self.loss_history = []
        self.device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

    def initialize_parameters(self):
        self.parameters = {}
        # Match numpy version exactly: uniform in [-limit, limit]
        # where limit = 6 / (fan_in + fan_out)
        for i in range(1, len(self.architecture)):
            limit = 6 / (self.architecture[i - 1] + self.architecture[i])
            self.parameters[f"W{i}"] = torch.rand(
                self.architecture[i - 1], self.architecture[i],
                device=self.device,
            ) * (2 * limit) - limit  # uniform in [-limit, limit]

            self.parameters[f"b{i}"] = torch.zeros(
                1, self.architecture[i], device=self.device,
            )

        print("Initial parameters: ")
        for k, v in self.parameters.items():
            print(f"  {k}: shape={v.shape}, device={v.device}")

    @staticmethod
    def relu(Z):
        return torch.maximum(Z * 0.01, Z)

    @staticmethod
    def relu_derivative(Z):
        return torch.where(Z > 0, torch.tensor(1.0), torch.tensor(0.01))

    @staticmethod
    def sigmoid(Z):
        return 1 / (1 + torch.exp(-Z))

    def forward_propagation(self, X):
        self.cache = {"A0": X}
        L = len(self.architecture) - 1

        # Hidden layers — Leaky ReLU
        for i in range(1, L):
            Z = self.cache[f"A{i - 1}"] @ self.parameters[f"W{i}"] + self.parameters[f"b{i}"]
            self.cache[f"Z{i}"] = Z
            self.cache[f"A{i}"] = self.relu(Z)

        # Output layer — Sigmoid
        ZL = self.cache[f"A{L - 1}"] @ self.parameters[f"W{L}"] + self.parameters[f"b{L}"]
        self.cache[f"A{L}"] = self.sigmoid(ZL)

        return self.cache[f"A{L}"]

    def backward_propagation(self, X, y, y_hat):
        m = X.shape[0]
        grads = {}
        L = len(self.architecture) - 1

        # dA = y_hat - y  (derivative of binary cross-entropy w.r.t. Z)
        dA = y_hat - y.reshape(-1, 1)

        for i in reversed(range(1, L + 1)):
            grads[f"dW{i}"] = (1 / m) * self.cache[f"A{i - 1}"].T @ dA
            grads[f"db{i}"] = (1 / m) * dA.sum(dim=0, keepdim=True)

            if i > 1:
                dA = (dA @ self.parameters[f"W{i}"].T) * self.relu_derivative(
                    self.cache[f"Z{i - 1}"]
                )

        return grads

    def fit(self, X, y, enable_early_stopping=True):
        # Convert numpy inputs to PyTorch tensors
        if isinstance(X, torch.Tensor):
            X_t = X.to(self.device)
        else:
            X_t = torch.tensor(X, device=self.device)

        if isinstance(y, torch.Tensor):
            y_t = y.to(self.device).double().reshape(-1, 1)
        else:
            y_t = torch.tensor(y, device=self.device).reshape(-1, 1)

        self.initialize_parameters()

        patience = 10
        min_delta = 1e-5
        best_loss = float("inf")
        patience_counter = 0

        n_samples = X_t.shape[0]

        for epoch in range(self.n_iterations):
            epoch_loss = 0.0

            # Shuffle
            indices = torch.randperm(n_samples, device=self.device)
            X_shuffled = X_t[indices]
            y_shuffled = y_t[indices]

            # Stochastic Gradient Descent — one sample at a time
            for i in range(n_samples):
                x_i = X_shuffled[i].reshape(1, -1)
                y_i = y_shuffled[i].reshape(1, 1)

                y_hat = self.forward_propagation(x_i)

                # Clip to avoid log(0)
                y_hat = torch.clamp(y_hat, 1e-8, 1 - 1e-8)

                loss = -(y_i * torch.log(y_hat) + (1 - y_i) * torch.log(1 - y_hat))
                epoch_loss += loss.item()

                grads = self.backward_propagation(x_i, y_i, y_hat)

                for l in range(1, len(self.architecture)):
                    self.parameters[f"W{l}"] -= self.lr * grads[f"dW{l}"]
                    self.parameters[f"b{l}"] -= self.lr * grads[f"db{l}"]

            epoch_loss /= n_samples
            self.loss_history.append(epoch_loss)

            # Early stopping
            if enable_early_stopping:
                if epoch_loss < best_loss - min_delta:
                    best_loss = epoch_loss
                    patience_counter = 0
                    best_weights = {k: v.clone() for k, v in self.parameters.items()}
                else:
                    patience_counter += 1

                if patience_counter >= patience:
                    print(f"Early stopping at epoch {epoch}  (loss={epoch_loss:.6f})")
                    self.parameters = best_weights
                    break

        return self

    def predict(self, X):
        if isinstance(X, torch.Tensor):
            X_t = X.to(self.device)
        else:
            X_t = torch.tensor(X, device=self.device)

        y_hat = self.forward_propagation(X_t)
        return (y_hat >= 0.5).to(torch.int32)


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
    print(f"Training set size: {X_train.shape[0]} samples, {X_train.shape[1]} features")
    print(f"Architecture: {architecture}")
    print()

    print("Training MLP (PyTorch tensors)...")
    mlp_start = time.time()

    mlp = MLPPyTorch(architecture=architecture)
    mlp.fit(X_train, y_train, enable_early_stopping=True)

    training_time = time.time() - mlp_start

    print(f"\n✓ MLP training completed in {training_time:.2f}s")
    print(f"✓ Loss decreased from {mlp.loss_history[0]:.4f} to {mlp.loss_history[-1]:.4f}")

    y_pred = mlp.predict(X_test)
    # Convert back to numpy for sklearn-style accuracy
    y_pred_np = y_pred.cpu().numpy().ravel()
    y_test_np = y_test.ravel() if hasattr(y_test, "ravel") else y_test

    accuracy = (y_pred_np == y_test_np).mean()
    print(f"✓ Test accuracy: {accuracy:.4f}")


if __name__ == "__main__":
    main()
