public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindrome(deque);
    }

    private boolean isPalindrome(Deque<Character> deque) {
        if (deque.size() == 1 || deque.size() == 0) {
            return true;
        }
        if (deque.removeFirst() != deque.removeLast()) {
            return false;
        } else {
            return isPalindrome(deque);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int start = 0;
        int end = word.length() - 1;
        while (start < end) {
            if (!cc.equalChars(word.charAt(start), word.charAt(end))) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

}
