

/**
 * Displays RCS (Reaction Control System) state.
 * 
 * @author Michael Lastufka 
 * @version Dec 2008
 */
public class RCS extends Indicator
{
    /**
     * Constructor for objects of class RCS
     */
    public RCS()
    {
        super(5,555,30,20);
    }

    /**
     * Set the value for this indicator to display.
     * @param linear true when the RCS is in linear mode.
     */
    public void setValue(boolean linear)
    {
        if (linear) 
        {
            setValue("Lin");
            setBackground(Color.BLUE);
        }
        else
        {
            setValue("Rot");
            setBackground(Color.CYAN);
        }
    }

}
