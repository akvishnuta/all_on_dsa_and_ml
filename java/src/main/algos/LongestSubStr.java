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
}
