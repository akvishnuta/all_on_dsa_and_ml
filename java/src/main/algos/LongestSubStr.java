import java.util.HashSet;
import java.util.Set;

public class LongestSubStr {

    /**
     * Given a string s, find the length of the longest substring without duplicate characters.
     *
     *
     *
     * Example 1:
     *
     * Input: s = "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3. Note that "bca" and "cab" are also correct answers.
     * Example 2:
     *
     * Input: s = "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * Example 3:
     *
     * Input: s = "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
     *
     *
     * Constraints:
     *
     * 0 <= s.length <= 105
     * s consists of English letters, digits, symbols and spaces.
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if(s.length()==0 || s.length()==1) return s.length();
        int lenOfLong = 0;


        for(int i=0;i<s.length();i++) {
            Set<Character> chars = new HashSet<>();
            chars.add(s.charAt(i));

            for(int j=i+1;j<s.length();j++){
                if(!chars.contains(s.charAt(j))) {
                    chars.add(s.charAt(j));

                    if(chars.size()>lenOfLong){
                        lenOfLong = chars.size();
                    }
                }else{
                    if(chars.size()>lenOfLong){
                        lenOfLong = chars.size();
                    }
                    break;
                }
            }
        }
        return lenOfLong;
    }


    public static void main(String[] args) {
        LongestSubStr longestSubStr = new LongestSubStr();
        System.out.println(longestSubStr.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(longestSubStr.lengthOfLongestSubstring("bbbbb")); // 1
        System.out.println(longestSubStr.lengthOfLongestSubstring("pwwkew")); // 3
    }

//    public int lengthOfLongestSubstring(String s) {
//        if (s == null || s.length() == 0) {
//            return 0;
//        }
//
//        // Tracks the last seen index of each character (ASCII extended)
//        int[] lastSeen = new int[256];
//        // Initialize all indices to -1
//        java.util.Arrays.fill(lastSeen, -1);
//
//        int maxLength = 0;
//        int left = 0; // Left boundary of the sliding window
//
//        // Right boundary expands the window
//        for (int right = 0; right < s.length(); right++) {
//            char currentChar = s.charAt(right);
//
//            // If the character was seen inside the current window, move left boundary
//            if (lastSeen[currentChar] >= left) {
//                left = lastSeen[currentChar] + 1;
//            }
//
//            // Update the last seen position of the character
//            lastSeen[currentChar] = right;
//
//            // Calculate and update the maximum length found so far
//            maxLength = Math.max(maxLength, right - left + 1);
//        }
//
//        return maxLength;
//    }


}
