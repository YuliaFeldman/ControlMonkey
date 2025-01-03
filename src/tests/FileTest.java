package tests;

import filesystem.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileTest{

    @Test
    public void testFileCreation(){
        File file = new File("file.txt", 100);
        assertEquals("file.txt", file.getName());
        assertEquals(100, file.getSize());
        assertNotNull(file.getCreationDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileCreationWithLongName(){
        new File("a".repeat(33), 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileCreationWithNegativeSize(){
        new File("file.txt", -1);
    }
}
