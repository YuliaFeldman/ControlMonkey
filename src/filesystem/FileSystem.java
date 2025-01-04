package filesystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the File System Manager.
 *
 */
public class FileSystem{

    protected long maxFileSize;
    protected String biggestFileName;
    protected Map<String, Directory> directories;
    protected Map<String, File> files;

    public FileSystem(){
        this.maxFileSize = 0;
        this.biggestFileName = null;
        this.directories = new HashMap<>();
        this.files = new HashMap<>();
    }

    /**
     * Adds a file to the FileSystem.
     *
     * @param parentDirName the name of the parent directory
     * @param fileName the name of the file
     * @param fileSize the size of the file
     * @throws IllegalArgumentException if file name or size is invalid
     *
     * @complexity Time: O(n) where n is the depth of the directory structure
     *            Space: O(1)
     */
    public void addFile(String parentDirName, String fileName, int fileSize){
        if(fileName == null || fileName.isEmpty())
            throw new IllegalArgumentException("File name cannot be null or empty");

        if(fileSize <= 0)
            throw new IllegalArgumentException("File size must be positive");

        File file = new File(fileName, fileSize);

        if(fileSize > maxFileSize){
            maxFileSize = fileSize;
            biggestFileName = fileName;
        }

        if(parentDirName == null || parentDirName.isEmpty()){
            if (this.files.containsKey(fileName))
                throw new IllegalArgumentException("File already exists in the root directory");
            files.put(fileName, file);
        }
        else{
            Directory parentDir = findDirectory(parentDirName);
            if (parentDir != null)
                parentDir.addFile(fileName, fileSize);
            else
                throw new IllegalArgumentException("Parent directory not found");
        }
    }

    /**
     * Adds a directory to the FileSystem.
     *
     * @param parentDirName the name of the parent directory
     * @param dirName the name of the directory
     * @throws IllegalArgumentException if directory name is invalid
     *
     * @complexity Time: O(n) where n is the depth of the directory structure
     *            Space: O(1)
     */
    public void addDir(String parentDirName, String dirName){
        if(dirName == null || dirName.isEmpty())
            throw new IllegalArgumentException("Directory name cannot be null or empty");

        Directory directory = new Directory(dirName);

        if(parentDirName == null || parentDirName.isEmpty()){
            if (directories.containsKey(dirName))
                throw new IllegalArgumentException("Directory already exists in the root directory");
            directories.put(dirName, directory);
        }
        else{
            Directory parentDir = findDirectory(parentDirName);
            if (parentDir != null)
                parentDir.addDirectory(dirName);
            else
                throw new IllegalArgumentException("Parent directory not found");
        }
    }

    /**
     * Finds a directory by path.
     *
     * @param path the path of the directory
     * @return the directory
     *
     * @complexity Time: O(n) where n is the depth of the directory structure
     *            Space: O(1)
     */
    public Directory findDirectory(String path){
        String[] dirNames = path.split("\\\\");
        Map<String, Directory> innerDirectories = this.directories;
        Directory currDir = null;
        for(String dirName : dirNames){
            if (!innerDirectories.containsKey(dirName))
                return null;
            else{
                currDir = innerDirectories.get(dirName);
                innerDirectories = currDir.getDirectories();
            }
        }
        return currDir;
    }

    /**
     * Gets the size of a file.
     *
     * @param fileName the name of the file
     * @return the size of the file
     * @throws IllegalArgumentException if file not found
     *
     * @complexity
     * Time: O(n) where n is the total number of files and directories in the directory tree,
     * because, in the worst case, it has to check each file and directory once to find the target file.
     * Space: O(m) where m is the maximum depth of the directory tree
     */
    public long getFileSize(String fileName){
        if(files.containsKey(fileName))
            return files.get(fileName).getSize();

        for(Directory dir : directories.values()){
            File file = findFile(dir, fileName);
            if(file != null)
                return file.getSize();
        }
        throw new IllegalArgumentException("File not found");
    }

    /**
     * Finds a file in a directory and its subdirectories.
     *
     * @param current the current directory
     * @param fileName the name of the file
     * @return the file if found, otherwise null
     *
     * @complexity Time: O(n) where n is the total number of files and directories in the directory tree,
     * because, in the worst case, it has to check each file and directory once to find the target file.
     * Space: O(m) where m is the maximum depth of the directory tree (since each recursive call adds a new frame to the call stack)
     */
    private File findFile(Directory current, String fileName){
        for(File file : current.getFiles().values()) {
            if(file.getName().equals(fileName))
                return file;
        }
        for(Directory dir : current.getDirectories().values()){
            File found = findFile(dir, fileName);
            if(found != null)
                return found;
        }
        return null;
    }

    /**
     * Gets the name of the biggest file.
     *
     * @return the name of the biggest file
     *
     * @complexity Time: O(1), Space: O(1)
     */
    public String getBiggestFile(){
        return biggestFileName;
    }

    /**
     * Shows the hierarchical structure of the FileSystem.
     *
     * @complexity
     * Time: O(n) where n is the total number of files and directories in the directory structure
     * Space: O(m) where m is the maximum depth of the directory tree.
     */
    public void showFileSystem(){
        for(File file : files.values()){
            System.out.println("File: " + file.getName() + ", Size: " + file.getSize() + ", Created: " + file.getCreationDate());
        }
        for(Directory dir : directories.values()){
            showDirectory(dir, 0);
        }
    }

    /**
     * Shows the hierarchical structure of a directory.
     *
     * @param dir the directory
     * @param level the level of indentation
     *
     * @complexity
     * Time: O(n) where n is the total number of files and directories in the directory structure
     * Space: O(m) where m is the maximum depth of the directory tree.
     */
    private void showDirectory(Directory dir, int level){
        printIndent(level);
        System.out.println("Directory: " + dir.getName() + ", Created: " + dir.getCreationDate());
        for(File file : dir.getFiles().values()){
            printIndent(level + 1);
            System.out.println("File: " + file.getName() + ", Size: " + file.getSize() + ", Created: " + file.getCreationDate());
        }
        for(Directory subDir : dir.getDirectories().values()){
            showDirectory(subDir, level + 1);
        }
    }

    /**
     * Prints the indentation for hierarchical display.
     *
     * @param level the level of indentation
     *
     * @complexity Time: O(level), Space: O(1)
     */
    private void printIndent(int level){
        for(int i = 0; i < level; i++){
            System.out.print("\t");
        }
    }

    /**
     * Deletes a file or directory.
     *
     * @param name the name of the file or directory
     * @throws IllegalArgumentException if file or directory not found
     *
     * @complexity
     * Time: O(n) where n is the total number of files and directories.
     * Space: O(m) where m is the maximum depth of the directory structure
     */
    public void delete(String name){
        boolean foundAndDeleted = false;
        if(files.containsKey(name)){
            files.remove(name);
            foundAndDeleted = true;
        }
        for(Directory dir : directories.values()){
            if(deleteEntity(dir, name)) {
                foundAndDeleted = true;
                break;
            }
        }
        if(directories.containsKey(name)){
            directories.remove(name);
            foundAndDeleted = true;
        }
        if(!foundAndDeleted)
            throw new IllegalArgumentException("Directory or File not found");
        if(name.equals(biggestFileName)) //Each name, file or directory is unique in the file system
            recalculateMaxFileSize();
    }


    /**
     * Deletes an entity (file or directory) recursively.
     *
     * @param current the current directory
     * @param name the name of the entity
     * @return true if the entity was deleted, false otherwise
     *
     * @complexity
     * Time: O(n) where n is the total number of files and directories.
     * Space: O(m) where m is the maximum depth of the directory structure
     */
    private boolean deleteEntity(Directory current, String name){
        boolean deleted = false;
        if(current.getFiles().containsKey(name)){
            current.deleteFile(name);
            deleted = true;
        }
        if(current.getDirectories().containsKey(name)){
            current.deleteDirectory(name);
            deleted = true;
        }
        for(Directory dir : current.getDirectories().values()){
            if (deleteEntity(dir, name))
                deleted = true;
        }
        return deleted;
    }

    /**
     * Recalculates the maximum file size and updates the name of the biggest file.
     *
     * @complexity
     * Time: O(n) where n is the total number of files and directories.
     * Space: O(m) where m is the maximum depth of the directory structure
     */
    private void recalculateMaxFileSize(){
        maxFileSize = 0;
        biggestFileName = null;

        for(File file : files.values())
            updateMaxFileSize(file);

        for(Directory dir : directories.values())
            findNewMaxFile(dir);
    }

    /**
     * Updates the maximum file size and the name of the biggest file based on the specified file.
     *
     * @param file the file to check
     *
     * @complexity Time:O(1), Space: O(1)
     */
    private void updateMaxFileSize(File file){
        if(file.getSize() > maxFileSize){
            maxFileSize = file.getSize();
            biggestFileName = file.getName();
        }
    }

    /**
     * Finds the new maximum file size in a directory and its subdirectories.
     *
     * @param dir the directory to search in
     *
     * @complexity
     * Time: O(n) where n is the total number of files and directories.
     * Space: O(m) where m is the maximum depth of the directory structure
     */
    private void findNewMaxFile(Directory dir){
        for(File file : dir.getFiles().values())
            updateMaxFileSize(file);

        for(Directory subDir : dir.getDirectories().values())
            findNewMaxFile(subDir);
    }
}
