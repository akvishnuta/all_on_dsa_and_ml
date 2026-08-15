package bfs; /**
 * You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls (represented as '+'). You are also given the entrance of the maze, where entrance = [entrancerow, entrancecol] denotes the row and column of the cell you are initially standing at.
 *
 * In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you cannot step outside the maze. Your goal is to find the nearest exit from the entrance. An exit is defined as an empty cell that is at the border of the maze. The entrance does not count as an exit.
 *
 * Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: maze = [["+","+",".","+"],[".",".",".","+"],["+","+","+","."]], entrance = [1,2]
 * Output: 1
 * Explanation: There are 3 exits in this maze at [1,0], [0,2], and [2,3].
 * Initially, you are at the entrance cell [1,2].
 * - You can reach [1,0] by moving 2 steps left.
 * - You can reach [0,2] by moving 1 step up.
 * It is impossible to reach [2,3] from the entrance.
 * Thus, the nearest exit is [0,2], which is 1 step away.
 * Example 2:
 *
 *
 * Input: maze = [["+","+","+"],[".",".","."],["+","+","+"]], entrance = [1,0]
 * Output: 2
 * Explanation: There is 1 exit in this maze at [1,2].
 * [1,0] does not count as an exit since it is the entrance cell.
 * Initially, you are at the entrance cell [1,0].
 * - You can reach [1,2] by moving 2 steps right.
 * Thus, the nearest exit is [1,2], which is 2 steps away.
 * Example 3:
 *
 *
 * Input: maze = [[".","+"]], entrance = [0,0]
 * Output: -1
 * Explanation: There are no exits in this maze.
 *
 *
 * Constraints:
 *
 * maze.length == m
 * maze[i].length == n
 * 1 <= m, n <= 100
 * maze[i][j] is either '.' or '+'.
 * entrance.length == 2
 * 0 <= entrancerow < m
 * 0 <= entrancecol < n
 * entrance will always be an empty cell.
 */
import java.util.LinkedList;
import java.util.Queue;

public class BFSNearestExitFromEntrance {

    public int nearestExit(char[][] maze, int[] entrance) {
        int rows = maze.length;
        int cols = maze[0].length;

        Queue<int []> queue = new LinkedList<>();

        queue.offer(new int[]{entrance[0], entrance[1], 0});

        maze[entrance[0]][entrance[1]] = '+';

        int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};

        while(!queue.isEmpty()){

            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int steps = curr[2];
            if((r==0 || c==0 ||r==rows-1|| c==cols-1) && !(r==entrance[0] && c==entrance[1])) {
                return steps;
            }

            //try moving in all directions

            for(int[] dir : directions) {
                int nr = dir[0] + r;
                int nc = dir[1] + c;

                if(nr>=0 && nc>=0 && nr<rows && nc<cols && maze[nr][nc]=='.') {
                    maze[nr][nc] = '+';
                    queue.offer(new int[]{nr, nc, steps+1});
                }
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        BFSNearestExitFromEntrance bfsNearestExitFromEntrance = new BFSNearestExitFromEntrance();
        char[][] maze = {
                {'+','+','.','+'},
                {'.','.','.','+'},
                {'+','+','+','.'}
        };
        int[] entrance = {1,2};
        int result = bfsNearestExitFromEntrance.nearestExit(maze, entrance);
        System.out.println("result = " + result); // 1


        BFSNearestExitFromEntrance bfsNearestExitFromEntrance2 = new BFSNearestExitFromEntrance();
        char[][] maze2 = {
                {'+','+','+'},
                {'.','.','.'},
                {'+','+','+'}
        };
        int[] entrance2 = {1,0};
        int result2 = bfsNearestExitFromEntrance2.nearestExit(maze2, entrance2);
        System.out.println("result2 = " + result2); // 2
    }
}
