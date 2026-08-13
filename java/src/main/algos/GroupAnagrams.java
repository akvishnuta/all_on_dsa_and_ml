import java.util.*;

/**
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 *
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 *
 * Explanation:
 *
 * There is no string in strs that can be rearranged to form "bat".
 * The strings "nat" and "tan" are anagrams as they can be rearranged to form each other.
 * The strings "ate", "eat", and "tea" are anagrams as they can be rearranged to form each other.
 * Example 2:
 *
 * Input: strs = [""]
 *
 * Output: [[""]]
 *
 * Example 3:
 *
 * Input: strs = ["a"]
 *
 * Output: [["a"]]
 *
 *
 *
 * Constraints:
 *
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] consists of lowercase English letters.
 */

public class GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {

        List<List<String>> result = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for(int i=0;i<strs.length;i++){
            char[] strChars_i = strs[i].toCharArray();
            Arrays.sort(strChars_i);
            String key = new String(strChars_i);
            List<String> intResult = map.getOrDefault(key, new ArrayList());
            intResult.add(strs[i]);
            map.put(key, intResult);

        }
        for(String key : map.keySet()){
            result.add(map.get(key));
        }
        return result;


        //Brute force
        // List<List<String>> result = new ArrayList<>();
        // Set<Integer> visited = new HashSet<>();
        // for(int i=0;i<strs.length;i++){
        //     if(visited.contains(i)) continue;
        //     List intResult = new ArrayList<>();
        //     intResult.add(strs[i]);
        //     visited.add(i);
        //     for(int j=i+1;j<strs.length;j++){
        //         if(visited.contains(j)) continue;

        //         if(strs[i].length() == strs[j].length()) {
        //             char[] strChars_i = strs[i].toCharArray();
        //             Arrays.sort(strChars_i);
        //             char[] strChars_j = strs[j].toCharArray();
        //             Arrays.sort(strChars_j);
        //             if(Arrays.equals(strChars_i, strChars_j)) {
        //                 intResult.add(strs[j]);
        //                 visited.add(j);
        //             }

        //         }
        //     }
        //     result.add(intResult);
        // }
        // return result;
    }

    public static void main(String[] args) {
        GroupAnagrams groupAnagrams = new GroupAnagrams();
        String[] strs = {"eat","tea","tan","ate","nat","bat"};
        List<List<String>> result = groupAnagrams.groupAnagrams(strs);
        System.out.println(result); // [[eat, tea, ate], [tan, nat], [bat]]
    }
}
