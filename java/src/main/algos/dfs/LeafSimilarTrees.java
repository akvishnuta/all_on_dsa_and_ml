package dfs;

import helpers.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Consider all the leaves of a binary tree, from left to right order, the values of those leaves form a leaf value sequence.
 *
 *
 *
 * For example, in the given tree above, the leaf value sequence is (6, 7, 4, 9, 8).
 *
 * Two binary trees are considered leaf-similar if their leaf value sequence is the same.
 *
 * Return true if and only if the two given trees with head nodes root1 and root2 are leaf-similar.
 */
public class LeafSimilarTrees {



    private boolean findSimilarLeafTrees(TreeNode root1, TreeNode root2) {
        List<Integer> leaves1 = new ArrayList<>();
        List<Integer> leaves2 = new ArrayList<>();

        dfs(root1, leaves1);
        dfs(root2, leaves2);


        return (!leaves1.isEmpty() && !leaves2.isEmpty() && leaves1.equals(leaves2));
    }

    private void dfs(TreeNode node, List<Integer> leaves){
        if(node==null) return;

        if(node.left == null && node.right == null) leaves.add(node.val);

        if(node.left != null)
            dfs(node.left, leaves);

        if(node.right != null)
            dfs(node.right, leaves);

    }


    public static void main(String[] args) {
        LeafSimilarTrees solution = new LeafSimilarTrees();

        // root1: 3 -> (5 -> 6, 2 -> 7, 4), (1 -> 9, 8)
        // leaves: 6, 7, 4, 9, 8
        TreeNode root1 = new TreeNode(3,
                new TreeNode(5, new TreeNode(6), new TreeNode(2, new TreeNode(7), new TreeNode(4))),
                new TreeNode(1, new TreeNode(9), new TreeNode(8)));

        // root2: 0 -> (2 -> 6, 5 -> 7, 4), (3 -> 9, 8)
        // leaves: 6, 7, 4, 9, 8 (same sequence, different shape)
        TreeNode root2 = new TreeNode(0,
                new TreeNode(2, new TreeNode(6), new TreeNode(5, new TreeNode(7), new TreeNode(4))),
                new TreeNode(3, new TreeNode(9), new TreeNode(8)));

        // root3: 1 -> 2, 3
        // leaves: 2, 3
        TreeNode root3 = new TreeNode(1, new TreeNode(2), new TreeNode(3));

        // root4: single node
        TreeNode root4 = new TreeNode(42);
        TreeNode root5 = new TreeNode(42);
        TreeNode root6 = new TreeNode(7);

        System.out.println("same leaves, different shape (expect true):  " + solution.findSimilarLeafTrees(root1, root2));
        System.out.println("different leaves (expect false):             " + solution.findSimilarLeafTrees(root1, root3));
        System.out.println("different single nodes (expect false):       " + solution.findSimilarLeafTrees(root4, root6));
        System.out.println("same single nodes (expect true):             " + solution.findSimilarLeafTrees(root4, root5));
    }
}
