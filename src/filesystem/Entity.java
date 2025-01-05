package filesystem;

import java.util.Date;

/**
 * Abstract class representing a generic entity in the File System.
 */
public abstract class Entity{
    protected String name;
    protected Date creationDate;
    protected int MAX_NAME_LENGTH = 32;

    /**
     * Constructor to create a new Entity.
     *
     * @param name the name of the entity (up to 32 characters)
     * @throws IllegalArgumentException if name is too long
     */
    public Entity(String name){
        if(name.length() > MAX_NAME_LENGTH)
            throw new IllegalArgumentException("Name too long");
        this.name = name;
        this.creationDate = new Date();
    }

    public String getName(){
        return name;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public abstract void show(String indent);
}
