import java.util.Arrays;

/**
 * Meeting Rooms II
 *
 * Given an array of meeting time intervals where:
 *
 * intervals[i] = [starti, endi]
 *
 * return the minimum number of conference rooms required to hold all meetings.
 *
 * Example 1
 * Input:
 * [[0,30],[5,10],[15,20]]
 *
 *
 * Output:
 * 2
 *
 * Explanation:
 *
 * Room 1: [0,30]
 * Room 2: [5,10] -> [15,20]
 *
 * Two rooms are needed.
 *
 * Example 2
 * Input:
 * [[7,10],[2,4]]
 *
 *
 * Output:
 * 1
 *
 * Explanation:
 *
 * [2,4] finishes before [7,10] starts
 *
 * Only one room is needed.
 *
 * Example 3
 * Input:
 * [[1,5],[2,6],[3,7],[4,8]]
 *
 *
 * Output:
 * 4
 *
 * All meetings overlap.
 */
public class MeetingRooms {

    public static int minMeetingRooms(int[][] intervals) {
        int n = intervals.length;
        if (n==0) return 0;

        // 1. Create a start and end arrays

        int[] start = new int[n];
        int[] end = new int[n];

        for(int i=0;i<n;i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }

        //2. sort the arrays
        Arrays.sort(start);
        Arrays.sort(end);

        int rooms = 0;
        int maxRooms = 0;

       //3. check the timeline
        int s=0, e=0;
        while (s<n){
            if(start[s]<end[e]) {
                rooms++;
                s++;
                maxRooms = Math.max(maxRooms, rooms);
            } else {
                rooms--;
                e++;
            }
        }

        return maxRooms;
    }


    public static void main(String[] args) {

        int[][] intervals = {{0,30},{5,10},{15,20}};
        System.out.println(minMeetingRooms(intervals)); // Output: 2

        intervals = new int[][]{{7,10},{2,4}};
        System.out.println(minMeetingRooms(intervals)); // Output: 1

        intervals = new int[][]{{1,5},{2,6},{3,7},{4,8}};
        System.out.println(minMeetingRooms(intervals)); // Output: 4
    }
}
