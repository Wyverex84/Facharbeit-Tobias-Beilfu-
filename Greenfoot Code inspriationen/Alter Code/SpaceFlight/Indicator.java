import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


import java.text.DecimalFormat;

/**
 * Cockpit Indicators are derived from this abstract class
 * for display only.
 * They do not encapsulate the functions of the value displayed.
 * 
 * @author Michael Lastufka 
 * @version Dec 2008
 */
public abstract class Indicator
{
    final public int x, y, w, h; // (x,y) upper left, (w,h) width, height
    private String m_content;
    private Color  m_background = Color.BLUE;
    private Color  m_pen = Color.YELLOW;
    private Font   m_font = null;
    protected DecimalFormat digits3 = new DecimalFormat("000");
    
    /**
     * Call this constructor as 'super(x,y,w,h)' to initialize 
     * a derived indicator.
     */
    public Indicator(int x0, int y0, int w0, int h0)
    {
        x = x0; y = y0; w = w0; h = h0; m_content = null;
        Font font = new Font("TimesRoman", Font.BOLD, 12);
    }
    
    /**
     * Set the value for this indicator to display.
     * @param content the content to display.
     */
    public void setValue(String content)
    {
        m_content = content;
    }
    
    /**
     * Set the background color.
     * @param background color to display behind the text.
     */
    public void setBackground(Color background)
    {
        m_background = background;
    }
    
    /**
     * Set the pen color.
     * @param pen text color to display.
     */
    public void setPen(Color pen)
    {
        m_pen = pen;
    }
    
    /**
     * Draws the motion mode indicator on the cockpit panel.
     */
    public GreenfootImage getImage()
    {
        GreenfootImage slate = new GreenfootImage(w, h);
        slate.setFont(m_font);
        slate.setColor(m_background);
        slate.fillOval(0, 0, w, h);
        slate.setColor(m_pen); 
        slate.drawString(m_content, 7, 15);
        return slate;
    }
    
    /**
     * Determine if the screen position is on the indicator.
     * Useful to see if a click landed on it.
     * @param mx the x-screen coordinate.
     * @param my the y-screen coordinate.
     * @return true if (mx,my) is on the indictor.
     */
    public boolean isTouching(int mx, int my)
    {
        return (Math.abs(x + w/2 - mx) < w/2
             && Math.abs(y + h/2 - my) < h/2);
    }
}
