import greenfoot.*;

public class SampleDialog extends Dialog
{
    /**
     * CLASS: SampleDialog
     * AUTHOR: danpost (greenfoot.username)
     * DATE: May 3, 2019
     * 
     * DESCRIPTION: Creates a dialog that exhibits the various component types.
     */
    
    // the following array is only for this demo
    private String[] states = { "Alabama", "Alaska", "Arizona", "Arkansas", "California",
        "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
        "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
        "Kansas", "Kentucky", "Louisianna", "Maine", "Maryland",
        "Massachusettes", "Michigan", "Minnesota", "Mississippi", "Missouri",
        "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
        "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
        "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
        "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
        "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
    };
    private World game; // world to proceed to when done with dialog
    
    /** **************************************************** */
    /**                     THE COMPONENTS                   */
    
    public InputBox inputBox = new InputBox();
    
    public ListedTextBox listBox = new ListedTextBox(new java.util.ArrayList<String>(){});
    
    public TextBox textBox = new TextBox(states[0]);
    
    // create a spinner that changes a text box display
    public Spinner spinner = new Spinner(1, 50)
    {
        /** what to do when the value changes (not required on any component) */
        protected void onChange()
        {
            textBox.setText(states[value-1]);
        }
    };
    
    public CheckBox checkBox = new CheckBox();
    
    // create a button with required method (adds input box value to list box list, unchecked)
    public Button button = new Button("Add color")
    {
        /** what to do when clicked (required on all buttons) */
        protected void onClick()
        {
            String text = inputBox.getText();
            if (text == null || "".equals(text)) return;
            listBox.add(text);
            inputBox.setText("");
            inputBox.setFocus();
        }
    };
    
    /**                                                      */
    /** **************************************************** */
    
    /**
     * Creates the dialog.
     */
    public SampleDialog(World world)
    {
        super("Dialog Title"); // crrates a dialog frame with given name
        game = world; // retains the next world to set active
        // builds the world with text and dialog elements
        String title = "THE COMPONENTS";
        Color titleColor = new Color(208, 160, 128);
        addImage(new GreenfootImage(title, 48, titleColor, TRANSPARENT), center, 60);
        addImage(new GreenfootImage("INPUT BOX", 28, Color.BLACK, TRANSPARENT), wide/3, 120);
        inputBox.setMaxLength(16);
        addObject(inputBox, wide/3, 150);
        addImage(new GreenfootImage("LIST BOX", 28, Color.BLACK, TRANSPARENT), wide*3/4, 120);
        listBox.add("Red");
        listBox.add("Green");
        listBox.add("Blue");
        addObject(listBox, wide*3/4, 150);
        addImage(new GreenfootImage("BUTTON", 28, Color.BLACK, TRANSPARENT), wide/3, 200);
        addObject(button, wide/3, 230);
        addImage(new GreenfootImage("TEXT BOX", 28, Color.BLACK, TRANSPARENT), wide*3/5, 280);
        textBox.setAlignment(TextBox.CENTER);
        addObject(textBox, wide*3/5, 310);
        addImage(new GreenfootImage("CHECK BOX", 28, Color.BLACK, TRANSPARENT), wide*3/4, 200);
        addObject(checkBox, wide*3/4, 230);
        addImage(new GreenfootImage("SPINNER", 28, Color.BLACK, TRANSPARENT), wide*2/5, 280);
        addObject(spinner, wide*2/5, 310);
    }
    
    /**
     * Called by dialog and used to determine if input was valid enough to close
     * the dialog and proceed to the next world (required for all Dialog objects).
     */
    protected void onClose()
    {
        if (true) Greenfoot.setWorld(game);
    }
}