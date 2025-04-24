package com.github.rccookie.greenfoot.ui;

import com.github.rccookie.util.ClassTag;
import com.github.rccookie.util.Console;
import com.github.rccookie.geometry.Vector;
import com.github.rccookie.greenfoot.core.Color;
import com.github.rccookie.greenfoot.core.GameObject;
import com.github.rccookie.greenfoot.core.Image;
import com.github.rccookie.greenfoot.core.Map;
import com.github.rccookie.greenfoot.ui.util.UIElement;

import java.util.HashMap;


public class Panel extends UIElement {

    static {
        ClassTag.tag(Panel.class, "ui");
    }

    private HashMap<GameObject, RelativeLocation> elements;
    private int x, y;
    private boolean coversWorld;

    public Panel(Map cover) {
        this(cover.getWidth(), cover.getHeight(), new Image(cover.getWidth(), cover.getHeight()));
        coversWorld = true;
    }

    public Panel(int x, int y) {
        this(x, y, Color.WHITE);
    }

    public Panel(int x, int y, Color color) {
        this(x, y, coloredImage(x, y, color));
        coversWorld = false;
    }

    public Panel(Map cover, Color color) {
        this(cover.getWidth(), cover.getHeight(), coloredImage(cover.getWidth(), cover.getHeight(), color));
        coversWorld = true;
    }

    public Panel(Map cover, Image image) {
        this(cover.getWidth(), cover.getHeight(), image);
        coversWorld = true;
    }

    public Panel(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        elements = new HashMap<GameObject, RelativeLocation>();
        setImage(image);
        coversWorld = false;
        addOnAdd(world -> {
            updateElements();
            if(coversWorld) setLocation(x / 2, y / 2);
        });
    }

    private static final Image coloredImage(int x, int y, Color color) {
        Image image = new Image(x, y);
        if(color != null) {
            image.setColor(color);
            image.fill();
        }
        return image;
    }


    public void add(GameObject element, double x, double y) {
        add(element, x, y, 0, 0);
    }

    public void add(GameObject element, double x, double y, double offX, double offY) {
        if(element == null) return;
        RelativeLocation loc = new RelativeLocation(x, y, offX, offY);
        elements.put(element, loc);
        if(getMap().isPresent()) {
            addElement(element, loc);
        }
    }

    private void addElement(GameObject element, RelativeLocation loc) {
        getMap().ifPresent(m -> m.add(
            element,
            getX() - this.x / 2 + this.x * loc.relative.x() + loc.offset.x(),
            getY() - this.y / 2 + this.y * loc.relative.y() + loc.offset.y()
        ));
    }

    public void move(GameObject element, double x, double y) {
        if(element != null && elements.keySet().contains(element)) {
            add(element, x, y);
        }
    }

    public void resize(int x, int y) {
        this.x = x;
        this.y = y;
        Image image = getImage();
        image.scale(x, y);
        setImage(image);
        updateElements();
    }

    private void updateElements() {
        for(GameObject element : elements.keySet()) {
            RelativeLocation loc = elements.get(element);
            if(element.getMap().isEmpty()) addElement(element, loc);
            else {
                element.setLocation(
                    getX() - (x / 2) + (int)(x * loc.relative.x() + loc.offset.x()),
                    getY() - (y / 2) + (int)(y * loc.relative.y() + loc.offset.y())
                );
            }
        }
    }

    @Override
    public void setLocation(Vector location) {
        if(location.x() == getX() && location.y() == getY()) return;
        coversWorld = false;
        super.setLocation(location);
        updateElements();
    }

    @Override
    public void setRotation(double angle) {
        if(angle == 0) return;
        Console.error("Cannot change angle of ui plane!");
    }



    /**
     * Removes this ui panel and all its subcomponents from its world.
     */
    @Override
    public boolean remove() {
        for(GameObject element : elements.keySet()) element.remove();
        return super.remove();
    }



    @Override
    public int getWidth() {
        return x;
    }

    @Override
    public int getHeight() {
        return y;
    }

    @Override
    protected void assignDefaultColorMappings() { }

    @Override
    protected void regenerateImages() {
        setImage(new Image(getWidth(), getHeight()));
    }
}
