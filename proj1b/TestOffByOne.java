import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        char x1 = 'a';
        char y1 = 'b';
        assertTrue(offByOne.equalChars(x1, y1));
        char x2 = 'c';
        char y2 = 'b';
        assertTrue(offByOne.equalChars(x2, y2));
        char x3 = 'c';
        char y3 = 'c';
        assertFalse(offByOne.equalChars(x3, y3));
        char x4 = 'A';
        char y4 = 'b';
        assertFalse(offByOne.equalChars(x4, y4));
        char x5 = '&';
        char y5 = '%';
        assertTrue(offByOne.equalChars(x5, y5));
    }
}
