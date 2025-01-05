package filesystem;

import java.util.*;

/**
 * Class representing a Directory in the File System.
 */
public class Directory extends Entity{
    protected Map<String, Directory> directories;
    protected Map<String, File> files;

    /**
     * Constructor to create a new Directory.
     *
     * @param dirName the name of the directory (up to 32 characters).
     * @throws IllegalArgumentException if directory name is too long
     */
    public Directory(String dirName){
        super(dirName);
        this.directories = new HashMap<>();
        this.files = new HashMap<>();
    }

    public Map<String, Directory> getDirectories(){
        return this.directories;
    }

    public Map<String, File> getFiles(){
        return this.files;
    }

    public void addFile(String fileName, int fileSize){
        if(files.containsKey(fileName))
            throw new IllegalArgumentException("File already exists");
        File file = new File(fileName, fileSize);
        this.files.put(fileName, file);
    }

    public void addDirectory(String dirName){
        if(directories.containsKey(dirName))
            throw new IllegalArgumentException("Directory already exists");
        Directory directory = new Directory(dirName);
        this.directories.put(dirName, directory);
    }

    public void deleteFile(String fileName){
        this.files.remove(fileName);
    }

    public void deleteDirectory(String dirName){
        this.directories.remove(dirName);
    }

    public Directory getSubDirectory(String dirName){
        return this.directories.get(dirName);
    }

    public File getFile(String fileName){
        return this.files.get(fileName);
    }

    public void show(String indent){
        System.out.println(indent + "Directory: " + getName() + ", Created: " + getCreationDate());
        String newIndent = indent + "\t";

        for(File file : files.values())
            file.show(newIndent);

        for(Directory directory : directories.values())
            directory.show(newIndent);
    }

}
