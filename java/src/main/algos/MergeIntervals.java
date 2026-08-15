import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
 *
 *
 *
 * Example 1:
 *
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
 * Example 2:
 *
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 * Example 3:
 *
 * Input: intervals = [[4,7],[1,4]]
 * Output: [[1,7]]
 * Explanation: Intervals [1,4] and [4,7] are considered overlapping.
 *
 *
 * Constraints:
 *
 * 1 <= intervals.length <= 104
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 104
 */

public class MergeIntervals {
    public int[][] merge(int[][] intervals) {

        int r = intervals.length;
        if(r==0)
            return new int [0][0];

        // sortArray(intervals, r);
        //Arrays.sort uses timSort with best case O(n log n) and worst case O(n log n) time complexity
        //whereas sortArray uses bubble sort with best case O(n) and worst case O(n^2) time complexity

        Arrays.sort(intervals, (a, b)->Integer.compare(a[0], b[0]));


        List<int[]> newIntervals = new ArrayList<>();


        newIntervals.add(intervals[0]);

        boolean merged = false;
        int j=0;
        for(int i=1; i<r;i++){
            merged = merge(newIntervals.get(j), intervals[i]);
            if (!merged) {
                j++;
                newIntervals.add(intervals[i]);
            }
        }

        return newIntervals.toArray(new int[newIntervals.size()][2]);
    }

    private boolean merge(int[] arr1, int[] arr2){
        if(arr1[1]>=arr2[0]) {
            arr1[1] = arr1[1]> arr2[1] ? arr1[1] : arr2[1];
            return true;
        }
        return false;
    }

    private void sortArray(int[][] intervals, int r) {
        boolean swapped;
        int [] temp = new int[2];

        for(int i=0; i<r-1;i++){
            swapped = false;
            for (int j=0;j<r-1-i;j++) {

                if(intervals[j][0]>intervals[j+1][0]) {
                    temp = intervals[j];
                    intervals[j] = intervals[j+1];
                    intervals[j+1] = temp;
                    swapped = true;
                }
            }

            if(swapped == false) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        MergeIntervals mergeIntervals = new MergeIntervals();
        int [][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int [][] result = mergeIntervals.merge(intervals);
        for(int i=0;i<result.length;i++){
            System.out.println(Arrays.toString(result[i]));
        }
    }

}
