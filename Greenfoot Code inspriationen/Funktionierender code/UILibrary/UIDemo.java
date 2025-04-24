import greenfoot.*;

/**
 * A demo of some GUI objects (from the "MSG" Greenfoot GUI toolkit).
 * 
 * @author Michael Kölling
 * @version 1.0
 */
public class UIDemo extends World
    implements ButtonListener
{
    private Label label1;
    private Label label2;
    private int clickCount;
    
    /**
     * Constructor for objects of class Demo.
     * 
     */
    public UIDemo()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        getBackground().setColor(new Color(200, 220, 210));
        getBackground().fill();
        prepare();
    }

    /**
     * A button in the world was clicked.
     * 
     * @param button  The button object that was clicked.
     */
    public void buttonClicked(Button button)
    {
        Label label;
        if (button.getIDNumber() == 1) {
            label = label2;
        }
        else {
            label = label1;
        }
            
        clickCount++;
        if (clickCount == 4) {
            label.setText("click");
            clickCount = 0;
        }
        else {
            label.setText(label.getText() + ", click");
        }
    }
    
    /**
     * Prepare the world for the start of the program. That is: create the initial objects and add them to the world.
     */
    public void prepare()
    {
        // a normal button - the size adjusts to the text length
        Button button = new Button("Simple Button");
        addObject(button, 20, 40);
        
        // a button with fixed width and height
        Button button2 = new Button("A button with custom size", 1);
        button2.setWidth(250);
        button2.setHeight(100);
        addObject(button2, 20, 80);
        
        // a normal label - by default, the background is transparent
        label1 = new Label("A simple text label");
        addObject(label1, 300, 40);
        
        // a label with some customisation
        label2 = new Label("Fancy label");
        label2.setBackground(Color.WHITE);
        label2.setForeground(Color.DARK_GRAY);
        label2.setBorder(Color.GRAY);
        label2.setWidth(200);
        label2.setAlignment(Label.RIGHT);
        addObject(label2, 300, 80);
        
        // a switch
        Switch onOff = new Switch("On", "Off");
        addObject(onOff, 300, 140);
        
        // // a checkbox
        Checkbox box = new Checkbox("with Cream");
        addObject(box, 30, 220);
        box = new Checkbox("with Sugar");
        box.setChecked(true);
        addObject(box, 30, 240);
        box = new Checkbox("extra strong");
        addObject(box, 30, 260);

        Checkbox bigBox = new Checkbox("Big checkbox");
        bigBox.setSize(26);
        addObject(bigBox, 30, 300);
        
        // a slider
        Slider slider = new Slider("Volume", 0, 11);
        addObject(slider, 300, 260);
        
    }
}
