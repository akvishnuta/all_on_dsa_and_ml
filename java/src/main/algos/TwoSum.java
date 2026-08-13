import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * You are given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * You can return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 * Example 2:
 *
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * Example 3:
 *
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 */

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        int [] result = {-1,-1};
        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0;i<nums.length;i++) {

            int compliment = target-nums[i];

            if(map.containsKey(compliment)) {
                return new int[]{map.get(compliment), i};
            }

            map.put(nums[i], i);

            // for (int j=i+1;j<nums.length;j++){
            //check nums[i]+nums[j] == target
            // if(i!=j) {
            // if (nums[i]+nums[j] == target) {
            //     result[0] = i;
            //     result[1] = j;
            //     break;
            // }
            // }
            // }
            // if(result[0] != -1) {
            //     break;
            // }
        }
        // return result;
        return new int[]{-1,-1};
    }

    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        int [] nums = {2,7,11,15};
        int target = 9;
        int [] result = twoSum.twoSum(nums, target);
        System.out.println(Arrays.toString(result)); // [0,1]
    }
}
