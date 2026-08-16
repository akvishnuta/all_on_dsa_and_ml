package directedacyclicgraph;

import java.util.ArrayList;
import java.util.List;

/**
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 *
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * Return true if you can finish all courses. Otherwise, return false.
 *
 *
 *
 * Example 1:
 *
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: true
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So it is possible.
 * Example 2:
 *
 * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
 * Output: false
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
 *
 *
 * Constraints:
 *
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= 5000
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * All the pairs prerequisites[i] are unique.
 */
public class CourseSchedule {
    private static int NOT_VISITED = 0;
    private static int VISITING = 1;
    private static int VISITED = 2;

    public boolean canFinish(int numCourses, int[][] prerequisites) {



        List<List<Integer>> graph = new ArrayList<>();

        for (int i=0;i<numCourses;i++) {
            graph.add(new ArrayList<>());
        }

        //1. Create prerequisite graph
        for (int[] pre : prerequisites) {
            graph.get(pre[1]).add(pre[0]);
        }


        int[] state = new int[numCourses];

        for(int i=0; i<numCourses; i++) {
            if(hasCycle(i, graph, state)) {
                return false;
            }
        }

        return true;

    }

    private boolean hasCycle(int course, List<List<Integer>> graph, int[] state) {
        if(state[course] == VISITING) {
            return true;
        }

        if(state[course] == VISITED) {
            return false;
        }

        state[course] = VISITING;

        for(int next: graph.get(course)) {
            if(hasCycle(next, graph, state)) {
                return true;
            }
        }

        state[course] = VISITED;


        return false;


    }

    public static void main(String[] args) {
        CourseSchedule courseSchedule = new CourseSchedule();
        int numCourses = 2;
        int[][] prerequisites = {{1,0}};
        boolean result = courseSchedule.canFinish(numCourses, prerequisites);
        System.out.println("result = " + result); //true

        int numCourses2 = 2;
        int[][] prerequisites2 = {{1,0},{0,1}};
        boolean result2 = courseSchedule.canFinish(numCourses2, prerequisites2);
        System.out.println("result2 = " + result2); //false
    }
}
