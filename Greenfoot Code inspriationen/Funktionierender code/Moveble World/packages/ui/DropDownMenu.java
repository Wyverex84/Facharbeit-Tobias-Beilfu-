package packages.ui;

import greenfoot.*;

/**
 * The drop.down menu opens a list of options when clicked and saves the selected one.
 * 
 * @author RcCookie
 * @version 1.0
 */
public class DropDownMenu extends packages.ui.Button{

    /**
     * The different buttons of the options shown when opened.
     */
    DropDownButton[] options;

    /**
     * The background that pops up behind the opened menu.
     */
    Background background;

    /**
     * The name of the menu, shown as default selection.
     */
    String name;


    /**
     * Constructs a new drop-down menu with the given default name and the given options.
     * 
     * @param name The name of the menu
     * @param options The options to choose from, as strings
     */
    public DropDownMenu(String name, String[] options){
        super(150, 35, name, 15, Color.LIGHT_GRAY, null);
        this.name = name;
        this.options = new DropDownButton[options.length];
        for(int i=0; i<options.length; i++){
            this.options[i] = new DropDownButton(150, 35, options[i], 15, Color.LIGHT_GRAY, this);
        }
        background = new Background(this);
    }

    /**
     * Constructs a new drop-down menu with the given default name and the given options in the given background color.
     * 
     * @param name The name of the menu
     * @param options The options to choose from, as strings
     * @param color The background color of the menu buttons
     */
    public DropDownMenu(String name, String[] options, Color color){
        super(150, 35, name, 15, color, null);
        this.color = color;
        this.name = name;
        this.options = new DropDownButton[options.length];
        for(int i=0; i<options.length; i++){
            this.options[i] = new DropDownButton(150, 35, options[i], 15, color, this);
        }
        background = new Background(this);
    }

    /**
     * Constructs a new drop-down menu with the given default name and the given options in the given size.
     * 
     * @param name The name of the menu
     * @param options The options to choose from, as strings
     * @param x The width of the menu
     * @param y The height of the closed menu / of every option button
     */
    public DropDownMenu(String name, String[] options, int x, int y){
        super(x, y, name, 15, Color.LIGHT_GRAY, null);
        this.name = name;
        this.options = new DropDownButton[options.length];
        for(int i=0; i<options.length; i++){
            this.options[i] = new DropDownButton(x, y, options[i], 15, Color.LIGHT_GRAY, this);
        }
        background = new Background(this);
    }

    /**
     * Constructs a new drop-down menu with the given default name and the given options in the given size
     * with the given background color. The text will be displayed in the specified font size.
     * 
     * @param name The name of the menu
     * @param options The options to choose from, as strings
     * @param x The width of the menu
     * @param y The height of the closed menu / of every option button
     * @param color The background color of the menu buttons
     * @param fontSize The font size of any text on the menu
     */
    public DropDownMenu(String name, String[] options, int x, int y, Color color, int fontSize){
        super(x, y, name, fontSize, color, null);
        this.color = color;
        this.name = name;
        this.options = new DropDownButton[options.length];
        for(int i=0; i<options.length; i++){
            this.options[i] = new DropDownButton(x, y, options[i], fontSize, color, this);
        }
        background = new Background(this);
    }
    

    /**
     * Checkes every frame weather the mouse has been pressed onto something else. If it has been, it
     * is going to close the menu.
     */
    public void run(){
        try{
            if(pressedOnSomethingElse()){
                clickCount++;
                close();
            }
        }catch(Exception e){}
    }


    /**
     * Checks weather the menu is open and if the mouse has been pressed. If it was, it will return
     * false if that press was on a part of the menu, otherwise it will return true.
     * 
     * @return If the mouse has been pressed onto something else while the menu was open
     */
    private boolean pressedOnSomethingElse(){
        if(background.getWorld() == null) return false;
        if(!Greenfoot.mousePressed(null)) return false;
        try{
            Actor touched = Greenfoot.getMouseInfo().getActor();
            if(touched == this) return false;
            if(touched == background) return false;
            for(DropDownButton b : options){
                if(touched == b) return false;
            }
        }catch(Exception e){}
        return true;
    }


    /**
     * Opens the selection of options below the menu button and underlays the background.
     */
    private void open(){
        setTitle(name);
        getWorld().addObject(background, 0, 0);
        for(int i=0; i<options.length; i++){
            getWorld().addObject(
                options[i],
                getX(),
                getY() + getImage().getHeight() * (i + 1)
            );
        }
    }


    /**
     * Closes the selection by removing the option buttons and the background. The menu
     * name will be set to the selected option.
     * 
     * @param selected The option that was selected
     */
    private void close(String selected){
        setTitle(selected);
        getWorld().removeObject(background);
        for(DropDownButton b : options) getWorld().removeObject(b);
    }

    /**
     * Closes the selection by removing the option buttons and the background. The menu
     * name will be set to the default option name.
     */
    private void close(){
        setTitle(name);
        getWorld().removeObject(background);
        for(DropDownButton b : options) getWorld().removeObject(b);
    }


    /**
     * Sets the location of itself and, if neccecary, of the options buttons and the background.
     * 
     * @param x The new x location of the main button
     * @param y The new y location of the main button
     */
    @Override
    public void setLocation(int x, int y){
        super.setLocation(x, y);
        if(background.getWorld() != null){
            close();
            open();
        }
    }
    

    /**
     * When th menu if open, it closes and shows the default option. If it was closed, it opens.
     * Called by the button class whenever the main button was clicked.
     */
    @Override
    public void onClick(){
        if(clickCount % 2 == 1) open();
        else close();
    }


    /**
     * Closes the menu to the given option.
     * 
     * @param name The option name to close to
     */
    private void buttonClicked(String name){
        clickCount++;
        close(name);
        onClick(name);
    }


    /**
     * Executed whenever an option button was pressed. Override to do something depending on the
     * selected option.
     * 
     * @param buttonName The name of the option selected
     */
    public void onClick(String buttonName){}
    

    /**
     * Returns the name of the currently selected option. May return the default option name.
     * @return The currently selected option
     */
    public String selected(){
        return getTitle();
    }


    /**
     * Weather there has been an option selected, or it is (still) the default option.
     * 
     * @return False if an option was selected
     */
    public boolean answered(){
        return getTitle() != name;
    }
    
    

    /**
     * A class without functionality that serves as a background for the menu.
     */
    private class Background extends Actor{
        /**
         * The menu this background belongs to.
         */
        DropDownMenu menu;

        /**
         * The number of pixels the background is standing out behind the buttons in every direction.
         */
        private static final int OUTLINE = 2;


        /**
         * Constructs a new background that automaticly generates a fitting image.
         * 
         * @param menu The menu this boackground belongs to
         */
        public Background(DropDownMenu menu){
            this.menu = menu;
            GreenfootImage image = new GreenfootImage(
                menu.getImage().getWidth() + OUTLINE * 2,
                menu.getImage().getHeight() * (options.length + 1) + OUTLINE * 2
            );
            image.setColor(Color.GRAY);
            image.fill();
            setImage(image);
        }


        /**
         * Called when added to the world. The background will automaticly go to the approptiate location.
         * 
         * @param w The world added to
         */
        protected void addedToWorld(World w){
            setLocation(
                menu.getX(),
                menu.getY() + menu.getImage().getHeight() * options.length / 2 + 1
            );
            getWorld().setPaintOrder(packages.ui.Button.class);
        }
    }
    

    /**
     * A type of button that executes a certain method on the main menu whenever clicked.
     */
    private class DropDownButton extends packages.ui.Button{
        /**
         * The menu this button belongs to.
         */
        DropDownMenu menu;


        /**
         * Constructs a new drop-down button with the given specifications.
         * 
         * @param x The width of the button
         * @param y The height of the button
         * @param name The text on the button
         * @param fontSize The font size of the text on the button
         * @param color The background color of the button
         * @param menu The menu this button belongs to
         */
        public DropDownButton(int x, int y, String name, int fontSize, Color color, DropDownMenu menu){
            super(x, y, name, fontSize, color, null);
            this.menu = menu;
        }
        

        /**
         * Called whenever the button was clicked. Calls a certain function in the main class
         * to notify it that it was pressed.
         */
        @Override
        public void onClick(){
            menu.buttonClicked(getTitle());
        }
    }
}