import java.util.ArrayList;
import java.awt.Point;

/**
 * SwitchButton
 * 
 * @author Taylor Born
 * @version November 2010 - March 2013
 */
public class SwitchButton extends Button
{
    private ArrayList<String> strs = new ArrayList<String>();
    private int index;

    /**
     * Create a new SwitchButton.
     * @param s The list of texts that the SwitchButton will toggle through to display.
     * @param size The width and height for the SwitchButton.
     * @param startIndex The index for what text will initially be displayed.
     */
    public SwitchButton(ArrayList<String> s, Point size, int startIndex)
    {
        super(s.get(startIndex), size);
        strs = s;
        index = startIndex;
    }
    
    /**
     * The action listener for the SwitchButton.
     * @return Whether the SwitchButton was clicked or not.
     */
    @Override
    public boolean wasClicked()
    {
        if (super.wasClicked())
        {
            index++;
            if (index > strs.size() - 1)
                index = 0;
            setText(strs.get(index));
            return true;
        }
        return false;
    }
    
    /**
     * Get the index for what text is being displayed from the list.
     * @return The index for what text is being displayed from the list.
     */
    public int getIndex()
    {
        return index;
    }
}