import greenfoot.*;
import java.io.*; // several classes from this package are needed, so the entire package is made available

/**
 * Class TextFileViewer:  a subclass of World that will display the contents of a text file.
 */
public class TextFileViewer extends World
{
    World gotoWorld; // the world to activate when leaving the text file display (this world)
    int scroll = 0; // to track the amount of scrolling of the text
    int loY, hiY, range; // to hold the limits and range of the scrollbar handle
    int lineHeight, maxScroll; // to hold the height of a single line and maximum value of the 'scroll'field
    Actor page, handle; // to hold references to the text panel and the scrollbar handle
    
    /**
     * TextFileViewer Constructor: reads the text file, builds the text panel, the scrollbar image and the scrollbar handle
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
            InputStream input = getClass().getClassLoader().getResourceAsStream(filename); // open input stream
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
        GreenfootImage text = new GreenfootImage(" ", 20, Color.BLACK, new Color(0, 0, 0, 0)); // create a sammple text image
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
            GreenfootImage lineImg = new GreenfootImage(lines.get(i), 20, tColor, new Color(0, 0, 0, 0)); // create line image for size
            int pos = lines.get(i).indexOf("//"); // determine comment position
            if (pos >= 0)
            {
                // split line up
                GreenfootImage starting = new GreenfootImage(lines.get(i).substring(0, pos), 20, Color.BLACK, new Color(0, 0, 0, 0));
                GreenfootImage ending = new GreenfootImage(lines.get(i).substring(pos), 20, Color.GRAY, new Color(0, 0, 0, 0));
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