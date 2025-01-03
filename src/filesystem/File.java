package filesystem;

/**
 * Class representing a File in the File System.
 */
public class File extends Entity{
    protected long size;

    /**
     * Constructor to create a new File.
     *
     * @param fileName  the name of the file (up to 32 characters)
     * @param fileSize  the size of the file (positive long integer)
     * @throws IllegalArgumentException if file name is too long or file size is not positive
     */
    public File(String fileName, long fileSize){
        super(fileName);
        if(fileSize <= 0)
            throw new IllegalArgumentException("File size must be positive");
        this.size = fileSize;
    }

    public long getSize(){
        return size;
    }
}
