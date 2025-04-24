package rccookie.data;

/**
 * RuntimeLoadable extends Saveable with the method {@code load(Object)}. This method can be called
 * to set the state of the instance to the loaded one. The loaded file will be the same as the file
 * that would be saved to.
 */
public interface RuntimeLoadable extends Saveable {
    
    /**
     * Adjusts all its parameters to fit the state of the loaded data.
     * <p>There may be a type of object expected, for example a {@code JsonObject}. Therefore, every
     * class that implements this method should also override its description.
     * 
     * @param data The object containing all information about the loaded state
     */
    public void load(Object data);
}
