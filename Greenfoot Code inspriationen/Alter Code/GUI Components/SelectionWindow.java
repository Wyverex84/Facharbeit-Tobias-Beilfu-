import java.util.ArrayList;
import java.awt.Point;

/**
 * SelectionWindow
 * 
 * @author Taylor Born
 * @version March 2013 - April 2013
 */
public class SelectionWindow<E> extends Window
{
    private DropDownList<E> options;
    private Button enterBtn = new Button("Enter", new java.awt.Point(50, 23));
    private Button cancelBtn = new Button("Cancel", new java.awt.Point(50, 23));
    private E result;

    /**
     * New SelectionWindow.
     * @param prompt What title is to be displayed for this Window.
     */
    public SelectionWindow(String prompt, ArrayList<E> list, int i)
    {
        super(prompt, true, true);
        
        options = new DropDownList(list, i);
        
        Container c = new Container(new Point(1, 2));
        c.addComponent(options);
        Container btns = new Container(new Point(2, 1));
        btns.addComponent(enterBtn);
        btns.addComponent(cancelBtn);
        c.addComponent(btns);
        addContainer(c);
    }
    public SelectionWindow(String prompt, ArrayList<E> list)
    {
        this(prompt, list, 0);
    }
    
    /**
     * Act.
     */
    @Override
    public void act()
    {
        super.act();
        if (enterBtn.wasClicked())
        {
            result = options.getSelected();
            toggleShow();
        }
        if (cancelBtn.wasClicked())
            toggleShow();
    }
    
    /**
     * Action listener for this SelectionWindow.
     * @return The selection from the DropDownList.
     */
    public E getResult()
    {
        E r = result;
        result = null;
        return r;
    }
    
    public void toggleShow(int i)
    {
        options.setIndex(i);
        toggleShow();
    }
    
    /**
     * When open, reset result.
     */
    @Override
    protected void initializeOpen()
    {
        super.initializeOpen();
        result = null;
    }
}