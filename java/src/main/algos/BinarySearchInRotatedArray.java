/**
 * There is an integer array nums sorted in ascending order (with distinct values).
 *
 * Prior to being passed to your function, nums is possibly left rotated at an unknown index k (1 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,5,6,7] might be left rotated by 3 indices and become [4,5,6,7,0,1,2].
 *
 * Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.
 *
 * You must write an algorithm with O(log n) runtime complexity.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * Example 2:
 *
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 * Example 3:
 *
 * Input: nums = [1], target = 0
 * Output: -1
 */
public class BinarySearchInRotatedArray {
    public int search(int[] nums, int target) {
        int start = 0, end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (nums[mid] == target) return mid;

            // Check if left part is sorted
            if (nums[mid] >= nums[start]) {
                //check if target is in the left part
                if (target >= nums[start] && target < nums[mid]) {
                    end = mid - 1; // move left
                } else {
                    start = mid + 1; // move right
                }
            }
            // Right part is sorted
            else {
                //check if target is in the right part
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1; // move right
                } else {
                    end = mid - 1; // move left
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        BinarySearchInRotatedArray binarySearchRotatedArray = new BinarySearchInRotatedArray();
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        int result = binarySearchRotatedArray.search(nums, target);
        System.out.println(result); // Output: 4

        target = 3;
        result = binarySearchRotatedArray.search(nums, target);
        System.out.println(result); // Output: -1

        nums = new int[]{1};
        target = 0;
        result = binarySearchRotatedArray.search(nums, target);
        System.out.println(result); // Output: -1
    }
}
