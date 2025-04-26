import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.*;
/**
 * Write a description of class world3d here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class world3d extends World
{

    protected PolygonRenderer polygonRenderer;
    protected ViewWindow viewWindow;

    /**
     * Constructor for objects of class world3d.
     * 
     */
    public world3d()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        createPolygonRenderer();

        addObject(new Text(),getWidth()/2, getHeight()/2);
              
    }
    
    public void started(){
        addObject(new house3d(polygonRenderer,viewWindow),300, 200);
    }

    public void createPolygonRenderer() {
        // make the view window the entire screen
        viewWindow = new ViewWindow(0, 0,
            this.getWidth(), this.getHeight(),
            (float)Math.toRadians(75));

        Transform3D camera = new Transform3D(0,100,0);
        polygonRenderer = new SolidPolygonRenderer(
            camera, viewWindow);
    }

      
}
