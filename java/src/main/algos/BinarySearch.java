/**
 * Given an array of integers nums which is sorted in ascending order, and an integer target, write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.
 *
 * You must write an algorithm with O(log n) runtime complexity.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [-1,0,3,5,9,12], target = 9
 * Output: 4
 * Explanation: 9 exists in nums and its index is 4
 * Example 2:
 *
 * Input: nums = [-1,0,3,5,9,12], target = 2
 * Output: -1
 * Explanation: 2 does not exist in nums so return -1
 */
public class BinarySearch {
    class Solution {
        public int search(int[] nums, int target) {
            int start = 0;
            int end = nums.length-1;
            int mid = end/2;
            // System.out.println(String.format("start = %d, mid = %d, end = %d", start, mid, end));
            while(start<=end) {
                if(nums[mid]==target) {
                    return mid;
                }

                if(target > nums[mid]) {
                    start = mid+1;
                } else {
                    end = mid-1;
                }

                mid = start + (end-start)/2;
                // System.out.println(String.format("start = %d, mid = %d, end = %d", start, mid, end));
            }

            return -1;
        }
    }

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        Solution solution = binarySearch.new Solution();
        int [] nums = {-1,0,3,5,9,12};
        int target = 9;
        int result = solution.search(nums, target);
        System.out.println(result); // 4

        target = 2;
        result = solution.search(nums, target);
        System.out.println(result); // -1
    }
}
