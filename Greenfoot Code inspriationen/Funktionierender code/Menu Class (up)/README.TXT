PROJECT TITLE: Menu Demo
PURPOSE OF PROJECT: Support classes: a 'Button' class with sub-classes of 'Text' and 'DelayButton' and a 'Menu' class that utilizes the
                    Button class for its title and options
VERSION or DATE: August 7, 2012
HOW TO START THIS PROJECT: Follow screen instruction
AUTHORS: danpost

TABLE OF CONTENTS:
  Menu API            Line  16
  Button API          Line  86
  Text API            Line 217
  DelayButton API     Line 253
  Marquiss API        Line 301
  BlinkText API       Line 344
  Instructions        Line 409

*****************************************************************************************
****************    Class 'Menu' extends Actor    ***************************************

Description:

     A class to create an image to cover the world and add button object over.  An array of text strings is passed to the constructor to use
     for the text of the button objects.  The first string in the array is the title text (i.e. 'MENU', 'MAIN MENU', 'OPTIONS', or something
     more specific; or it could even be 'Cancel', since it is what you click on to cancel the menu, if allowed).  The Menu class will create
     and hold an array of the button object, then while creating the buttons, number them and populate the array.  The buttons are numbered
     using the element number in the String array of the text that the button uses.
     
     PLEASE NOTE: The Menu class will NOT work alone.  The Button class MUST also be included for the Menu class to work.
     
                                          ****


Fields:

  Instance fields:                                                                                            Default value:         
        *private boolean canCancel: flag indicating whether the menu can be cancelled without selecting       false
                                    an option button by clicking on the title button
        *private String[] buttonText: the array of text strings to be used on the button objects              undefined
                                      (the first is the title button text)
        *private Button[] button: the array of Button objects that this menu has created                      undefined
        *private boolean wasSizedSame: flag indicating whether the buttons were similarly sized or not        false
        *private int menuAlpha: the alpha of the window tint menu object                                      96

                                          ****
        
Constructors:

    >Menu(String[] txtArray)

        *String[] 'txtArray': the text to display on the button objects that the Menu creates; the first entry is alway the title text

                                          ****

     >Menu(String[] txtArray, boolean cancelFlag)
                
        *String[] 'txtArray': as in previous constructors
        *boolean 'cancelFlag': a flag to indicate whether the menu can be exited without making a menu option selection by clicking on 
                               the title button

                                          ****

Methods:

    >public void activateButton(int btnNum): if 'btnNum' is a valid button number, and not equal to 0, activates that button
    
    >public void deactivateButton(int btnNum): if 'btnNum' is a valid button number, deactivates that button
    
    >public void removeMenu(): removes this menu and all of its associated buttons from the world
    
    >public void setButtonAlpha(int alfa): sets the image alpha of all menu buttons to 'alfa'
    
    >public void setButtonCircleFactor(int circFactor): sets the circle factor of all the menu buttons to 'circFactor'
    
    >public void setButtonColor(Color btnColor): sets the background color of all menu buttons to 'btnColor'
    
    >public void setButtonText(int btnNum, String newText): changes the text of the specified menu button to 'newText'
    
    >public void setMenuAlpha(int alfa): sets the image alpha of the menu (window tinting) to 'alfa'
    
    >public void setRoundButtons(boolean areRound): sets the state of the menu buttons images to 'areRound'
    
    >public void setSameSizeButtons(): sets the width of all menu buttons to that of the largest width
    
    >public void setTextColor(Color txtColor): sets the text color of all menu buttons to 'txtColor'
                                             
****************    end of class 'Menu'   ******************************************

***************    Class 'Button' extends Actor    *************************************

Description:

     A class to create a text/button object (an inactive text object or an active button object).  When an active button is clicked on,
     its number is stored in a class variable that the world can check on, using 'getSelected()'.  The only number that should not be used
     for an active button is -1, as that number is reserved for when no selection has been made or for an inactive button. If Menu objects
     are present, it is best to use negative values less than negative one (< -1) or values greater than twenty (> 20) for the button number.
     
     NOTE: If you do happen to use -1 for the button number, it will be treated as an inactive menu title button.
           Also, if you use 0 for a button number, you will lose some appearance changing abilities, as that number is reserved for the
           menu title button.
     
                                        ****

Fields:

   Class fields:
        *public static int selected: temporarily stores the button number of a clicked button (default value is -1, which indicates no active
             button was clicked on that has not been processed)
        *public static int defaultButtonHeight: this can be set prior to creating individual buttons; it refers to the height of the button object; 
             which, in turn, controls the font size (initial value at start-up is 50)
        *public static Color defaultButtonColor: initial default is Color.white
        *public static Color defaultTextColor: initial default is Color.black
        *public static Color defaultFrameColor: initial default is Color.black
        *public static int defaultImageAlpha: initial default is 192
        *public static int defaultFrameLineCount: initial default is 2
        *public static int defaultCircleFactor: initial default is 0
        *public static boolean defaultRounded: initial default is true
        *public static boolean defaultSolidFrame: initial default is false
        
  Instance fields:                                                                                            Default value:
        *private String text: the text String that is to be displayed on the button;                          ""
        *private Color buttonColor: the background color of the button                                        defaultButtonColor
        *private Color textColor: the color of the button text                                                defaultTextColor
        *private int buttonNumber: the number of the button; used to set the 'selected' field with            -1
        *private boolean mouseOver: tracks when mouse is found over the button object                         false
        *private boolean canClick: tracks whether the button is active or not                                 true
        *private int imageAlpha: the standard alpha value for the button image                                defaultImageAlpha
        *private int buttonHeight: the current height of the button image                                     defaultButtonHeight
        *private int buttonWidth: the current width of the button image                                       0
        *private int initialButtonWidth: the width of the button image when first created                     0
        *private int fontSize: the current font size (height) of the text for the button object               defaultButtonHeight * 4 / 5
        *private int frameLineCount: the number of lines to draw around the edge of the image                 defaultFrameLineCount
        *private Color frameColor: the current color of the button image frame                                defaultFrameColor
        *private boolean solidFrame: tracks the type of frame for the button image                            defaultSolidFrame
        *private boolean rounded: tracks the shape of the button image                                        defaultRounded
        *private int circleFactor: the change in the circular arc produced at the button image sides          defaultCircleFactor
                                   (minimum: -buttonWidth;  maximum: buttonHeight - 13)
        
                                        ****

Constructors:

    >Button(String btnText, int btnNumber)

        *String 'btnText': the text string to display on the button
        *int 'btnNumber': any unique integer value except -1 (see description above); a value of 0 will be treated as a title object
                             and will have its color set accordingly, and its value change to -1 if no cancel of menu is allowed
                        
        NOTES: It is the responsibility of the programmer to ensure that no two active buttons have the same button number
               (this could happen if you have two menus running at the same time, or a menu and a stand-alone button)
               
               This method will primarily be used by the Menu class; though can also be used to create stand-alone buttons.
              
                                         ****

    >Button(String text, int buttonNumber int textSize, Color txtColor, Color btnColor)
    
        *String 'btnText': the text string to display on the button
        *int 'btnNumber': any unique integer value except -1 (see description above)
        *int 'textSize': the font size to use for the button text
        *Color 'txtColor': the color to use for the button text
        *Color 'btnColor': the background color to use for the button object
        
                                         ****

Methods: 

    >public void activate(): sets the value of 'canClick' to true and updates its image; cannot be a title button object; it will allow the
                             user to be able to click on it

    >public void deactivate(): sets the value of 'canClick' to false and updates its image; it will no longer allow the user to click on it

    >public int getButtonHeight(): returns the current height of the button image
    
    >public int getButtonNumber(): returns the number assigned to the button object

    >public int getButtonWidth(): returns the current width of the button image

    >public int getFontSize(): returns the current font size of the button text
    
    >public int getImageAlpha(): returns the current button image alpha value
    
    >public int getSelected(): returns the number of the button clicked on, or, if no button was clicked, -1 (this method returns the value
                               of the static field 'selected' and resets it back to -1)

    >public String getText(): returns the current text of the button object

    >public void setButtonColor(Color btnColor): sets the background color of the button image to 'btnColor'

    >public void setButtonHeight(int btnHeight): sets the height of the button to 'btnHeight'; the value cannot be less than the font size of
                                                 the button text

    >public void setButtonWidth(int btnWidth): sets the width of the button to 'btnWidth'; the value cannot be less than the width of the
                                               original button image
    
    >public void setCircleFactor(int circFactor): sets the change amount of the circular arc produced on both sides of the button image
                                                  Both endpoints of both the top and bottom straight edges will move inward (-) or
                                                  outward (+) by 'circFactor' pixels (limited by size of image)

    >public void setFrameColor(Color frmColor): sets the color of the button image frame lines to 'frmColor'

    >public void setFrameLineCount(int lineCt): sets the number of lines to draw to make the frame of the button image (0, 1, or 2) to 'lineCt'

    >public void setFontSize(int fSize): sets the font size of the button text to 'fSize'

    >public void setImageAlpha(int alfa): sets the value for the alpha of the image of the button to 'alfa' (128 <= alfa <= 255)

    >public void setRounded(boolean isRound): sets the flag tracking whether the button image is rounded or squared to 'isRound'

    >public void setSolidFrame(boolean isSolid): sets the flag tracking whether the frame is solid or not to 'isSolid'

    >public void setText(String txt): sets the text of the button object to 'txt' and updates its image; the text for a title button object
                                      cannot be changed unless it is inactive

    >public void setTextColor(Color txtColor): sets the color of the button text to 'txtColor'
                                                  

******************    end of class 'Button'   *******************************************

****************    Class 'Text' extends Button    ***************************************

Description:

     A sub-class of the Button class used to create an inactive Text object
     
                                          ****

Fields:  All fields are inherited from the Button class

                                          ****
        
Constructors:

    >Text(String txt)

        *String 'txt': the text to display on the Text object
        
        *NOTE: this constructor calls the creation of a Button object with a button number of -1
               (making this object an inactive button object)

                                          ****

     >Text(String txt, int txtSize, Color txtColor, Color bgColor)
                
        *String 'txt': the text to display on the Text object
        *int 'txtSize': the font size to use for the text of the Text object
        *Color 'txtColor': the color to use for the text of the Text object
        *Color 'bgColor': the background color of the Text object

                                          ****

Methods:  This class only contain an empty act() method to over-ride mouse checking in the Button class

********************    end of class 'Text'   ******************************************

****************    Class 'DelayButton' extends Button    ***************************************

Description:

     A sub-class of the Button class giving the button a built-in timer used to delay its use
     (the button becomes inactive for a specified period of time (given in seconds), then re-activates)
     
                                          ****

Fields:  The following fields are added to those inherited from the Button class

  Instance fields:                                                                                            Default value:
        *private int delayTime: the total number of seconds to delay                                          1
        *private long lastSelectTime: the time that the delay was started, or, if not delaying, 0             0

                                          ****
        
Constructors: these constructors call the creation of a Button object; then sets up the delay state

    >DelayButton(String txt, int val, int delayAmt, boolean delaying)

        *String 'txt': the text to display on the Button object
        *int 'val': the number to be assigned to this Button object
        *int 'delayAmt': the total time in seconds to delay
        *boolean 'delaying': a flag indicating whether to delay at creation or not

                                          ****

     >DelayButton(String txt, int val, int txtSize, Color txtColor, Color btnColor, int delayAmt, boolean delaying)
                
        *String 'txt': the text to display on the Button object
        *int 'val': the number to be assigned to this Button object
        *int 'txtSize': the font size to use for the text of the Button object
        *Color 'txtColor': the color to use for the text of the Button object
        *Color 'btnColor': the background color of the Button object
        *int 'delayAmt': the total time in seconds to delay
        *boolean 'delaying': a flag indication whether to delay at creation or not

                                          ****

Methods:  This class contain an act() method that calls the Button class act() method; then,
          does its delay/timer checking
          
    >public void delay():  this method is called within the DelayButton class to initiate the delaying;
                           however, it can also be called from elsewhere

********************    end of class 'DelayButton'   ******************************************

****************    Class 'Marquiss' extends Button    ***************************************

Description:

     A sub-class of the Button class used to create an inactive scrolling text object
     
                                          ****

Fields:  The following fields are added to those inherited from the Button class

  Instance fields:                                                                                            Default value:
        *private int scrollPosition: the horizontal location on (or off) the button where the text starts           0
        *private int rate: the speed at which to text scrolls across the button (pixels per move)                   3
        *private GreenfootImage buttonImage: to hold the background button image                             null
        *private GreenfootImage textImage: to hold the text image                                            null

                                          ****
        
Constructors: these constructors call the creation of a inactive Button object

    >Marquiss(String txt, int speed)

        *String 'txt': the text to display on the Button object
        *int 'speed': the number of pixels to move each act cycles

                                          ****

    >Marquiss(String txt, int txtSize, Color txtColor, Color bgColor, int speed)
                
        *String 'txt': the text to display on the Button object
        *int 'txtSize': the font size to use for the text of the Button object
        *Color 'txtColor': the color to use for the text of the Button object
        *Color 'bgColor': the background color of the Button object
        *int 'speed': the number of pixels to move each act cycles

                                          ****

Methods:  This class contain an act() method that over-rides the Button class act() method.  It controls
    the scrolling text.  The class also contains an over-riding 'updateImage' method which is optimized
    for quickly creating the new image each cycle.
          
********************    end of class 'Marquiss'   ******************************************

****************    Class 'BlinkText' extends Button    ***************************************

Description:

     A subclass of the Button class used to create an inactive blinking text object.
     
                                          ****

Fields:  The following fields are added to those inherited from the Button class

  Instance fields:                                                                                            Default value:
        *private int delay: the number of act cycles between on and off blink states                                10
        *private int state: the current blink state ('0' is off and '1' is on)                                       0
        *private int counter: the act cycle counter; when equal to 'delay', changes blink state and resets           0
        *private Color[] textColors: the two text colors to alternate between                                 { null, null }

                                          ****
        
Constructors: these constructors call the creation of an inactive Button object; if the alternate color is not
    specified, the button background color is used to give an on text / off text look.

    >BlinkText(String txt, int delayAmt)

        *String 'txt': the text to display on the Button object
        *int 'delayAmt': the number of act cycles between changes in the text color

                                          ****

     >BlinkText(String txt, int txtSize, Color txtColor, Color bgColor, int delayAmt)
                
        *String 'txt': the text to display on the Button object
        *int 'txtSize': the font size to use for the text of the Button object
        *Color 'txtColor': the color to use for the text of the Button object
        *Color 'bgColor': the background color of the Button object
        *int 'delayAmt': the number of act cycles between changes in the text color

                                          ****

     >BlinkText(String txt, int delayAmt, Color altColor)
     
        *String 'txt': the text to display on the Button object
        *int 'delayAmt': the number of act cycles between changes in the text color
        *Color 'altColor': the alternate color of the text

                                          ****

     >BlinkText(String txt, int txtSize, Color txtColor, Color bgColor, int delayAmt, Color altColor)
     
        *String 'txt': the text to display on the Button object
        *int 'txtSize': the font size to use for the text of the Button object
        *Color 'txtColor': the color to use for the text of the Button color
        *Color 'bgColor': the background color of the Button object
        *int 'delayAmt': the number of act cycles between changes in the text color
        *Color 'altColor': the alternate color of the text

                                           ****

Methods:  This class contain an act() method that over-rides the Button class act() method and controls
    the color alternating for the text of the button object.
          
********************    end of class 'BlinkText'   ******************************************
***********************************************************************************************

INSTRUCTIONS: 

Sections:

(1) The Button class                      Line 426
    (a) Creating a text object            Line 459
    (b) Creating a button object          Line 483
    (c) Catching button clicks            Line 489
    (d) The DelayButton object            Line 512
(2) The Menu class                        Line 523
    (a) Creating a menu                   Line 529
    (b) Manipulating menu buttons         Line 578
    (c) Removing a menu                   Line 597
(3) Image manipulation                    Line 615

                                           ****

(1) The Button class
        Create a sub-class of Actor, name it 'Button', and copy/paste the Button class code 
        from this scenario into yours (if you are running Greenfoot 2.2.0 or later, you can
        copy/paste the JAVA file into the folder that the importable classes are located; mine
        is in Greenfoot>lib>greenfoot>common; once the JAVA file is there, you can select 'Edit'
        on the menu bar, and then select 'Import class...', or just press Ctrl-I, and import the
        class from there). Once you have the Button class in your scenario, you can start using
        it to create both text and button objects.
        
        Before we begin, an explanation is in order.  All of the class variables (static), except
        'selected', can be programmatically changed prior to creating Button objects.  This allows
        the programmer more ease in coding, in that the various methods do not have to be repeatedly
        used on multiple objects.  Just set the default value(s) and create as many objects as needed.
        All the new objects will be created with the new default(s). For example, say you want to create
        several same-sized rectangular buttons with a solid frame and red text on a cyan background, and
        display them across the top of the screen.
        
            Button.defaultButtonHeight = 30; // button height (font will be 30 * (4 / 5) = 24 by default)
            Button.defaultRounded = false; // rectangular
            Button.defaultTextColor = Color.red; // red text
            Button.defaultButtonColor = Color.cyan; // cyan background
            Button.defaultSolidFrame = true; // solid frame
            String[] btnText = { "Credits", "Start", "Info", "Options" }; // text array
            Button[] buttons = new Button[btnText.length]; // new button array
            int maxWide = 0; // to make same-sized buttons, we need width of widest button
            for (int i = 0; i < btnText.length; i++)
            {
                buttons[i] = new Button(btnText[i], i + 20);
                addObject(buttons[i], (getWidth() / btnText.length) / 2 + (getWidth() / btnText.length) * i, 18);
                maxWide = (int) Math.max(maxWide, buttons[i].getButtonWidth());
            }
            for (int i = 0; i < buttons.length; i++) buttons[i].setButtonWidth(maxWide); // same-sized buttons
        
  (a) Creating a text object
        You can create a text object through the Button class with:
        
            Button.defaultButtonHeight = 80; // sets the height for all new button objects created (and font-size of text = 64)
            Button button = new Button("YOU\nWIN", -1);
            button.deactivate();
            
        However, you lose the ability to change some of the images characteristics (mainly the background color).
        Therefore, the Text class (a sub-class of the Button class) was added to retain all the image manipulating
        features.  It accomplishes this by over-riding the act() of the Button class (an empty method in the Text
        class).  You can still use the default changing as described in (1) above to pre-set the characteristics
        of the Text objects.  To create the same button as above, you would write
        
            Button.defaultButtonHeight = 80;
            Text text = new Text("YOU\nWIN");
            
        If you used this code and added to object to the world, you would notice that the object will actually be
        160 pixels high, not 80.  That is because defaultButtonHeight is the height of the button for a one-liner.
        You could consider defaultButtonHeight to actually mean 'default textline height'.  Please note, however,
        that 'getButtonHeight' and 'setButtonHeight(int)' are absolutes (not textline heights).
        
        That is all there is to it (other than adding the object to the world)!  There are a variety of ways to 
        manipulate the image by way of method calls.  We will investigate those later.
        
  (b) Creating a stand-alone (non-menu) button object
        We saw an example of creating stand-alone buttons in (1) above.  If you had noticed, we used 'i + 20' for
        the value to assign as the button number.  We will explain that in the instructions on creating Menu objects.
        For now, let us just suffice to say that numbers between -1 and +19, inclusive, are in a danger zone and
        should not be used.
  
  (c) Catching button clicks
        You will need to add code in your sub-class of world to 'catch' button clicks.  This is rather 
        simple, as when a button is clicked on, its value is stored in the Button class variable 'selected'. 
        All the world needs to do in its 'act' method, is:
        
            switch (Button.getSelected())
            {
                case -1: break; // no action required (default value of 'selected' in the Button class)
                case -2: doSomething(); break; // some non-menu button was clicked
                case -3: doSomethingElse(); break; // some other non-menu button was clicked
                case 0: removeMenuObjects(); break; // menu title button was clicked (menu cancelled)
                case 1: actOnFirstMenuItem(); break; // first menu option was clicked
                etc.
            }
        
        Instead of having this code in the act() method, you could call 'checkButtonClicks()' from act and have
        the following method added to the class:
        
            private void checkButtonClicks()
            {
                // insert 'switch' code above
            }
            
    (d) The DelayButton object
        While creating my 'Private Messaging' scenario, I came in need of this one.  A button that has a built-in
        delay mechanism.  The button can be created with or without the delay being activated; and it can be 'called'
        to activate; however, it will always be activated upon the button being clicked on.
        To create a button that will not activate for 5 seconds, use the following code:
        
            Button delayButton = new DelayButton("Continue", -2, 5, true);
            
        The last two parameters are the duration of time to delay in seconds and the flag indicating that we want
        a delay to start immediately upon creation.
            
(2) The Menu class
        Import this class into your scenario, or create a sub-class of Actor, called 'Menu' and 
        copy/paste the code from this scenario into yours.  Follow similar steps as in (1) above.
        The menu object will create a backdrop (a tinted, framed window covering the entire
        scenario window).  The button objects will be placed over top of the tinted window.
        WARNING: the Menu class will NOT run on scenarios whose grid-size is greater than 1.
  (a) Creating a menu object
        Before creating a menu object, we need to brush up on arrays.  An array is a indexed list of
        items.  Each element can be accessed by its numerical index. Each element in the array
        is seperated from the next by commas; and each group of elements is enclosed within
        squiggley brackets '{' and '}'.  A one-dimensional array of String type elements could look
        like this:    { "one", "two", "three", "four" }     or this:  { "Jan", "Feb", "Mar" }
        It is rather easy to create an array of String type elements.  This is one way:
        
            String[] month;
            month = new String[12];
            month[0] = "Jan";
            month[1] = "Feb";
            etc.
            
        A faster way is:
         
            String[] month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", ... , "Dec" };
            
        You can refer to the whole array as 'month', or to an element of it as 'month[11]' which is "Dec".
        (Java counting starts at zero).  Sorry for the side-tracking, but it was neccessary for the
        creation of a menu.  The text that is to be displayed on each menu object (including the title
        object) is transferred to the menu class by way of its constructors as an array of String type
        elements.  So, you need to create an array of Strings whose elements are the text to be displayed
        on each of the button objects (the first one being the title object text).
        For example:  String[] dowText = { "DAY OF WEEK", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        Now you can create a menu that has buttons with the text elements in the String array:
        
            Menu dowMenu = new Menu(dowText);
            addObject(dowMenu, getWidth() / 2, getHeight() / 2);
         
        The Menu class object should always be centered in the world, at least until I update the
        class to change the Menu (backdrop) image itself.  The Menu class will create the button
        objects and number them, starting at zero; and store the objects in an array of Button
        objects. This will allow access to the Button objects by way of the Menu class to access
        the individual buttons for any changes (through the Menu class) you wish to make to them.
        The menu will assign each button its number (0 through array.length - 1), with the title button
        getting 0.  This number informs the Menu class of its special status (as a title button) and
        modifies the image of the button accordingly.  The numbers 1 up to the number of menu options
        are assigned to their respective buttons.  These numbers, 0 through 19 (for the menu buttons) 
        and -1 (default for no button) should be avoided when creating non-menu buttons (mainly to 
        avoid conflict).
       
        The other constructor for creating a Menu object adds a second parameter of type boolean (true/false).
        It tells the Menu class whether to allow the menu to be cancelled or not.  Cancelling is done by
        clicking on the title button, if allowed.  Cancelling is NOT allowed unless you use this constructor,
        and specify 'true' for allowing it:
        
            Menu dowMenu = new Menu(dowText, true);
        
  (b) Manipulating menu buttons
        NOTE: before changing the characteristics of menu buttons using methods in the Menu class, you MUST 
        first add the menu to the world.  You can still change the default values before creating the menu.
        
        As mentioned near the end of the last section, the Menu object saves references to the Button objects
        created for it.  The methods in the Menu class bridges the gap between you, the programmer, and the
        button objects created by it.  Let's say we did not want the user to choose a week-end day ("Sat" or "Sun")
        from the menu in the previous section.
        
            dowMenu.deactivateButton(1);
            dowMenu.deactivateButton(7);
            
        is all that needs added after adding the menu to the world.  Active buttons will enlarge and brighten when
        hovered over by the mouse.  Inactive buttons will show a medium gray color and not enlarge or brighten.
        A title button that is active will be a light gray (almost off-white) color.  Clicking on an active title
        button will exit (or cancel) the menu.  Activating a menu button is just as easy:
        
            dowMenu.activateButton(7);
            
  (c) Removing a menu
        You can remove all buttons and menu objects from the world, or, if you were smart enough to have saved the
        menu object in a variable, you could use the Menu class method 'removeMenu()' to remove it.  The method will
        remove all of its associated buttons and them remove itself from the world.  Let us say you declared an
        world instance variable (field)
        
            Menu menu = null;
            
        Then when you created the menu, you used
        
            menu = new Menu(dowText, true);
            
        Then, just use the following to remove the menu
        
            menu.removeMenu();
            menu = null;
            
        That is all there is to it!
                                               ****

(3) Image manipulation
        You are afforded plenty of ways to change the button images.  I show several different shapes for buttons
        in the demo scenario.  If you notice, there are only two actor classes in the whole thing.  So, all objects
        are either a Menu object (whose image is solely the framed window tint) or a Button object (everything else,
        except for the background fractal image, which is not an Actor at all).  
        EDIT: I have added several sub-classes of Button since the initial writing.  But, even so, the objects
        created are still Button objects.
        
        Menu buttons are somewhat restricted as far as image manipulation goes; but there is good reason for it
                                               ****
FUTURE UPDATES: Unknown.

        