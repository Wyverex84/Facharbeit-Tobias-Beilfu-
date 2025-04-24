import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.util.ArrayList;


import java.awt.Point;

/**
 * GUI_DemoWorld
 * 
 * @author Taylor Born
 * @version November 2010 - January 2014
 */
public class GUI_DemoWorld extends World
{
    private static final int WIDTH  = 600;
    private static final int HEIGHT = 400;

    private Menu myMenu;
    
    private TextBox txtB;
    private Point[] textBoxDisplaySizes = new Point[] { new Point(250, 100), new Point(140, 75), new Point(100, 50) };
    private DropDownList<String> mySelectList;
    
    private ListBox<String> myList;
    
    private Slider mySlider;
    
    private Button btnClick;
    private SwitchButton btnAction;
    private Label lblCounter;
    private Label lblWords;
    private SwitchButton btnEn;
    
    // ---------------------------------------------------------------------------------------------------------
    // Window Examples. Access them through the Menu in top left of scenario
    //
    private Palette colors = new Palette();
    private FontWindow fonts = new FontWindow();
    private InputWindow inputW = new InputWindow("Enter some text:", 13);
    private ConfirmWindow confirmW = new ConfirmWindow("Are you sure?");
    private TextWindow textW = new TextWindow("Text Editor", 200, 150, "");
    private SelectionWindow<String> selectW = new SelectionWindow("Select something:", new ArrayList<String>(){{ add("Option A"); add("Option B"); add("Option C"); }}, 0);
    private MessageWindow messageW = new MessageWindow("This is an important message.");
    //
    // ---------------------------------------------------------------------------------------------------------
    
    
    public GUI_DemoWorld()
    {
        super(WIDTH, HEIGHT, 1);
        setBackground(new GreenfootImage(WIDTH, HEIGHT));
        
        
        // IGNORE/DISCARD this, it has nothing to do with the GUI Components
        CodeKeyUser.enter();
        
        
        
        // Should not include individual subclasses of GUI_Component in the following:
        setPaintOrder(GUI_Component.class);
        
        
        
        // Create Menu
        setUpMenu();
        
        
        
        // -------------------------------------------------------------------------------------------
        // TextBox Display
        //
        String text = "A TextBox that is word-wrapped, scrollable, typeable, sizeable, selectable, and more. Must have focus in order to edit (Click on it to give it focus).\n\nYou can copy/cut/paste (ctrl+c, ctrl+x, ctrl+v).\n\nYou can use the enter key to make new lines.\n\nClick somewhere to move the blinking cursor there. Can press the arrow keys to also move it.\n\nCan select text by:\n1. Click and drag.\n2. Shift + click.\n3. Shift + arrow keys.\n4. ctrl+a\n5. Double/Triple click.\n\nWorks with different Fonts and sizes. Try out the TextWindow (from the Menu in the top left corner of scenario), that has access for changing text Color and the font family, size, and style.\n\nAbove you can use the DropDownList to change the size of this TextBox. The TextBox also auto scrolls while you type to stay with the blinking cursor." + /* IGNORE/DISCARD the following, it has nothing to do with the GUI Components */ (!CodeGenerator.haveUser() ? "" : "\n\nEnter this code into bourne's Residents scenario: " + CodeGenerator.getCode(CODE_ID));
        txtB = new TextBox(new Point(250, 100), text, new Font("Helvetica", Font.PLAIN, 15));
        addObject(txtB, 470, 73);
        // 
        Container textBoxSizeContainer = new Container(new Point(2, 1));
        textBoxSizeContainer.addComponent(new Label("TextBox Size:"));
        mySelectList = new DropDownList(new ArrayList<String>(){{add("Large (250 X 100)"); add("Medium (140 X 75)"); add("Small (100 X 50)"); }}, 0);
        textBoxSizeContainer.addComponent(mySelectList);
        addObject(textBoxSizeContainer, 470, 12);
        //
        // -------------------------------------------------------------------------------------------
        
        
        
        // -------------------------------------------------------------------------------------------
        // ListBox Display
        //
        addObject(new Label("A ListBox that holds a list of Strings."), 470, 159);
        addObject(new Label("Selectable and Scrollable"), 470, 172);
        // Create an ArrayList of Strings
        ArrayList<String> strs2 = new ArrayList<String>(){{add("Fruit"); add("Breads"); add("Meats"); add("Dairy"); add("Sweets"); add("Vegetables"); add("Nuts"); add("Cakes"); add("Pies"); add("Fast Food"); add("Peanut Butter"); add("Water"); add("Root Beer");}};
        // Initialize ListBox with size and ArrayList of Strings
        myList = new ListBox(new Point(130, 78), strs2);
        addObject(myList, 470, 222);
        //
        // -------------------------------------------------------------------------------------------
        
        
        
        // -------------------------------------------------------------------------------------------
        // Password TextBox
        //
        TextBox tbPassword = new TextBox(new Point(100, 18), "");
        tbPassword.setMaxLength(12);
        tbPassword.setAsPassword(true);
        tbPassword.setMessage("Password");
        addObject(tbPassword, 61, 154);
        //
        // -------------------------------------------------------------------------------------------
        
        
        
        // -------------------------------------------------------------------------------------------
        // Slider
        //
        mySlider = new Slider(255, 0, 255, 0, 1);
        addObject(mySlider, 135, 251);
        mySlider.setValue(30);
        //
        // -------------------------------------------------------------------------------------------
        
        
        
        // -------------------------------------------------------------------------------------------
        // Button and Label display
        //
        // Initialize Button with text "Click this" and with size 100 x 23
        btnClick = new Button("Click this", new Point(100, 23));
        addObject(btnClick, 58, 350);
        // Create an ArrayList of Strings
        ArrayList<String> strs = new ArrayList<String>(){{add("Add Counter"); add("Add Word");}};
        // Initialize SwitchButton with ArrayList of Strings, with size 100 x 23, and text starting index of 0
        btnAction = new SwitchButton(strs, new Point(100, 23), 0);
        addObject(btnAction, 181, 350);
        // Button to enable/disable other two Buttons
        btnEn = new SwitchButton(new ArrayList<String>(){{add("Enable Buttons"); add("Disable Buttons");}}, new Point(100, 23), 1);
        addObject(btnEn, 58, 379);
        // Add unimportant labels
        addObject(new Label("To"), 120, 351);
        addObject(new Label("(Click this Button to change action of other Button)"), 373, 351);
        // Initialize Label with String
        lblCounter = new Label("0");
        addObject(lblCounter, 195, 381);
        // Initialize Label with String
        lblWords = new Label("Random Words");
        addObject(lblWords, 386, 381);
        // -------------------------------------------------------------------------------------------
        
        
        
        Greenfoot.start();
    }
    
    @Override
    public void act()
    {
        // Listen for action from Menu
        listenToMenu(myMenu.getItemPressed());
        
        // Check if Slider's value has changed
        if (mySlider.hasChanged())
        {
            GreenfootImage back = getBackground();
            back.setColor(Color.WHITE);
            back.fill();
            back.setColor(new Color(0, 0, 255, (int)mySlider.getValue()));
            back.fill();
        }
        
        // Listen for if the Button is clicked
        if (btnClick.wasClicked())
        {
            // Add 1 to the number already in my lblCounter
            if (btnAction.getText().equals("Add Counter"))
                lblCounter.setText("" + (Integer.parseInt(lblCounter.getText()) + 1));
            else
            {
                // Check if label's String is long and should be reseted
                if (lblWords.getText().length() > 25)
                    lblWords.setText("");
                // Add a random word from the ListBox to the Label
                lblWords.setText(lblWords.getText() + " " + myList.get(Greenfoot.getRandomNumber(myList.size())));
            }
        }
        
        // Listen for if the SwitchButton is clicked so it may toggle
        if (btnAction.wasClicked())
        {}
        
        // Listen for if the SwitchButton is clicked so it may toggle,
        // And so that we can take action
        if (btnEn.wasClicked())
        {
            btnClick.setEnable(btnEn.getIndex() != 0);
            btnAction.setEnable(btnEn.getIndex() != 0);
        }
        
        // Listen to change size of TextBox on display
        if (mySelectList.hasChanged())
            txtB.setSize(textBoxDisplaySizes[mySelectList.getIndex()]);
    }
    
    private void setUpMenu()
    {
        // Top level categories
        ArrayList<String> menuStr = new ArrayList<String>(){{ add("Options"); add("Window Examples"); }};
        
        // Create the Menu with top level categories, and make it snug in top left corner of scenario
        myMenu = new Menu(menuStr, new Point(0, 0));
        
        // Make some customizations
        myMenu.setFont(new Font("Helvetica", Font.BOLD, 14));
        myMenu.setTextColor(Color.BLUE);
        myMenu.setHoverColor(Color.ORANGE);
        
        // Add items
        myMenu.addItems("Window Examples/", new ArrayList<String>(){{ add("Palette"); add("ConfirmWindow"); add("InputWindow"); add("FontWindow"); add("TextWindow"); add("SelectionWindow"); add("MessageWindow"); }});
        myMenu.addItems("Options/", new ArrayList<String>(){{ add("Slider"); add("ListBox"); }});
        myMenu.addToggleItem("Options/ListBox/Multiple Selecting", false);
        myMenu.addItem("Options/Slider/Increment");
        myMenu.addItemsAsSet("Options/Slider/Increment/", new ArrayList<String>(){{add("1"); add("5"); add("15"); }}, 0);
        
        // Add to World
        addObject(myMenu, 330, 78);
    }
    
    private void listenToMenu(String s)
    {
        if (s == null)
            return;
        
        if (s.startsWith("Window Examples/"))
        {
            s = s.substring(s.indexOf('/') + 1);
            
            if (s.equals("Palette"))
                colors.toggleShow();
            else if (s.equals("ConfirmWindow"))
                confirmW.toggleShow();
            else if (s.equals("InputWindow"))
                inputW.toggleShow();
            else if (s.equals("FontWindow"))
                fonts.toggleShow();
            else if (s.equals("TextWindow"))
                textW.toggleShow();
            else if (s.equals("SelectionWindow"))
                selectW.toggleShow();
            else if (s.equals("MessageWindow"))
                messageW.toggleShow();
        }
        else if (s.startsWith("Options/"))
        {
            s = s.substring(s.indexOf('/') + 1);
            
            if (s.startsWith("Slider/"))
            {
                s = s.substring(s.indexOf('/') + 1);
                if (s.startsWith("Increment/"))
                {
                    s = s.substring(s.indexOf('/') + 1);
                    mySlider.setIncrement(Integer.parseInt(s));
                    mySlider.setValue(mySlider.getValue() - (mySlider.getValue() % mySlider.getIncrement()));
                }
            }
            else if (s.equals("ListBox/Multiple Selecting"))
                myList.setMultipleSelecting(!myList.multipleSelectingIsEnabled());
        }
    }
    
    
    // IGNORE/DISCARD this, it has nothing to do with the GUI Components
    private static final int CODE_ID = 38;
}