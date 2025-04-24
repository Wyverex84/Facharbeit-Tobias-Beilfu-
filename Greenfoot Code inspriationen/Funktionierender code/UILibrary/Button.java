import greenfoot.*;

/**
 * This is a generic, reusable Button for a user interface. This button is part of the "MSG" 
 * Greenfoot GUI toolkit.
 * 
 * Scenarios using these buttons must also contain the ButtonListener interface, and the World
 * class must implement that interface. When a button of this class is clicked, the 'buttonClicked()' 
 * method in the world class will be called.
 * 
 * Note that positioning places the button's top left corner at the target location.
 * 
 * @author Michael Kölling 
 * @version 1.2
 */
public class Button extends Actor
{
    // colours with default values
    private Color textColor = Color.BLACK;
    private Color backgroundColor = new Color(184, 184, 184);
    private Color backgroundColorPressed = new Color(152, 152, 152);
    private Color bottomBorderColor = new Color(128, 128, 128);
    private Color topBorderColor = new Color(220, 220, 220);
    
    // spacing, with default values
    private final int INSET = 8;  // space between text and edge
    
    private String text;
    private int ID;
    private int fontSize = 16;
    private int width = -1;
    private int height = -1;

    private boolean down = false;
    private GreenfootImage textImage;
    private int textX;      // the x-coordinate of the left edge of the text
    
    public Button()
    {
        this("         ");
    }
    
    public Button(String text)
    {
        this.text = text;
        createImage();
    }
    
    public Button(String text, int IDNumber)
    {
        this.text = text;
        this.ID = IDNumber;
        createImage();
    }
    
    /**
     * Act - check whether any mouse events are detected onthis button and react to them.
     */
    public void act() 
    {
        if (Greenfoot.mousePressed(this)) {
            down = true;
            repaint();
        }
        else if (Greenfoot.mouseDragEnded(this)) {
            // mouse released outside of button - ignore
            down = false;
            repaint();
        }
        else if (Greenfoot.mouseClicked(this)) {
            down = false;
            repaint();
            buttonClicked();
        }
    }
    
    /**
     * This button was clicked by a user. Perform whatever action this button should do.
     */
    protected void buttonClicked()
    {
        ((ButtonListener)getWorld()).buttonClicked(this);
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
     * Set the font size for the button text.
     */
    public void setFontSize(int size)
    {
        fontSize = size;
        createImage();
    }
    
    /**
     * Return this button's font size.
     */
    public int getFontSize()
    {
        return fontSize;
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
     * Set the width of this button to a fixed width.
     */
    public void setWidth(int width)
    {
        this.width = width;
        createImage();
    }
    
    /** 
     * Set the height of this button to a fixed height.
     */
    public void setHeight(int height)
    {
        this.height = height;
        createImage();
    }
    
    /**
     * Return this button's ID number.
     */
    public int getIDNumber()
    {
        return ID;
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
     * Paint the button's background.
     */
    private void paintBackground()
    {
        GreenfootImage img = getImage();
        if (down) {
            img.setColor(backgroundColorPressed);
        }
        else {
            img.setColor(backgroundColor);
        }
        img.fill();
    }
    
    /**
     * Paint one 1-pixel-line of the border bevel.
     */
    private void paintBevel(int offset)
    {
        GreenfootImage img = getImage();
        int w = img.getWidth()-1;
        int h = img.getHeight()-1;
        if (down) {
            img.setColor(bottomBorderColor);
        }
        else {
            img.setColor(topBorderColor);
        }
        img.drawLine(offset, offset, offset, h-offset);
        img.drawLine(offset, offset, w-offset, offset);
        if (down) {
            img.setColor(topBorderColor);
        }
        else {
            img.setColor(bottomBorderColor);
        }
        img.drawLine(offset, h-offset, w-offset, h-offset);
        img.drawLine(w-offset, offset, w-offset, h-offset);
    }
    
    /**
     * Paint the button's border.
     */
    private void paintBorder()
    {
        paintBevel(0);
        paintBevel(1);
        paintBevel(2);
    }
    
    /**
     * Paint the button's text.
     */
    private void paintText()
    {
        GreenfootImage img = getImage();
        int y = (img.getHeight()-textImage.getHeight())/2;
        if (down) {
            y++;
        }
        img.drawImage(textImage, textX, y);
    }
    
    /**
     * Paint the button image, including the button text and decorations.
     */
    private void repaint()
    {
        paintBackground();
        paintBorder();
        paintText();
    }

    /**
     * Create the actor's image at the right size. If the size has been set explicitly, that size 
     * is used. If not, the size is calculated  to fit the button text. The size calculation takes 
     * the text on the button and the current font into account.
     */
    private void createImage()
    {
        int imgWidth = width;
        int imgHeight = height;
        
        textImage = new GreenfootImage(text, fontSize, textColor, null);
        
        int textWidth = textImage.getWidth();
        if (imgWidth <= 0) {
            imgWidth = textWidth + 2 * INSET;
        }
        if (imgHeight <= 0) {
            imgHeight = textImage.getHeight() + 2 * INSET;
        }
        textX = (imgWidth/2) - (textWidth/2);
        
        GreenfootImage img = new GreenfootImage(imgWidth, imgHeight);
        setImage(img);
        repaint();
    }
}
