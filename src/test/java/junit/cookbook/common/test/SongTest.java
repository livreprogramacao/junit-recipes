package junit.cookbook.common.test;

import junit.cookbook.common.Song;
import junit.framework.TestCase;

public class SongTest extends TestCase {
    public void testDurationInMinutes() {
        Song song = new Song("Bicyclops", "Fleck", 260);
        assertEquals(4.333333d, song.getDurationInMinutes(), 0.000001d);
    }
}
