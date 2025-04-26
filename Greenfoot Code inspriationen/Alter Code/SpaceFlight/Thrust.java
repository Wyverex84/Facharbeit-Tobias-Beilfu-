/**
 * Displays Thrust state.
 * 
 * @author Michael Lastufka 
 * @version Dec 2008
 */
public class Thrust extends Indicator
{
    /**
     * Constructor for objects of class MouseOn
     */
    public Thrust(int xPos, int yPos)
    {
        super(xPos, yPos, 40, 20);
    }

    /**
     * Set the value for this indicator to display.
     * @param thrust value of thrust being used.
     */
    public void setValue(double thrust)
    {
        setValue(digits3.format(thrust));
    }
}
