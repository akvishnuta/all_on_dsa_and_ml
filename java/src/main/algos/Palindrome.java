public class Palindrome {
    public boolean isPalindrome(String s) {
        s=s.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<s.length();i++){

            if((s.charAt(i)>=97 && s.charAt(i)<=122) || (s.charAt(i)>=48 && s.charAt(i)<=57)) sb.append(s.charAt(i));
        }

        if(sb.length()==0) return true;

        boolean result = true;
        for(int i=0,j=sb.length()-1;i<j;i++,j--){

            if(sb.charAt(i)!=sb.charAt(j)){
                result = false;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Palindrome palindrome = new Palindrome();
        System.out.println(palindrome.isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(palindrome.isPalindrome("race a car")); // false
        System.out.println(palindrome.isPalindrome(" ")); // true
    }
}