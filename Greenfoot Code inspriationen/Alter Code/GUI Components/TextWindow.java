import java.util.ArrayList;
import java.awt.Point;



/**
 * TextWindow
 * <p>
 * A simple text editor Window, containing a TextBox, and a Menu with options to change Font or Color, to clear, or print to console, - the text in the TextBox.
 * 
 * @author Taylor Born
 * @version February 2013
 */
public class TextWindow extends Window
{
    protected Menu menu;
    protected TextBox textB;
    private Palette colors = new Palette();
    private FontWindow fonts = new FontWindow();
    private ConfirmWindow confirmClear = new ConfirmWindow("Are you sure you want to clear all the text?");

    public TextWindow(String title, int w, int h, String text)
    {
        super(title);
        textB = new TextBox(new Point(w, h), text);
        
        Container c = new Container(new Point(1, 1));
        c.addComponent(textB);
        addContainer(c);
        
        setUpMenu();
        
        addHelperWindow(colors);
        addHelperWindow(fonts);
        addHelperWindow(confirmClear);
    }
    
    protected void setUpMenu()
    {
        menu = createMenu();
        ArrayList<String> topMenu = new ArrayList<String>();
        topMenu.add("TextBox");
        menu.addItems("", topMenu);
        menu.addItem("TextBox/Text");
        menu.addItem("TextBox/Text/Font");
        menu.addItem("TextBox/Text/Color");
        menu.addItem("TextBox/Clear");
        menu.addItem("TextBox/Print");
    }
    
    @Override
    public void act()
    {
        super.act();
        
        listenToMenu(menu.getItemPressed());
        
        Font f = fonts.getResult();
        if (f != null)
            textB.setFont(f);
        
        Color c = colors.getResult();
        if (c != null)
            textB.setTextColor(c);
        
        if (confirmClear.isConfirmed())
            textB.clear();
    }
    
    protected void listenToMenu(String s)
    {
        if (s != null)
        {
            if (s.startsWith("TextBox/"))
            {
                s = s.substring(s.indexOf("/") + 1);
                if (s.startsWith("Text/"))
                {
                    s = s.substring(s.indexOf("/") + 1);
                    if (s.equals("Font"))
                    {
                        if (fonts.inWorld())
                            fonts.toggleShow();
                        fonts.toggleShow(textB.getFont());
                    }
                    else if (s.equals("Color"))
                    {
                        if (colors.inWorld())
                            colors.toggleShow();
                        colors.toggleShow(textB.getTextColor());
                    }
                }
                else if (s.equals("Clear"))
                {
                    if (confirmClear.inWorld())
                        confirmClear.toggleShow();
                    confirmClear.toggleShow();
                }
                else if (s.equals("Print"))
                    System.out.println(textB.getText());
            }
        }
    }
    
    public String getText()
    {
        return textB.getText();
    }
    
    public void setText(String s)
    {
        textB.setText(s);
    }
}