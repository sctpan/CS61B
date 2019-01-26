import org.junit.Test;

import static org.junit.Assert.*;

public class OffByNTest {
    static CharacterComparator offByN = new OffByN(5);

    @Test
    public void testEqualChars() {
        assertTrue(offByN.equalChars('a', 'f'));
    }
}
