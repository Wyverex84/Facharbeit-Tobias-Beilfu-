import greenfoot.*;

/**
 * World Class 'MenuWorld': A world to demonstrate the usage of the Support Actor Classes 
 * 'Button' and 'Menu'
 * 
 * Author: danpost
 * Version: 2.1  June 23, 2012
 */
public class MenuWorld extends World
{
    static final int WIDE = 400, HIGH = 400;
    // The following are arrays of String text for the menu items
    // (the first is the title (which is programatically changed); the rest are the menu options)
    String[] menu1 = { "FIRST MENU", "One", "Two", "Three", "Four", "Five", "Six" };
    String[] menu2 = { "SECOND MENU", "First", "Second", "Third" };
    final int MAX_MENU_CT = 7; // number of example menus displayed in this world
    int menuNumber = MAX_MENU_CT - 1; // used to rotate through the menus
    Menu menu = null; // to hold the current menu object

    /**
     * Constructor MenuWorld: create a world of specified width (WIDE) and height (HIGH)
     * with a grid-size of 1; also adds an Instruct object giving instructions
     */
    public MenuWorld()
    {    
        super(WIDE, HIGH, 1);
        Text text = new Text("Click\n'Run' button\nbelow to\nstart", 84, Color.BLACK, new Color(160, 160, 160));
        addObject(text, WIDE / 2, HIGH / 2);
        // example of making the sides less rounded
        text.setCircleFactor(200);
    }

    /**
     * Method 'started': removes the Instruct object (or Menu object with Button objects)
     * from the world, and places several non-menu buttons in the world
     */
    public void started()
    {
        removeObjects(getObjects(null));
        menu = null;
        menuNumber = MAX_MENU_CT - 1;
        Button.defaultButtonHeight = 30;
        Button.defaultRounded = false;
        Button.defaultTextColor = Color.RED;
        Button.defaultButtonColor = Color.CYAN;
        Button.defaultSolidFrame = true;
        String[] btnText = { "Credits", "Start", "Info", "Options" };
        Button[] buttons = new Button[btnText.length];
        int maxWide = 0;
        for (int i = 0; i < btnText.length; i++)
        {
            if (i != 1) buttons[i] = new Button(btnText[i], i + 20); else buttons[i] = new DelayButton(btnText[i], i + 20, 3, true);
            addObject(buttons[i], 51 + 99 * i, 18);
            maxWide = (int) Math.max(maxWide, buttons[i].getButtonWidth());
        }
        for (int i = 0; i < buttons.length; i++) buttons[i].setButtonWidth(maxWide);
    }

    /**
     * Method 'act': re-acts to button clicks and displays a new menu if all objects were removed from the world
     */
    public void act()
    {
        int selection = Button.getSelected(); // saving the button number so we can display it
        // The following should be placed in your world 'act' method
        switch (selection)
        {
            case -1:
                break; // no action required (no button was clicked)
            case -2:
                removeObjects(getObjects(Button.class));
                break; // 'New menu' (non-menu) button object was clicked
            case 0: // example of a text object
                menu.removeMenu();
                menu = null;
                BlinkText text = new BlinkText("Menu\ncancelled", 96, Color.WHITE, Color.DARK_GRAY, 15, Color.YELLOW);
                addObject(text, 200, 150);
                text.setButtonHeight(200);
                text.setImageAlpha(240);
                text.setRounded(false);
                // another example of a text object
                addObject(new Marquiss("Button has a 3 second delay", 40, Color.WHITE, Color.BLACK, 3), 200, 360);
                // example of a button with a built-in delay
                addObject(new DelayButton("New menu", -2, 3, true), 200, 300);
                break; // the first button (title button object) wss clicked
            case 20: // 'Credits'
            case 21: // 'Start'
            case 22: // 'Options'
            case 23: // 'Help'
                removeObjects(getObjects(Button.class)); // user created menu option was clicked
            case 1: // 'One' or 'First'
            case 2: // 'Two' or 'Second'
            case 3: // 'Three' or 'Third'
            case 4: // 'Four'
            case 5: // 'Five'
            case 6: // 'Six'
            default:
                if (menu != null) menu.removeMenu();
                menu = null;
                Text text2 = new Text("Button " + selection + "\nwas clicked", 84, Color.WHITE, Color.DARK_GRAY);
                addObject(text2, 200, 200);
                // example of creating a circular object
                text2.setButtonHeight(text2.getButtonWidth());
                text2.setImageAlpha(240);
                // example of indicating size before creating (using default colors)
                Button.defaultButtonHeight = 50;
                addObject(new Button("New menu", -2), 200, 300);
                break; // one of the active non-title button objects was clicked
        }
        // The following is just for the demo, although it is more example code 
        if (getObjects(null).isEmpty())
        {
            // change some of the button defaults
            Button.defaultRounded = true;
            Button.defaultTextColor = Color.BLACK;
            Button.defaultButtonColor = Color.WHITE;
            // show the next menu
            menuNumber = (menuNumber + 1) % MAX_MENU_CT; // go to the next menu (rotating)
            // The following selects each menu with different color buttons using both cancel settings,
            // and a deactivated button in one of them; examples of using the different constructors are given
            switch (menuNumber)
            {
                case 0: // example of making the menu cancellable by clicking on the title button
                    menu = new Menu(menu1, true);
                    addObject(menu, WIDE / 2, HIGH / 2);
                    // example of changing the background color of the menu option buttons
                    menu.setButtonColor(Color.CYAN);
                    break;
                case 1:
                    menu = new Menu(menu1, true);
                    addObject(menu, WIDE / 2, HIGH / 2);
                    menu.setButtonColor(Color.MAGENTA);
                    // example of deactivating a menu option button
                    menu.deactivateButton(4);
                    // example of making the menu buttons all the same size
                    menu.setSameSizeButtons();
                    break;
                case 2: // example of using the short constructor (menu cannot be cancelled by default)
                    menu = new Menu(menu2);
                    addObject(menu, WIDE / 2, HIGH / 2);
                    menu.setButtonColor(new Color(64, 224, 224));
                    break;
                case 3:
                    menu = new Menu(menu1, true);
                    addObject(menu, WIDE / 2, HIGH / 2);
                    menu.setButtonColor(new Color(224, 64, 224));
                    menu.setSameSizeButtons();
                    // example of changing the curvature of the sides of the menu buttons
                    menu.setButtonCircleFactor(24);
                    break;
                case 4:
                    menu = new Menu(menu1, false);
                    addObject(menu, WIDE / 2, HIGH / 2);
                    menu.setButtonColor(Color.PINK);
                    // example of making the menu buttons rectangular
                    menu.setRoundButtons(false);
                    break;
                case 5:
                    menu = new Menu(menu1);
                    addObject(menu, WIDE / 2, HIGH / 2);
                    // example of changing the text color of the menu buttons
                    menu.setTextColor(Color.RED);
                    // example of making the menu buttons more opaque
                    menu.setButtonAlpha(232);
                    break;
                case 6:
                    menu = new Menu(menu2);
                    addObject(menu, WIDE / 2, HIGH / 2);
                    menu.setTextColor(Color.BLUE);
                    menu.setSameSizeButtons();
                    menu.setRoundButtons(false);
                    break;
            }
            // example of changing the text of a menu button
            menu.setButtonText(0, "MENU " + menuNumber);
        }
    }
}
