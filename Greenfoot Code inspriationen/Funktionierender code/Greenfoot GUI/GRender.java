import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class GRender here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GRender  extends Actor
{
    private List<GCanvas> canvas = new ArrayList<GCanvas>();
    public void add(GCanvas g) {
        canvas.add(g);
    }
    
    public void remove(GCanvas g) {
        canvas.remove(g);
    }
    
    public GRender() {
        setImage(new GreenfootImage(1,1));
    }
    
    /**
     * Act - do whatever the GRender wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo m = Greenfoot.getMouseInfo();
        setLocation(0,0);
        GCanvas[] draw = canvas.toArray(new GCanvas[0]);
        for(int i=0;i<draw.length;i++) {
            draw[i].update();
            draw[i].setImage(draw[i].getPic());
            draw[i].handleInput(m,draw[i].getX(),draw[i].getY());
        }
    }    
}
