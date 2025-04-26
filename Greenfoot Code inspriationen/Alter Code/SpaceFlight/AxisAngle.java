import java.text.DecimalFormat;


/**
 * AxisAngle indicator for x, y, z.
 * 
 * @author Michael Lastufka 
 * @version Dec 2008
 */
public class AxisAngle extends Indicator
{
    private String m_label = "X";
    
    /**
     * Constructor for objects of class AxisAngle
     * @param yPos the vertical placement of the indicator.
     * @param label the prefix label for the angle.
     */
    public AxisAngle(String label, int yPos)
    {
        super(5,yPos,45,20);
        m_label = label;
        setBackground(Color.CYAN);
        setPen(Color.BLACK);
    }

    /**
     * Set the value for this indicator to display.
     * @param angle rotational position.
     */
    public void setValue(double angle)
    {
        setValue(m_label + " " + digits3.format(angle));
    }
}
