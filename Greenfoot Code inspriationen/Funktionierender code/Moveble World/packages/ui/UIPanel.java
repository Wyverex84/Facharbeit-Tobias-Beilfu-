package packages.ui;

import greenfoot.*;
import java.util.HashMap;
public class UIPanel extends Actor{
    private HashMap<Actor, double[]> elements;
    private int x, y;
    private boolean coversWorld;
    
    public UIPanel(World cover){
        x = cover.getWidth();
        y = cover.getHeight();
        elements = new HashMap<Actor, double[]>();
        setImage(new GreenfootImage(x, y));
        coversWorld = true;
    }
    
    public UIPanel(int x, int y, Color color){
        this.x = x;
        this.y = y;
        elements = new HashMap<Actor, double[]>();
        GreenfootImage image = new GreenfootImage(x, y);
        image.setColor(color);
        image.fill();
        setImage(image);
        coversWorld = false;
    }
    
    public UIPanel(World cover, Color color){
        x = cover.getWidth();
        y = cover.getHeight();
        elements = new HashMap<Actor, double[]>();
        GreenfootImage image = new GreenfootImage(x, y);
        image.setColor(color);
        image.fill();
        setImage(image);
        coversWorld = true;
    }
    
    public UIPanel(int x, int y, GreenfootImage image){
        this.x = x;
        this.y = y;
        elements = new HashMap<Actor, double[]>();
        setImage(image);
        coversWorld = false;
    }
    
    public UIPanel(World cover, GreenfootImage image){
        x = cover.getWidth();
        y = cover.getHeight();
        elements = new HashMap<Actor, double[]>();
        setImage(image);
        coversWorld = true;
    }
    
    protected void addedToWorld(World w){
        updateElements();
        if(coversWorld) setLocation(x / 2, y / 2);
    }
    
    public void add(Actor element, double x, double y){
        if(element == null) return;
        double[] loc = {x, y};
        elements.put(element, loc);
        if(getWorld() != null){
            getWorld().addObject(
                element,
                getX() - (this.x / 2) + (int)(this.x * elements.get(element)[0]),
                getY() - (this.y / 2) + (int)(this.y * elements.get(element)[1])
            );
        }
    }
    
    public void move(Actor element, double x, double y){
        if(element != null && elements.keySet().contains(element)){
            add(element, x, y);
        }
    }
    
    public void resize(int x, int y){
        this.x = x;
        this.y = y;
        GreenfootImage image = getImage();
        image.scale(x, y);
        setImage(image);
        updateElements();
    }
    
    private void updateElements(){
        for(Actor element : elements.keySet()){
            if(element.getWorld() == null){
                getWorld().addObject(
                    element,
                    getX() - (x / 2) + (int)(x * elements.get(element)[0]),
                    getY() - (y / 2) + (int)(y * elements.get(element)[1])
                );
            }
            else{
                element.setLocation(
                    getX() - (x / 2) + (int)(x * elements.get(element)[0]),
                    getY() - (y / 2) + (int)(y * elements.get(element)[1])
                );
            }
        }
    }
    
    @Override
    public void setLocation(int x, int y){
        if(x == getX() && y == getY()) return;
        coversWorld = false;
        super.setLocation(x, y);
        updateElements();
    }
    
    @Override
    public void setRotation(int angle){
        System.out.println("Cannot change angle of ui plane!");
    }
}