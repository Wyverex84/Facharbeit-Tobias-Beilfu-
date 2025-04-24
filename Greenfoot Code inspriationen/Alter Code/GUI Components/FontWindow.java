import greenfoot.World;
import java.util.Collections;
import java.util.ArrayList;
import java.awt.Point;

import java.awt.GraphicsEnvironment;

/**
 * FontWindow
 * <p>
 * Font selection Window of the Fonts found on the computer.<p>
 * Includes:<p>
 * ListBox containing the Fonts found on the computer.<p>
 * DropDownList containing styles "Regular", "Bold", and "Italics".<p>
 * Slider to determine Font size.<p>
 * Displays a sample String drawn with the selected Font.<p>
 * "Enter" and "Cancel" Buttons that will close the FontWindow. The "Enter" Button makes ready the last selected Font for the action listener getResult().<p>
 * <p>
 * Action listener: getResult()
 * 
 * @author Taylor Born
 * @version February 2013 - March 2013
 */
public class FontWindow extends Window
{
    private ListBox<String> names;
    private DropDownList styles = new DropDownList(new ArrayList<String>(){{ add("Regular"); add("Bold"); add("Italics"); }}, 0);
    private Slider sizes = new Slider(200, 1, 72, 12, 1);
    private Button enterBtn = new Button("Enter", new Point(44, 22));
    private Button cancelBtn = new Button("Cancel", new Point(44, 22));
    private CheckBoxLabel showSample = new CheckBoxLabel("Sample String", false);
    private LabelWindow labelW = new LabelWindow();
    private Font result;

    /**
     * Create a new FontWindow.
     */
    public FontWindow()
    {
        super("Fonts");
        
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ArrayList<Font> fonts = new ArrayList<Font>();
        Collections.addAll(fonts, e.getAllFonts());
        ArrayList<String> ns = new ArrayList<String>();
        for (Font f : fonts)
            ns.add(f.getName());
        names = new ListBox(new Point(180, 105), ns);
        
        Container c = new Container(new Point(1, 2));
        Container topC = new Container(new Point(2, 1));
        topC.addComponent(names);
        Container topRightC = new Container(new Point(1, 3));
        topRightC.addComponent(styles);
        Container btnC = new Container(new Point(2, 1));
        btnC.addComponent(enterBtn);
        btnC.addComponent(cancelBtn);
        topRightC.addComponent(btnC);
        topRightC.addComponent(showSample);
        topC.addComponent(topRightC);
        c.addComponent(topC);
        c.addComponent(sizes);
        addContainer(c);
        
        addHelperWindow(labelW);
    }
    
    /**
     * Act.
     */
    @Override
    public void act()
    {
        super.act();
        
        if (showSample.hasChanged())
            if (showSample.isChecked())
            {
                if (!labelW.inWorld())
                    labelW.toggleShow();
            }
            else if (labelW.inWorld())
                labelW.toggleShow();
        
        if (enterBtn.wasClicked())
        {
            result = getSelectedFont();
            toggleShow();
            return;
        }
        if (cancelBtn.wasClicked())
            toggleShow();
    }
    
    private Font getSelectedFont()
    {
        int style = Font.PLAIN;
        switch (styles.getIndex())
        {
            case 1: style = Font.BOLD; break;
            case 2: style = Font.ITALIC;
        }
        return new Font(names.getOneSelection(), style, (int)sizes.getValue());
    }
    
    /**
     * Does what toggleShow() does but with given Font as inital selected Font.
     * @param font The Font to be initially selected.
     * @see toggleShow()
     */
    public void toggleShow(Font font)
    {
        toggleShow();
        names.setIndex(names.indexOf(font.getName()));
        if (font.isPlain())
            styles.setIndex(0);
        else if (font.isBold())
            styles.setIndex(1);
        else if (font.isItalic())
            styles.setIndex(2);
        sizes.setValue(font.getSize());
    }
    
    /**
     * Action listener for the FontWindow.
     * @return The Font selected when "Enter" Button was clicked. If Button wasn't clicked, returns null.
     */
    public Font getResult()
    {
        Font f = result;
        result = null;
        return f;
    }
    
    /**
     * Reset result.
     */
    @Override
    protected void initializeOpen()
    {
        super.initializeOpen();
        result = null;
    }
    
    private Point getFontWindowSize()
    {
        return new Point(getImage().getWidth(), getImage().getHeight());
    }
    private Point getFontWindowLocation()
    {
        return new Point(getX(), getY());
    }
    
    @Override
    public void setLocation(int x, int y)
    {
        super.setLocation(x, y);
        if (labelW.inWorld())
            labelW.relocate();
    }

    private class LabelWindow extends Window
    {
        private Label label;
        private Container c;
        
        public LabelWindow()
        {
            super("");
            label = new Label("Sample String");
            c = new Container(new Point(1, 1));
            c.addComponent(label);
            addContainer(c);
        }
        @Override
        public void act()
        {
            super.act();
            if (names.hasChanged() || sizes.hasChanged() || styles.hasChanged()) {
                label.setFont(getSelectedFont());
                relocate();
            }
        }
        @Override
        public void removeFromWorld()
        {
            super.removeFromWorld();
            if (!isBringingToFront())
                showSample.setChecked(false);
        }
        
        private void relocate()
        {
            Point pLoc = getFontWindowLocation();
            Point pSize = getFontWindowSize();
            
            if (pLoc.getX() + pSize.getX() / 2 + getImage().getWidth() <= getWorld().getWidth())
                setLocation((int)(pLoc.getX() + pSize.getX() / 2) + getImage().getWidth() / 2, (int)pLoc.getY());
            else if (pLoc.getY() + pSize.getY() / 2 + getImage().getHeight() > getWorld().getHeight())
                setLocation(getWorld().getWidth() - getImage().getWidth() / 2, (int)(pLoc.getY() - pSize.getY() / 2) - getImage().getHeight() / 2);
            else
                setLocation(getWorld().getWidth() - getImage().getWidth() / 2, (int)(pLoc.getY() + pSize.getY() / 2) + getImage().getHeight() / 2);
        }
        
        @Override
        public void addedToWorld(World world)
        {
            super.addedToWorld(world);
            relocate();
        }
    }
}