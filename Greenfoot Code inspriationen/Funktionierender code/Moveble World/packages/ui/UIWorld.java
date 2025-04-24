package packages.ui;

import greenfoot.*;
import java.util.HashMap;
import packages.tools.Time;
public class UIWorld extends World{
    private HashMap<Actor, double[]> elements;
    private Time time;
    public UIWorld(int x, int y, int cellSize){
        super(x, y, cellSize);
        elements = new HashMap<Actor, double[]>();

        time = new Time();
        addObject(time, 0, 0);

        setPaintOrder(Text.class, Button.class, Slider.class, UIPanel.class);
    }
    
    public UIWorld(int x, int y, int cellSize, boolean bound){
        super(x, y, cellSize, bound);
        elements = new HashMap<Actor, double[]>();

        time = new Time();
        addObject(time, 0, 0);
    }
    
    public void add(Actor element, double x, double y){
        if(element == null) return;
        double[] loc = {x, y};
        elements.put(element, loc);
        addObject(
            element,
            (int)(getWidth() * elements.get(element)[0]),
            (int)(getHeight() * elements.get(element)[1])
        );
    }

    public void addFps(){
        if(getObjects(FpsDisplay.class).size()==0) add(new FpsDisplay(), .063, .03);
    }
    
    public void move(Actor element, double x, double y){
        if(element != null && elements.keySet().contains(element)){
            add(element, x, y);
        }
    }

    public Time time(){
        return time;
    }
}