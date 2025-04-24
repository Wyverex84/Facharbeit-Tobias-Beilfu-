import greenfoot.*;

/**
 * This is a generic, reusable checkbox for a user interface. This component is part of the "MSG" 
 * Greenfoot GUI toolkit.
 * 
 * The checkbox has no callback - it is used by polling its state at appropriate times.
 * 
 * Note that positioning places the component's top left corner at the target location.
 * 
 * @author Michael Kölling 
 * @version 1.3
 */
public class Checkbox extends Actor
{
    private static final int spacing = 6;
    
    // colours with default values
    private Color textColor = Color.BLACK;
    private Color backgroundColor = new Color(184, 184, 184);
    private Color bottomBorderColor = new Color(128, 128, 128);
    private Color topBorderColor = new Color(220, 220, 220);
    
    private int boxSize = 14;

    private String text;
    private int fontSize = 16;
    private boolean checked = false;
    GreenfootImage textImage;
    
    /**
     * Create a checkbox without text.
     */
    public Checkbox()
    {
        this("");
    }
    
    /**
     * Create a checkbox with a given text label.
     */
    public Checkbox(String text)
    {
        this.text = text;
        createImage();
    }
    
    /**
     * Act - check whether the checkbox was clicked.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this)) {
            toggle();
            repaint();
        }
    }
    
    /**
     * Check or uncheck this checkbox.
     * 
     * @param checked  If true, check this box. Otherwise uncheck.
     */
    public void setChecked(boolean checked)
    {
        this.checked = checked;
        repaint();
    }

    /**
     * Return the status of this checkbox.
     * 
     * @return True if this checkbox is checked. False otherwise.
     */
    public boolean isChecked()
    {
        return checked;
    }

    /**
     * Toggle this switch's state.
     */
    public void toggle()
    {
        checked = !checked;
        repaint();
    }
    
    /** 
     * Set the text to be displayed.
     */
    public void setText(String text)
    {
        this.text = text;
        createImage();
    }
    
    /**
     * Return this button's text.
     */
    public String getText()
    {
        return text;
    }
    
    /** 
     * Set the font for the button text.
     */
    public void setFont(Font font)
    {
        getImage().setFont(font);
        createImage();
    }
    
    /**
     * Return this button's font.
     */
    public Font getFont()
    {
        return getImage().getFont();
    }
    
    /** 
     * Set the background color.
     */
    public void setBackground(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
        createImage();
    }
    
    /** 
     * Set the foreground color.
     */
    public void setForeground(Color foregroundColor)
    {
        this.textColor = foregroundColor;
        createImage();
    }
    
    /** 
     * Set the size of this checkbox.
     */
    public void setSize(int boxSize)
    {
        this.boxSize = boxSize;
        fontSize = boxSize;
        createImage();
    }
    
    /**
     * Override setLocation to position the button's top left corner at the given location.
     */
    public void setLocation(int x, int y)
    {
        super.setLocation (x + getImage().getWidth()/2, y + getImage().getHeight()/2);
    }
    
    // --------------------------- private methods ---------------------------------

    /**
     * Paint one 1-pixel-line of the border bevel.
     */
    private void paintBevel(int offset)
    {
        GreenfootImage img = getImage();
        if (checked) {
            img.setColor(bottomBorderColor);
        }
        else {
            img.setColor(topBorderColor);
        }
        img.drawLine(offset, offset+1, offset, boxSize-offset-1);
        img.drawLine(offset, offset+1, boxSize-offset-1, offset+1);
        if (checked) {
            img.setColor(topBorderColor);
        }
        else {
            img.setColor(bottomBorderColor);
        }
        img.drawLine(offset, boxSize-offset, boxSize-offset-1, boxSize-offset);
        img.drawLine(boxSize-offset-1, offset+1, boxSize-offset-1, boxSize-offset);
    }
    
    /**
     * Paint the button's background.
     */
    private void paintBox()
    {
        GreenfootImage img = getImage();
        img.clear();
        img.setColor(backgroundColor);
        img.fillRect(2, 3, boxSize-4, boxSize-4);
        paintBevel(0);
        paintBevel(1);
        //paintBevel(2);
    }
    
    /**
     * Paint the button's border.
     */
    private void paintCheck()
    {
        if (checked) {
            GreenfootImage img = getImage();
            img.setColor(textColor);
            Font textFont = img.getFont();
            img.setFont(new Font(boxSize));
            img.drawString("x", 3, img.getHeight()-6);  
            img.setFont(textFont);
        }
    }
    
    /**
     * Paint the button's text.
     */
    private void paintText()
    {
        GreenfootImage img = getImage();
        img.drawImage(textImage, boxSize+spacing, (img.getHeight()-textImage.getHeight())/2);
    }
    
    /**
     * Paint the button image, including the button text and decorations.
     */
    private void repaint()
    {
        paintBox();
        paintCheck();
        paintText();
    }

    /**
     * Create the actor's image at the right size. If the size has been set explicitly, that size 
     * is used. If not, the size is calculated  to fit the button text. The size calculation takes 
     * the text on the button and the current font into account.
     */
    private void createImage()
    {
        textImage = new GreenfootImage(text, fontSize, textColor, null);

        int textWidth =  textImage.getWidth();
        int imgWidth = textWidth + boxSize + spacing;
        int imgHeight = Math.max(boxSize+2, textImage.getHeight());
        
        GreenfootImage img = new GreenfootImage(imgWidth, imgHeight);
        setImage(img);
        repaint();
    }
}
