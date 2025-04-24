import greenfoot.*;
import java.io.*; // several classes from this package are needed, so the entire package is made available

/**
 * Class TextFileViewer:  a subclass of World that will display the contents of a text file.
 * 
 * TO ADD BUTTONS TO IN YOUR WORLD TO VIEW FILES:
 *  (1) Add Actor fields to your world class to hold the buttons and one more to hold which one (if any) 
 *      the mouse happens to be over
 *      
 *          EXAMPLE:
 *  Actor btnFloor, btnPortal, btnMan, btnText, mouseButton;
 *  
 *  (2) Add a method in your world class to create and return a button object
 *  
 *          EXAMPLE: 
 *  / **
 *    * Method getNewButton: creates an Actor object with a button image with the caption given; the
 *    * image of the button is given the ability to change intensity as the mouse moves on and off it.
 *    *
 *    * @param caption the text to display on the button
 *    * @return the button Actor object created
 *    * /
 *  private Actor getNewButton(String caption)
 *  {
 *      // create the image for the actor not yet created
 *      GreenfootImage base = new GreenfootImage(120, 30); // create the base image for the button
 *      base.fill(); // fill with black (default drawing color) to be used for the frame
 *      base.setColor(new java.awt.Color(192, 192, 255)); // set drawing color to a light blue
 *      base.fillRect(3, 3, 114, 24); // fill background of button leaving the outer frame
 *      GreenfootImage text = new GreenfootImage(caption, 24, java.awt.Color.black, null); // create text image
 *      base.drawImage(text, 60-text.getWidth()/2, 15-text.getHeight()/2); // draw text image onto base image
 *      base.setTransparency(128); // adjust the intensity of the image to 'mouse not over' state
 *      Actor button = new Actor() // create the Actor
 *      {
 *          / **
 *            * Method act (for button Actor): changes intensity of image for mouse hovering action
 *            * /
 *          public void act()
 *          {
 *              // gaining mouse hover
 *              if (mouseOn == null && Greenfoot.mouseMoved(this))
 *              {
 *                  mouseOn = this;
 *                  getImage().setTransparency(255);
 *              }
 *              // losing mouse hover
 *              if (mouseOn == this && Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this))
 *              {
 *                  mouseOn = null;
 *                  getImage().setTransparency(128);
 *              }
 *          }
 *      };
 *      button.setImage(base); // give button its image
 *      return button; // return the button Actor object
 *  }
 *  
 *  (3) create and add button actors into the world in your world constructor saving them in the fields
 *  
 *      EXAMPLE:
 *  // add button objects that can be clicked to show source code of classes
 *  addObject(btnFloor = getNewButton("Floor"), 160, 550); // sets button field and adds button to world
 *  addObject(btnPortal = getNewButton("Portal"), 320, 550); // sets button field and adds button to world
 *  addObject(btnMan = getNewButton("Man"), 480, 550); // sets button field and adds button to world
 *  addObject(btnText = getNewButton("Text"), 640, 550); // sets button field and adds button to world
 *
 *  (4) add code in your world act method to detect button mouse clicks and initiate the text viewing worlds
 *  
 *      EXAMPLE:
 *  if (Greenfoot.mouseClicked(btnMan)) Greenfoot.setWorld(new TextFileViewer("Man.txt", this));
 *  if (Greenfoot.mouseClicked(btnFloor)) Greenfoot.setWorld(new TextFileViewer("Floor.txt", this));
 *  if (Greenfoot.mouseClicked(btnPortal)) Greenfoot.setWorld(new TextFileViewer("Portal.txt", this));
 *  if (Greenfoot.mouseClicked(btnText)) Greenfoot.setWorld(new TextFileViewer("Text.txt", this));
 */
public class TextFileViewer extends World
{
    static final Color TRANS = new Color(0, 0, 0, 0);
    
    World gotoWorld; // the world to activate when leaving the text file display (this world)
    int scroll = 0; // to track the amount of scrolling of the text
    int loY, hiY, range; // to hold the limits and range of the scrollbar handle
    int lineHeight, maxScroll; // to hold the height of a single line and maximum value of the 'scroll'field
    Actor page, handle; // to hold references to the text panel and the scrollbar handle
    
    /**
     * TextFileViewer Constructor: reads the text file, builds the text panel, the scrollbar image and the
     * scrollbar handle
     *
     * @param filename the path/filename of the text file to read in
     * @param world the World object to activate when leaving this world
     */
    public TextFileViewer(String filename, World world)
    {
        super(800, 600, 1, false); // creates the base world
        gotoWorld = world; // saves to World object to activate when leaving this world
        /** read in text file */
        java.util.List<String> lines = new java.util.ArrayList(); // creates a list object for the lines of text read
        BufferedReader br = null; // sets up a local field for the BufferedReader object
        // attempt to open an inputstream to the file given
        try
        {
            InputStream input = getClass().getClassLoader().getResourceAsStream(filename); // create and open input stream
            br = new BufferedReader(new InputStreamReader(input)); // wrap the stream within a BufferedReader object
        }
        catch (Exception e) { System.out.println("Scenario file missing"); return; } // for failure to open stream
        // attempt to read in the lines of text
        try
        {
            String line = null; // sets up a local field for each line of text
            while ((line = br.readLine()) != null)  lines.add(line); // read each line and add them to the 'lines' list
            br.close(); // close the BufferedReader object
        }
        catch (Exception e) { try { br.close(); } catch (Exception f) {} } // close file if read error occurred
        /** create text file image */
        GreenfootImage text = new GreenfootImage(" ", 20, Color.BLACK, TRANS); // create a sammple text image
        lineHeight = text.getHeight(); // get pixel height of sample text image
        GreenfootImage pane = new GreenfootImage (800, lineHeight*lines.size()); // create appropriate size image for text
        maxScroll = (pane.getHeight()-600)/lineHeight; // determine maximum scrolling amount
        if (maxScroll < 0) maxScroll = 0; // maximum amount cannot be less than minimum amount (zero)
        Color tColor = null; // to hold text color
        for (int i=0; i<lines.size(); i++) // draw text lines onto pane
        {
            if ("".equals(lines.get(i).trim())) continue;
            tColor = Color.BLUE; // set color to documentation
            if ("*/".indexOf(""+lines.get(i).trim().charAt(0)) < 0) tColor = Color.BLACK; // not documentation
            GreenfootImage lineImg = new GreenfootImage(lines.get(i), 20, tColor, TRANS); // create line image for size
            int pos = lines.get(i).indexOf("//"); // determine comment position
            if (pos >= 0)
            {
                // split line up
                GreenfootImage starting = new GreenfootImage(lines.get(i).substring(0, pos), 20, Color.BLACK, TRANS);
                GreenfootImage ending = new GreenfootImage(lines.get(i).substring(pos), 20, Color.GRAY, TRANS);
                lineImg.clear(); // clear line image
                // re-create line image using to different colored text images
                lineImg.drawImage(starting, 0, 0);
                lineImg.drawImage(ending, starting.getWidth(), 0);
            }
            pane.drawImage(lineImg, 5, lineHeight*i); // draw line on pane
        }
        /** create Actor to display text */
        page = new Actor(){}; // create an Actor object to apply image to
        page.setImage(pane); // set image of page actor to the text pane
        addObject(page, 400,  Math.min(pane.getHeight()/2, 300+page.getImage().getHeight()/2)); // add page to world
        /** create scrollbar handle image */
        int handleHeight = 600*600/pane.getHeight(); // determine height of scrollbar handle
        if (handleHeight >= 600) return; // if handle fills the entire height of the screen, then no scrolling is needed
        GreenfootImage hImg = new GreenfootImage(16, handleHeight);
        hImg.setColor(Color.GRAY);
        hImg.fill();
        /** determine limits (and range) of scrollbar handle */
        loY = hImg.getHeight()/2;
        hiY = 600-hImg.getHeight()/2;
        range = hiY-loY;
        /** add scrollbar slide image to background of world */
        GreenfootImage sbImg = new GreenfootImage(20, 600);
        sbImg.setColor(Color.LIGHT_GRAY);
        sbImg.fill();
        sbImg.setColor(Color.DARK_GRAY);
        sbImg.drawRect(2, 2, 16, 596);
        getBackground().drawImage(sbImg, 779, 0);
        /** create the actor for the scrollbar handle */
        handle = new Actor()
        {
            /**
             * Method act (for scrollbar handle): handles mouse and keyboard events for scrolling of the text pane.
             */
            public void act()
            {
                // handle mouse dragging
                if (Greenfoot.mouseDragged(this)) setLocation(getX(), Greenfoot.getMouseInfo().getY());
                // handle keyboard events
                if (Greenfoot.isKeyDown("up")) setLocation(getX(), getY()-1);
                if (Greenfoot.isKeyDown("down")) setLocation(getX(), getY()+1);
                // maintain scrollbar handle within limits
                if (getY() < loY) setLocation(getX(), loY);
                if (getY() > hiY) setLocation(getX(), hiY);
                int ds = maxScroll*(getY()-loY)/range-scroll; // determine amount of change in scrolling
                page.setLocation(page.getX(), page.getY()-ds*lineHeight); // adjust text pane location
                scroll += ds; // adjust scroll amount
           }
        };
        handle.setImage(hImg); // set the image of the handle
        addObject(handle, 790, loY); // add handle to the world
    }
    
    /**
     * Method act; handles keyboard events for leaving the text viewing world
     */
    public void act()
    {
        String key = Greenfoot.getKey();
        if ("escape".equals(key) || "x".equals(key)) Greenfoot.setWorld(gotoWorld);
    }
}