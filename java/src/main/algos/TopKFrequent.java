import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,2,2,3], k = 2
 *
 * Output: [1,2]
 *
 * Example 2:
 *
 * Input: nums = [1], k = 1
 *
 * Output: [1]
 *
 * Example 3:
 *
 * Input: nums = [1,2,1,2,1,2,3,1,3,2], k = 2
 *
 * Output: [1,2]
 *
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * k is in the range [1, the number of unique elements in the array].
 * It is guaranteed that the answer is unique.
 *
 *
 * Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class TopKFrequent {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequencies = new HashMap<>();
        for (int num: nums) {
            int currentFreq = frequencies.getOrDefault(num, 0);
            frequencies.put(num, ++currentFreq);
        }
        // System.out.println(frequencies);
        PriorityQueue<Integer> heap = new PriorityQueue<>(
                (a,b)->frequencies.get(a)-frequencies.get(b)
        );

        for(int key: frequencies.keySet()) {
            heap.add(key);
            if(heap.size()>k) {
                heap.poll();
            }
        }


        int [] result = new int[k];
        for(int i=k-1;i>=0;i--) {
            int ele = heap.poll();
            result[i] = ele;
        }

        return result;
    }


    public static void main(String[] args) {
        TopKFrequent topKFrequent = new TopKFrequent();
        int[] nums = {1,1,1,2,2,3};
        int k = 2;
        int[] result = topKFrequent.topKFrequent(nums, k);
        System.out.println(Arrays.toString(result)); // [1, 2]
    }
}

