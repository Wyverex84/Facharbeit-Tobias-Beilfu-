import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WorldX here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WorldX  extends World
{
    private GRender render = new GRender();

    /**
     * Constructor for objects of class WorldX.
     * 
     */
    public WorldX()
    {    
        // Create a new world with 20x20 cells with a cell size of 10x10 pixels.
        super(600, 480, 1);
        
        GScrollPane sp = new GScrollPane(290,230,2);
        for(int i =0;i<100;i++) sp.add(new Square());
        
        GFrame frame = new GFrame("HELLO","Window",300,240);
        frame.add(sp);
        
        addObject(frame,300,240);
        addObject(render,0,0);
    }
    
    public void addObject(Actor a,int x, int y) {
        if(a instanceof GCanvas) render.add((GCanvas) a);
        super.addObject(a,x,y);
    }
    
    public void removeObject(Actor a) {
        if(a instanceof GCanvas) render.remove((GCanvas) a);
        super.removeObject(a);
    }
        
}
