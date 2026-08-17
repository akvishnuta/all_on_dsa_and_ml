package heap;

import java.util.PriorityQueue;

/**
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0).
 *
 * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
 *
 * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
 *
 *
 *
 * Example 1:
 *
 *
 * Input: points = [[1,3],[-2,2]], k = 1
 * Output: [[-2,2]]
 * Explanation:
 * The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
 * Example 2:
 *
 * Input: points = [[3,3],[5,-1],[-2,4]], k = 2
 * Output: [[3,3],[-2,4]]
 * Explanation: The answer [[-2,4],[3,3]] would also be accepted.
 *
 *
 * Constraints:
 *
 * 1 <= k <= points.length <= 104
 * -104 <= xi, yi <= 104
 */
public class KClosestPoints {
    public int[][] kClosest(int[][] points, int k) {
        int[][] result = new int[k][2];

        //since we want the smaller distances to stay on queue we use (b-a) in the comparator to make it a max heap
        PriorityQueue<int []> heap = new PriorityQueue<>(
                (a,b)-> Double.compare(dist(b),dist(a))
        );

        for(int i=0;i<points.length;i++) {
            heap.add(points[i]);
            if(heap.size()>k) {
                heap.poll();
            }
        }

        for(int i=0;i<k;i++) {
            result[i] = heap.poll();
        }

        return result;
    }

    private double dist(int [] p){
        return Math.sqrt(p[0]*p[0] + p[1]*p[1]);
    }

    public static void main(String[] args) {
        KClosestPoints kClosestPoints = new KClosestPoints();
        int [][] points = {{1,3},{-2,2}};
        int k = 1;
        int [][] result = kClosestPoints.kClosest(points, k);
        for(int i=0;i<result.length;i++) {
            System.out.println("[" + result[i][0] + "," + result[i][1] + "]");
        }

        points = new int[][]{{3,3},{5,-1},{-2,4}};
        k = 2;
        result = kClosestPoints.kClosest(points, k);
        for(int i=0;i<result.length;i++) {
            System.out.println("[" + result[i][0] + "," + result[i][1] + "]");
        }
    }
}
