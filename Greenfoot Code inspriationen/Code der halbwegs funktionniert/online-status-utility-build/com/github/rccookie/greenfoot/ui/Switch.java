package com.github.rccookie.greenfoot.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.rccookie.greenfoot.core.Color;
import com.github.rccookie.greenfoot.core.Image;
import com.github.rccookie.greenfoot.ui.util.Interactable;


public class Switch extends Interactable {

    private final int size;

    private boolean state = false;

    private final List<Consumer<Boolean>> switchActions = new ArrayList<>();
    
    public Switch() {
        this(20);
    }

    public Switch(int size) {
        this.size = size;
        addOnClick(() -> switchState());
    }



    private Image generateImageOff(int size) {
        if(size < 10) size = 10;
        Image image = generateBackgroundImage(size);
        image.drawImage(generateHandleImage(Color.RED.darker(), size), 1, 1);
        return image;
    }

    private Image generateImageOn(int size) {
        if(size < 10) size = 10;
        Image image = generateBackgroundImage(size);
        image.drawImage(generateHandleImage(Color.GREEN, size), size + 1, 1);
        return image;
    }

    private Image generateBackgroundImage(int size) {
        Image image = new Image(2 * size, size);
        image.setColor(elementColor("background"));
        image.fill();
        image.setColor(Color.DARK_GRAY);
        image.drawRect(0, 0, 2 * size - 1, size - 1);
        return image;
    }

    private Image generateHandleImage(Color c, int size) {
        Image image = new Image(size - 2, size - 2);
        image.setColor(c);
        image.fill();
        image.setColor(c.darker());
        image.drawRect(0, 0, size - 3, size - 3);
        return image;
    }

    public void setState(boolean state) {
        this.state = state;
        imageChanged();
        updateImageSelection();
    }

    public void switchState(){
        setState(!getState());
        onSwitch(getState());
    }


    @Override
    protected Image createMainImage() {
        return state ? generateImageOn(size) : generateImageOff(size);
    }


    protected void onSwitch(boolean state) {
        for(Consumer<Boolean> action : switchActions) action.accept(state);
    }
    
    public boolean getState(){
        return state;
    }


    public Switch addSwitchAction(Consumer<Boolean> newState) {
        if(newState == null) return this;
        switchActions.add(newState);
        return this;
    }

    public Switch removeSwitchAction(Consumer<Boolean> action) {
        switchActions.remove(action);
        return this;
    }

    @Override
    protected void assignDefaultColorMappings() {
        mapColor("background", 0, false);
    }
}