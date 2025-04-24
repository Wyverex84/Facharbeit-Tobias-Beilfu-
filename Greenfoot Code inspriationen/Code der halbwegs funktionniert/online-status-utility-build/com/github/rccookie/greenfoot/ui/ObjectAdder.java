package com.github.rccookie.greenfoot.ui;

import com.github.rccookie.util.ClassTag;
import com.github.rccookie.greenfoot.core.GameObject;
import com.github.rccookie.greenfoot.core.MouseState;

/**
 * A simple class that takes in an object to add to the world using the mouse.
 */
public class ObjectAdder extends GameObject {

    static {
        ClassTag.tag(ObjectAdder.class, "ui");
    }
    

    private GameObject object;

    public ObjectAdder(GameObject objectToAdd) {
        object = objectToAdd;
        setImage(object.getImage());
        addOnAdd(() -> setLocation(MouseState.get().location));
    }



    @Override
    public void update() {
        MouseState mouse = MouseState.get();
        setLocation(mouse.location);
        if(mouse.button == 1) {
            getMap().ifPresent(m -> m.add(object, getLocation()));
            remove();
            object = null;
        }
        else if(mouse.button == 3) {
            remove();
            object = null;
        }
    }
}
