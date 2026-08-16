package dfs;

/**
 * Given the root of a binary tree, return its maximum depth.
 *
 * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 3
 * Example 2:
 *
 * Input: root = [1,null,2]
 * Output: 2
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [0, 104].
 * -100 <= Node.val <= 100
 */
public class DFSMaxDepthOfBinTree {



    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    int maxDepth = 0;
    public int maxDepth(TreeNode root) {

        if(root == null)
            return 0;

        int depth = 0;
        dfs(root, depth);

        return maxDepth;
    }

    private void dfs(TreeNode node, int depth){
        if(node == null) return;

        depth = depth+1;
        if(depth>maxDepth) {
            maxDepth = depth;
        }
        dfs(node.left, depth);
        dfs(node.right, depth);

    }

    public static void main(String[] args) {

        DFSMaxDepthOfBinTree dfsMaxDepthOfBinTree = new DFSMaxDepthOfBinTree();

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        int result = dfsMaxDepthOfBinTree.maxDepth(root);
        System.out.println(result); // Output: 3
    }
}
