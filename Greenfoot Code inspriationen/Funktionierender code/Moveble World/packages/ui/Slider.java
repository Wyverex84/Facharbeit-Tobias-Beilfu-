package packages.ui;

import packages.twoD.Vector;
import greenfoot.*;

/**
 * A slider that lets you select a value in a given range. May be set to only allow integers.
 * 
 * @author RcCookie
 * @version 1.0
 */
public class Slider extends Actor{
    private boolean useFractions = true;
    private final double min, max;
    private int length;
    
    private Vector slideVector;
    private Handle handle;
    
    private final Color sliderCol, handleCol;
    public Slider(){
        min = 0;
        max = 1;
        length = 100;
        sliderCol = Color.GRAY;
        handleCol = Color.LIGHT_GRAY;
        setup();
    }
    
    public Slider(double min, double max){
        this.min = min;
        this.max = max;
        length = 100;
        if(length < 10) length = 10;
        sliderCol = Color.GRAY;
        handleCol = Color.LIGHT_GRAY;
        setup();
    }
    
    public Slider(double min, double max, int length){
        this.min = min;
        this.max = max;
        this.length = length;
        if(length < 10) length = 10;
        sliderCol = Color.GRAY;
        handleCol = Color.LIGHT_GRAY;
        setup();
    }
    
    public Slider(double min, double max, int length, Color sliderCol, Color handleCol){
        this.min = min;
        this.max = max;
        this.length = length;
        if(length < 10) length = 10;
        this.sliderCol = sliderCol;
        this.handleCol = handleCol;
        setup();
    }
    
    private void setup(){
        handle = new Handle(this);
        setImage(generateSliderImage());
        slideVector = Vector.angledVector(0, length);
    }
    
    protected void addedToWorld(World w){
        w.addObject(handle, getX(), getY());
    }
    
    public void allowFractions(boolean useFractions){
        this.useFractions = useFractions;
    }
    
    public int getIntValue(){
        return (int)(getValue() + 0.5);
    }
    
    public double getValue(){
        if(handle.getWorld() == null) getWorld().addObject(handle, getX(), getY());
        double range = max - min;
        double sliderRange;
        double locFromSlider0;
        if(maxX() - minX() > maxY() - minY()){
            sliderRange = maxX() - minX();
            locFromSlider0 = handle.getX() - minX();
        }
        else{
            sliderRange = maxY() - minY();
            locFromSlider0 = handle.getY() - minY();
        }
        double percentage = locFromSlider0 / sliderRange;
        
        if(useFractions) return min + percentage * range;
        return (int)(min + percentage * range + 0.5);
    }
    
    @Override
    public void setRotation(int angle){
        super.setRotation(angle);
        handle.setRotation(angle);
        double oldValue = getValue();
        slideVector = Vector.angledVector(angle, length);
        setValue(oldValue);
    }
    @Override
    public void setLocation(int x, int y){
        double oldValue = getValue();
        super.setLocation(x, y);
        setValue(oldValue);
    }
    
    public void setValue(double value){
        double percentage = (value - min) / (max - min);
        handle.value = value;
        try{
            handle.setLocation(
                minX() + (int)(percentage * slideVector.x),
                minY() + (int)(percentage * slideVector.y)
            );
        }catch(Exception e){}
    }
    
    public int minX(){
        return getX() - (int)(slideVector.x * 0.5);
    }
    public int minY(){
        return getY() - (int)(slideVector.y * 0.5);
    }
    public int maxX(){
        return getX() + (int)(slideVector.x * 0.5);
    }
    public int maxY(){
        return getY() + (int)(slideVector.y * 0.5);
    }
    
    
    private class Handle extends packages.ui.Button{
        Vector offset;
        Slider s;
        public double value;
        public Handle(Slider s){
            super(generateHandleImage(s.handleCol), "", 1, false, null);
            this.s = s;
        }
        protected void addedToWorld(World w){
            value = s.getValue();
        }
        
        public void run(){
            s.setValue(value);
            if(offset != null){
                try{
                    MouseInfo mouse = Greenfoot.getMouseInfo();
                    setLocation(mouse.getX() + (int)offset.x, mouse.getY() + (int)offset.y);
                    if(getX() < minX()) setLocation(minX(), getY());
                    else if(getX() > maxX()) setLocation(maxX(), getY());
                    if(getY() < minY()) setLocation(getX(), minY());
                    else if(getY() > maxY()) setLocation(getX(), maxY());
                }catch(Exception e){
                    offset = null;
                }
            }
            value = getValue();
            setValue(value);
        }
        
        @Override
        public void onPress(){
            try{
                MouseInfo mouse = Greenfoot.getMouseInfo();
                offset = new Vector(getX() - mouse.getX(), getY() - mouse.getY());
            }catch(Exception e){
                pressed = false;
                offset = null;
            }
        }
        public void onRelease(){
            offset = null;
            //setValue((int)value);
        }
    }
    
    private GreenfootImage generateSliderImage(){
        GreenfootImage image = new GreenfootImage(length, 10);
        image.setColor(Color.DARK_GRAY);
        image.fill();
        image.setColor(sliderCol);
        image.fillRect(1, 1, length - 2, 8);
        return image;
    }
    private static GreenfootImage generateHandleImage(Color handleCol){
        GreenfootImage image = new GreenfootImage(10, 20);
        image.setColor(handleCol);
        image.fill();
        image.setColor(Color.DARK_GRAY);
        image.drawRect(0, 0, 9, 19);
        return image;
    }
}