import java.awt.Point;

/**
 * InputWindow
 * <p>
 * A Window containing a TextBox for entry, and an "Enter" and "Cancel" Buttons.<p>
 * The TextBox is given focus when opened.<p>
 * When one of the Buttons is clicked, the InputWindow closes.<p>
 * If the enter Button had been clicked, the action listener method getResult() will return the TextBox's contents.<p>
 * <p>
 * Action listener: getResult()
 * 
 * @author Taylor Born
 * @version February 2013 - April 2013
 */
public class InputWindow extends Window
{
    private TextBox input;
    private Button enterBtn = new Button("Enter", new java.awt.Point(50, 23));
    private Button cancelBtn = new Button("Cancel", new java.awt.Point(50, 23));
    private String result;

    /**
     * New InputWindow.
     * @param prompt What title is to be displayed for this Window.
     */
    public InputWindow(String prompt, int max)
    {
        super(prompt, true, true);
        input = new TextBox(new Point(135, 18), "");
        
        input.acceptOnly(TextBox.LETTERS + TextBox.NUMBERS);
        input.setMaxLength(max);
        
        enterBtn.setAcceptByEnterKey(true);
        
        Container c = new Container(new Point(1, 2));
        c.addComponent(input);
        Container btns = new Container(new Point(2, 1));
        btns.addComponent(enterBtn);
        btns.addComponent(cancelBtn);
        c.addComponent(btns);
        addContainer(c);
    }
    
    /**
     * Act.<p>
     * Listen for when the enter and cancel Buttons are clicked, which case the ConfirmWindow will close.<p>
     * If the enter Button was clicked, the action listener method getResult() will return the TextBox's contents.
     */
    @Override
    public void act()
    {
        super.act();
        if (enterBtn.wasClicked())
        {
            result = input.getText();
            toggleShow();
        }
        if (cancelBtn.wasClicked())
            toggleShow();
    }
    
    /**
     * Action listener for this InputWindow.
     * @return The contents of the TextBox if the enter Button was clicked, otherwise null.
     */
    public String getResult()
    {
        String r = result;
        result = null;
        return r;
    }
    
    /**
     * Switch between being set to show and not.<p>
     * If hidden status is false, will be added or removed from World according to new show status.<p>
     * Used instead of toggleShow() when wanting to add to World with the given initial value for the TextBox.
     * @param value Text to be set as the TextBox's contents.
     * @see toggleShow()
     */
    public void toggleShow(String value)
    {
        input.setText(value);
        toggleShow();
    }
    
    /**
     * When open, give TextBox focus and reset result.
     */
    @Override
    protected void initializeOpen()
    {
        super.initializeOpen();
        input.giveFocus();
        result = null;
    }
}