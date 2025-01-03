package tests;

import filesystem.Directory;
import org.junit.Test;
import static org.junit.Assert.*;


public class DirectoryTest{

    @Test
    public void testDirectoryCreation(){
        Directory directory = new Directory("Documents");
        assertEquals("Documents", directory.getName());
        assertNotNull(directory.getCreationDate());
        assertTrue(directory.getDirectories().isEmpty());
        assertTrue(directory.getFiles().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDirectoryCreationWithLongName(){
        new Directory("a".repeat(33));
    }

    @Test
    public void testAddFile(){
        Directory directory = new Directory("Documents");
        directory.addFile("file.txt", 100);
        assertEquals(1, directory.getFiles().size());
        assertNotNull(directory.getFile("file.txt"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddExistingFile() {
        Directory directory = new Directory("Documents");
        directory.addFile("file.txt", 100);
        directory.addFile("file.txt", 200);
    }

    @Test
    public void testAddDirectory(){
        Directory directory = new Directory("Documents");
        directory.addDirectory("Projects");
        assertEquals(1, directory.getDirectories().size());
        assertNotNull(directory.getSubDirectory("Projects"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddExistingDirectory(){
        Directory directory = new Directory("Documents");
        directory.addDirectory("Projects");
        directory.addDirectory("Projects");
    }

    @Test
    public void testDeleteFile(){
        Directory directory = new Directory("Documents");
        directory.addFile("file.txt", 100);
        directory.deleteFile("file.txt");
        assertNull(directory.getFile("file.txt"));
    }

    @Test
    public void testDeleteDirectory(){
        Directory directory = new Directory("Documents");
        directory.addDirectory("Projects");
        directory.deleteDirectory("Projects");
        assertNull(directory.getSubDirectory("Projects"));
    }
}
