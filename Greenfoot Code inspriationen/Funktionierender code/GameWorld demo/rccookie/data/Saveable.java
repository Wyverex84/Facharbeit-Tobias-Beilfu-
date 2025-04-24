package rccookie.data;


/**
 * Saveables are used as a simple way to save objects using various data saving forms.
 * <p>Every saveable can return all its data that should be stored using {@code getSaveData()}.
 * This data should be saved in the directory specified by {@code getSaveDir()}. The file
 * name of each instance is specified by {@code getSaveName()}.
 */
public interface Saveable {
    
    /**
     * Returns an object that contains all the information about this instance that should
     * be saved.
     * <p>Depending on the type of saving that should be performed with this object, the object
     * returned should be a compatible type. For example, Json expects an object of type
     * {@code Map<?,?>} and will throw an error if this is not the case.
     * 
     * @return The information to be saved
     */
    public Object getSaveData();

    /**
     * Returns a string representing the directory the json save file should be saved to,
     * for example {@code "saves\\"} or {@code "C:\\Desktop"}. The last backslash is optional.
     * <p>This method should propably not be dependent of the instance of the class.
     * 
     * @return The directory the save file should be saved at
     */
    public String getSaveDir();

    /**
     * Returns a string representing the name of the objects save file (without the file ending).
     * <p>This method should return different names for different instances of the same class so
     * that they differ and create multiply save files.
     * 
     * @return The name of the save file
     */
    public String getSaveName();
}
