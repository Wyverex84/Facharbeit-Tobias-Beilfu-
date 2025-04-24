import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**This is part of PiRocks' greenfoot gui package.
 * 
 * @author PiRocks
 * @version 1.0
 */
public class GContainer  extends GComponent
{
    /**Elements in this container*/
    private List<GComponent> comps = new ArrayList<GComponent>();

    /**
     * Add a component to the container.
     */
    public void add(GComponent g) {
        comps.add(g);
    }
    
    /**
     * Remove a component from the container.
     */
    public void remove(GComponent g) {
        comps.remove(g);
    }
    
    /**
     * how many elements are in this container.
     */
    public int elementCount() {
        return comps.size();
    }
    
    /**
     * GEts an array of all the elements in the container.
     */
    public GComponent[] getElements() {
        return comps.toArray(new GComponent[0]);
    }
    
    public void update() {
        GComponent[] draw = getElements();
        int y = 0;
        int index = 0;
        while(index < draw.length) {
            int x = 0;
            int yincr = 0;
            for(int i = 0; i < 3;i++) {
                try {getDrawPic().drawImage(draw[index].getDrawPic(),x,y);
                    x+=draw[index].getDrawPic().getWidth();
                    if(draw[index].getDrawPic().getHeight() > yincr) yincr = draw[index].getDrawPic().getHeight();
                } catch(Exception e) { }//If the elements don't comepletely fill up the last row, we will get a few ArrayIndexOutOfBoundsExceptions. 
                index++;
            }
            y+=yincr;
        }
            
    }
}
