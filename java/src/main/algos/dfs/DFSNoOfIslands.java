package dfs;

public class DFSNoOfIslands {

    /**
     * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
     *
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
     *
     *
     *
     * Example 1:
     *
     * Input: grid = [
     *   ["1","1","1","1","0"],
     *   ["1","1","0","1","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","0","0","0"]
     * ]
     * Output: 1
     * Example 2:
     *
     * Input: grid = [
     *   ["1","1","0","0","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","1","0","0"],
     *   ["0","0","0","1","1"]
     * ]
     * Output: 3
     *
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 300
     * grid[i][j] is '0' or '1'.
     * @param grid
     * @return
     */

    public int numIslands(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;

        for(int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {
                if(grid[i][j]=='1') {
                    islands++;
                    dfs(grid, i, j);
                }
            }
        }

        return islands;
    }

    private void dfs(char[][] grid, int r, int c){
        int rows = grid.length;
        int cols = grid[0].length;
        if(r<0 || c<0 || r>=rows || c>=cols || grid[r][c] == '0'){
            return;
        }
        grid[r][c] = '0';
        dfs(grid, r-1, c);
        dfs(grid, r, c-1);
        dfs(grid, r+1, c);
        dfs(grid, r, c+1);
    }

    public static void main(String[] args) {
        DFSNoOfIslands dfsNoOfIslands = new DFSNoOfIslands();
        char[][] grid = {
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'}
        };
        int result = dfsNoOfIslands.numIslands(grid);
        System.out.println(result); // Output: 1

        grid = new char[][]{
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };
        result = dfsNoOfIslands.numIslands(grid);
        System.out.println(result); // Output: 3
    }
}
