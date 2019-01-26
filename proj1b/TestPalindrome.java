import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    /*// You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.*/
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String word1 = "cat";
        assertFalse(palindrome.isPalindrome(word1));
        String word2 = "cattac";
        assertTrue(palindrome.isPalindrome(word2));
        String word3 = "noion";
        assertTrue(palindrome.isPalindrome(word3));
        String word4 = "asdfasdf";
        assertFalse(palindrome.isPalindrome(word4));
    }

    @Test
    public void testOffByOneIsPalindrome() {
        CharacterComparator cc = new OffByOne();
        String word1 = "cac";
        assertFalse(palindrome.isPalindrome(word1, cc));
        String word2 = "flake";
        assertTrue(palindrome.isPalindrome(word2, cc));
    }


}
