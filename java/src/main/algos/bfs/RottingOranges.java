package bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * You are given an m x n grid where each cell can have one of three values:
 *
 * 0 representing an empty cell,
 * 1 representing a fresh orange, or
 * 2 representing a rotten orange.
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
 *
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 * Example 2:
 *
 * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
 * Example 3:
 *
 * Input: grid = [[0,2]]
 * Output: 0
 * Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 10
 * grid[i][j] is 0, 1, or 2.
 */
public class RottingOranges {

    public int orangesRotting(int[][] grid) {
        Queue<int []> queue = new LinkedList<>();
        //Find entry point
        int rows = grid.length;
        int cols = grid[0].length;
        int mins = 0;
        int fresh = 0;
        for(int r= 0;r<rows;r++) {
            for(int c= 0;c<cols;c++) {
                if(grid[r][c] == 2) {
                    queue.add(new int[]{r,c});
                }

                if(grid[r][c] == 1) {
                    fresh++;
                }
            }
        }

        if(fresh == 0)
            return 0;

        int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};


        while(!queue.isEmpty() && fresh>0) {

            int size = queue.size();

            for(int i=0;i<size;i++) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                //propagation

                for (int[] dir : dirs) {
                    int nr = r+dir[0];
                    int nc = c+dir[1];
                    if(nr<0|| nc<0|| nr>=rows || nc>=cols || grid[nr][nc]!=1) {
                        continue;
                    }


                    grid[nr][nc] = 2;
                    queue.offer(new int[]{nr,nc});
                    fresh--;

                }

            }

            mins++;
        }

        // System.out.println(mins);
        return fresh==0? mins: -1;
    }

    public static void main(String[] args) {
        RottingOranges rottingOranges = new RottingOranges();
        int[][] grid = {{2,1,1},{1,1,0},{0,1,1}};
        int result = rottingOranges.orangesRotting(grid);
        System.out.println("result = " + result); // 4

        int[][] grid2 = {{2,1,1},{0,1,1},{1,0,1}};
        int result2 = rottingOranges.orangesRotting(grid2);
        System.out.println("result2 = " + result2); // -1

        int[][] grid3 = {{0,2}};
        int result3 = rottingOranges.orangesRotting(grid3);
        System.out.println("result3 = " + result3); // 0
    }
}
