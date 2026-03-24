package com.github.livreprogramacao.example.App;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void reverse_nullReturnsNull() {
        assertNull(StringUtils.reverse(null));
    }

    @Test
    void reverse_basic() {
        assertEquals("cba", StringUtils.reverse("abc"));
    }

    @Test
    void isPalindrome_nullFalse() {
        assertFalse(StringUtils.isPalindrome(null));
    }

    @Test
    void isPalindrome_emptyTrue() {
        assertTrue(StringUtils.isPalindrome(""));
    }

    @Test
    void isPalindrome_ignoresNonAlphaNumericAndCase() {
        assertTrue(StringUtils.isPalindrome("A man, a plan, a canal: Panama"));
    }

    @Test
    void joinWithDelimiter_works() {
        assertEquals("a|b|c", StringUtils.joinWithDelimiter("|", "a", "b", "c"));
    }

    @Test
    void joinWithDelimiter_emptyParts() {
        assertEquals("", StringUtils.joinWithDelimiter(",", new String[0]));
    }
}
