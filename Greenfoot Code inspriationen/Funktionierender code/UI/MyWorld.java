import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

// This is used for the ui. IT REQUIRES YOU TO COPY THE 'rccookie' FOLDER INTO YOUR SCENARIO!

// If you want to import all, use
// import rccookie.greenfoot.ui.basic.*;
// import rccookie.greenfoot.ui.advanced.*;
// And for design and some more stuff
// import rccookie.greenfoot.ui.util.*;
import rccookie.greenfoot.ui.advanced.DropDownMenu;
import rccookie.greenfoot.ui.advanced.Switch;
import rccookie.greenfoot.ui.basic.IconButton;
import rccookie.greenfoot.ui.basic.Text;
import rccookie.greenfoot.ui.basic.TextButton;
import rccookie.greenfoot.ui.basic.UIWorld;
import rccookie.greenfoot.ui.util.Design;
import rccookie.greenfoot.ui.util.Theme;
import rccookie.greenfoot.ui.util.UIElement;

// Note that this uses 'UIWorld' which is not neccecary but offsers some nice methods (marked in the following)
public class MyWorld extends UIWorld {

    private static final long serialVersionUID = 1L;

    public MyWorld() {
        super(600, 400, 1);
        // Disables debug mode. Only neccecary if debug mode may have been enabled before
        Design.setDebug(false);
        // Enables dark mode on all ui elements
        Design.useDarkMode();
        // Adds an interactable fps display (UIWorld)
        addFps();
        // Like clicking 'run' on the Greenfoot interface
        Greenfoot.start();
        // Tells the world to color its background in the current style (UIWorld)
        colorBackground(true);

        // Adds a button that switches between dark mode and light mode (the 'add' method is part of UIWorld and adds the object at the relative location in the world)
        add(new TextButton("Switch mode").addClickAction(m -> {
            // Sets dark and light mode for all ui elements
            if(Design.getDefaultDesign().equals(Design.DARK_MODE)) Design.useLightMode();
            else Design.useDarkMode();
        }), 0.25, 0.5);
        // Adds a button that prints 'Click' into the console when cicked
        add(new TextButton("A Button").addClickAction(m -> System.out.println("Click")), 0.5, 0.5);
        // Adds a new drop down menu that prints the selected option into the console
        add(new DropDownMenu("Menu", new String[] {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"}).addSelectAction(s -> System.out.println(getObject(DropDownMenu.class).getSelection())), 0.75, 0.5);
        // Adds a switch that sets the design debug state to whatever state is is set to
        add(new Switch().addSwitchAction(state -> Design.setDebug(state)), 0.5, 0.75);

        // Adds some text
        add(new Text("UI"), 0.5, 0.25);
        // Adds some more text with the font size 15
        add(new Text("Debug Mode", 15), 0.5, 0.7);







        // More methods for a button: (These are examples and not displayed)

        // UIElement is the super class of all buttons, switches and other ui elements
        // IconButton is a button that shows a GreenfootImage
        UIElement iconButton = new IconButton(new GreenfootImage(1, 1));
        // Uses lambda to print the x coordinate of the mouse when clicked
        iconButton.addClickAction(mouse -> System.out.println("Mouse x is " + mouse.getX()));

        TextButton button = new TextButton("Some title");
        // Sets the buttons visual theme
        button.setDesign(new Design(new Theme(Color.RED, Color.BLUE), new Theme(Color.YELLOW, Color.ORANGE)));
        button.setDrawOutline(true); // Enables a visual outline for the button
        button.setEnabled(false); // Disables the buttons functionallity
        button.setMaxHeight(30);
        button.setMinWidth(100);
        button.setOutlineWidth(3);
        button.setTitle("Some new title");
        button.setUseBigBorder(false); // Big border is the normal border, it means the border from thext to edge
        button.getText().setFontSize(30); // Sets the font size of the text object behind the button
        button.addPressAction(m -> System.out.println("Started pressing")); // Runs whenever the mouse starts pressing the button
        button.addReleaseAction(m -> System.out.println("Ended pressing")); // Runs whenever the mouse releases, also when that is not considered a click
    }
}
