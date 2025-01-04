package tests;

import filesystem.FileSystem;
import org.junit.Test;
import static org.junit.Assert.*;


public class FileSystemTest {

    @Test
    public void testAddFile(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addDir("", "root");
        fileSystem.addFile("root", "file.txt", 100);
        assertEquals(100, fileSystem.getFileSize("file.txt"));
        assertEquals("file.txt", fileSystem.getBiggestFile());
    }

    @Test
    public void testAddFileToNonExistentDirectory(){
        FileSystem fileSystem = new FileSystem();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileSystem.addFile("nonexistent", "file.txt", 100);
        });
        assertTrue(exception.getMessage().contains("Parent directory 'nonexistent' not found"));
    }

    @Test
    public void testAddDirectory(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addDir("", "root");
        fileSystem.addDir("root", "Documents");
        assertNotNull(fileSystem.findDirectory("root\\Documents"));
    }

    @Test
    public void testAddDirectoryToNonExistentParent(){
        FileSystem fileSystem = new FileSystem();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileSystem.addDir("nonexistent", "Documents");
        });
        assertTrue(exception.getMessage().contains("Parent directory 'nonexistent' not found"));
    }

    @Test
    public void testDeleteFile(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addDir("", "root");
        fileSystem.addFile("root", "file.txt", 100);
        fileSystem.delete("file.txt");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileSystem.getFileSize("file.txt");
        });
        assertTrue(exception.getMessage().contains("File 'file.txt' not found"));
    }

    @Test
    public void testDeleteDirectory(){
        FileSystem fileSystem = new FileSystem();
        fileSystem.addDir("", "root");
        fileSystem.addDir("root", "Documents");
        fileSystem.delete("Documents");
        assertNull(fileSystem.findDirectory("root\\Documents"));
    }

    @Test
    public void testDeleteNonExistentFileOrDirectory(){
        FileSystem fileSystem = new FileSystem();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileSystem.delete("nonExistentEntity");
        });
        assertTrue(exception.getMessage().contains("Directory or File 'nonExistentEntity' not found"));
    }
}
