package tests;

import filesystem.FileSystem;

import org.junit.Test;
import static org.junit.Assert.*;



public class FileSystemTest{

    @Test
    public void testAddFile(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addDir("", "root");
        fileSystem.addFile("root", "file.txt", 100);
        assertEquals(100, fileSystem.getFileSize("file.txt"));
        assertEquals("file.txt", fileSystem.getBiggestFile());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFileToNonExistentDirectory(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addFile("nonexistent", "file.txt", 100);
    }

    @Test
    public void testAddDirectory(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addDir("", "root");
        fileSystem.addDir("root", "Documents");
        assertNotNull(fileSystem.findDirectory("root\\Documents"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDirectoryToNonExistentParent(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addDir("nonexistent", "Documents");
    }

    @Test
    public void testDeleteFile(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addDir("", "root");
        fileSystem.addFile("root", "example.txt", 100);
        fileSystem.delete("example.txt");
        try {
            fileSystem.getFileSize("example.txt");
            fail("File should have been deleted");
        }
        catch(IllegalArgumentException e){
            // Expected exception
        }
    }

    @Test
    public void testDeleteDirectory(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addDir("", "root");
        fileSystem.addDir("root", "Documents");
        fileSystem.delete("Documents");
        assertNull(fileSystem.findDirectory("root\\Documents"));
    }
}
