package packages.ui;

import greenfoot.*;
public class Switch extends packages.ui.Button{
    private GreenfootImage on, off, clickedOn, clickedOff, hoveredOn, hoveredOff;
    
    public Switch(){
        super(generateImageOff(20), "", 1, false, null);
        int size = 20;
        on = generateImageOn(size);
        off = generateImageOff(size);
        GreenfootImage temp = new GreenfootImage(size * 2, size * 2);
        temp.setColor(Color.BLACK);
        temp.fill();
        temp.setTransparency(80);
        clickedOn = new GreenfootImage(on);
        clickedOn.drawImage(temp, 0, 0);
        clickedOff = new GreenfootImage(off);
        clickedOff.drawImage(temp, 0, 0);
        hoveredOn = new GreenfootImage(on);
        hoveredOn.scale((int)(on.getWidth() * 1.08), (int)(on.getHeight() * 1.08));
        hoveredOff = new GreenfootImage(off);
        hoveredOff.scale((int)(off.getWidth() * 1.08), (int)(off.getHeight() * 1.08));
    }

    public Switch(int size){
        super(generateImageOff(size), "", 1, false, null);
        on = generateImageOn(size);
        off = generateImageOff(size);
        GreenfootImage temp = new GreenfootImage(size * 2, size * 2);
        temp.setColor(Color.BLACK);
        temp.fill();
        temp.setTransparency(80);
        clickedOn = new GreenfootImage(on);
        clickedOn.drawImage(temp, 0, 0);
        clickedOff = new GreenfootImage(off);
        clickedOff.drawImage(temp, 0, 0);
        hoveredOn = new GreenfootImage(on);
        hoveredOn.scale((int)(on.getWidth() * 1.08), (int)(on.getHeight() * 1.08));
        hoveredOff = new GreenfootImage(off);
        hoveredOff.scale((int)(off.getWidth() * 1.08), (int)(off.getHeight() * 1.08));
    }
    
    private static GreenfootImage generateImageOff(int size){
        if(size < 10) size = 10;
        GreenfootImage image = generateBackgroundImage(size);
        image.drawImage(generateHandleImage(Color.RED.darker(), size), 1, 1);
        return image;
    }
    private static GreenfootImage generateImageOn(int size){
        if(size < 10) size = 10;
        GreenfootImage image = generateBackgroundImage(size);
        image.drawImage(generateHandleImage(Color.GREEN, size), size + 1, 1);
        return image;
    }
    private static GreenfootImage generateBackgroundImage(int size){
        GreenfootImage image = new GreenfootImage(2 * size, size);
        image.setColor(Color.LIGHT_GRAY);
        image.fill();
        image.setColor(Color.DARK_GRAY);
        image.drawRect(0, 0, 2 * size - 1, size - 1);
        return image;
    }
    private static GreenfootImage generateHandleImage(Color c, int size){
        GreenfootImage image = new GreenfootImage(size - 2, size - 2);
        image.setColor(c);
        image.fill();
        image.setColor(c.darker());
        image.drawRect(0, 0, size - 3, size - 3);
        return image;
    }
    
    public void onClick(){
        switchState();
    }
    
    public void setState(boolean state){
        if(state){
            setImage(on);
            image = on;
            clickedImage = clickedOn;
            hoveredImage = hoveredOn;
        }
        else{
            setImage(off);
            image = off;
            clickedImage = clickedOff;
            hoveredImage = hoveredOff;
        }
    }
    public void switchState(){
        setState(!getState());
        onSwitch(getState());
    }

    public void onSwitch(boolean state){}
    
    public boolean getState(){
        return getImage() == on || getImage() == clickedOn;
    }
}