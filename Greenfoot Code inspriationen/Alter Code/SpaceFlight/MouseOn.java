

/**
 * Displays MouseOn state.
 * 
 * @author Michael Lastufka 
 * @version Dec 2008
 */
public class MouseOn extends Indicator
{
    /**
     * Constructor for objects of class MouseOn
     */
    public MouseOn()
    {
        super(5,530,30,20);
    }

    /**
     * Set the value for this indicator to display.
     * @param on true when mouse navigation is on.
     */
    public void setValue(boolean on)
    {
        if (on) 
        {
            setValue("On");
            setBackground(Color.CYAN);
        }
        else
        {
            setValue("Off");
            setBackground(Color.BLUE);
        }
    }

}
