package dfs.backtracking;

/**
 * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * Output: true
 * Example 2:
 *
 *
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
 * Output: true
 * Example 3:
 *
 *
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
 * Output: false
 */
public class WordSearch {

    public boolean exist(char[][] board, String word) {

        int rows = board.length;
        int cols = board[0].length;

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {

                if(dfs(board, word, r, c, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board,
                        String word,
                        int r,
                        int c,
                        int index) {

        if(index == word.length()) {
            return true;
        }

        if(r < 0 ||
                c < 0 ||
                r >= board.length ||
                c >= board[0].length ||
                board[r][c] != word.charAt(index)) {

            return false;
        }

        char temp = board[r][c];
        board[r][c] = '#';

        boolean found =
                dfs(board, word, r + 1, c, index + 1) ||
                        dfs(board, word, r - 1, c, index + 1) ||
                        dfs(board, word, r, c + 1, index + 1) ||
                        dfs(board, word, r, c - 1, index + 1);

        //this is important in backtracking, we need to restore the original value of the cell after exploring all possible paths
        board[r][c] = temp;

        return found;
    }

    public static void main(String[] args) {
        WordSearch wordSearch = new WordSearch();

        char[][] board = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };

        String word1 = "ABCCED";
        System.out.println("Word: " + word1 + ", Exists: " + wordSearch.exist(board, word1)); // true

        String word2 = "SEE";
        System.out.println("Word: " + word2 + ", Exists: " + wordSearch.exist(board, word2)); // true

        String word3 = "ABCB";
        System.out.println("Word: " + word3 + ", Exists: " + wordSearch.exist(board, word3)); // false
    }
}
