public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> ret = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            ret.addLast(word.charAt(i));
        }
        return ret;
    }
// /** Non-recursion version isPalindrome */
//    public boolean isPalindrome(String word) {
//        Deque<Character> dq = wordToDeque(word);
//
//        while (dq.size() > 1) {
//            char front = dq.removeFirst();
//            char back = dq.removeLast();
//            if (front != back) return false;
//        }
//        return true;
//    }

    private boolean isPalindromeHelper(String word, int b, int e) {
        if (b >= e) {
            return true;
        } else {
            return word.charAt(b) == word.charAt(e) && isPalindromeHelper(word, b + 1, e - 1);
        }
    }

    /** Recursion version isPalindrome */
    public boolean isPalindrome(String word) {
        return isPalindromeHelper(word, 0, word.length() - 1);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dq = wordToDeque(word);

        while (dq.size() > 1) {
            char front  = dq.removeFirst();
            char back = dq.removeLast();
            if (!cc.equalChars(front, back)) {
                return false;
            }
        }
        return true;
    }
}
