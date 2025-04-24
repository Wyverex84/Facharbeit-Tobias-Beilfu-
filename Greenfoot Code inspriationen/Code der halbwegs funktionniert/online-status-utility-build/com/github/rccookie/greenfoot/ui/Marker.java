package com.github.rccookie.greenfoot.ui;

import java.util.Objects;

import com.github.rccookie.greenfoot.core.Color;
import com.github.rccookie.greenfoot.core.GameObject;
import com.github.rccookie.greenfoot.core.Image;
import com.github.rccookie.greenfoot.ui.util.Theme;
 
public class Marker extends GameObject {

    private static final int BORDER = 2;

    private GameObject object;
    
    private Image lastImage = null;
    private double lastAngle;

    private Theme theme = new Theme(Color.RED);


    public Marker(GameObject object) {
        Objects.requireNonNull(object);
        this.object = object;
        lastAngle = object.getAngle();
        
        update();
    }


    @Override
    public void update() {

        if(object == null) {
            remove();
            return;
        }

        if(object.getMap().get() != getMap().get()) {
            if(object.getMap().isEmpty()) {
                setImage((Image)null);
                return;
            }
            object.getMap().get().add(this, 0, 0);
        }
        setLocation(object);

        if(object.getAngle() != lastAngle || object.getImage() != lastImage) {

            Image image;
            if(object.getImage() == null) image = null;
            else {
                double sin = Math.sin(Math.toRadians(object.getAngle())), cos = Math.cos(Math.toRadians(object.getAngle()));
                int w = (int)(Math.abs(cos * object.getImage().getWidth())) + (int)(Math.abs(sin * object.getImage().getHeight()));
                int h = (int)(Math.abs(sin * object.getImage().getWidth())) + (int)(Math.abs(cos * object.getImage().getHeight()));

                image = new Image(w + BORDER * 2, h + BORDER * 2);
                image.setColor(theme.main());
                image.drawRect(0, 0, w - 1 + BORDER * 2, h - 1 + BORDER * 2);
            }
            setImage(image);
            lastImage = object.getImage();
            lastAngle = object.getAngle();
        }
    }


    public void setActor(GameObject object) {
        this.object = object;
        update();
    }
    
    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
