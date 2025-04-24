package com.github.rccookie.greenfoot.ui;

import com.github.rccookie.util.ClassTag;
import com.github.rccookie.greenfoot.core.Color;
import com.github.rccookie.greenfoot.core.Image;
import com.github.rccookie.greenfoot.ui.util.UIElement;

public class Fade extends UIElement {

    static {
        ClassTag.tag(Fade.class, "ui");
    }

    public final Color color;
    public final long duration;
    public final boolean fadeIn;

    private long startTime, endTime;

    private Fade(Color color, double duration, boolean fadeIn) {
        this.color = color;
        this.duration = (long)(duration * 1000000000);
        this.fadeIn = fadeIn;

        addOnAdd(w -> {
            startTime = System.nanoTime();
            endTime = startTime + this.duration;
            imageChanged();
        });
    }


    /**
     * 
     * @param color The color at the beginning
     * @param duration In secondes
     * @return A fadein
     */
    public static final Fade fadeIn(Color color, double duration) {
        return new Fade(color, duration, true);
    }

    /**
     * 
     * @param color The color at the end
     * @param duration In secondes
     * @return A fadeout
     */
    public static final Fade fadeOut(Color color, double duration) {
        return new Fade(color, duration, false);
    }


    


    @Override
    public void update() {
        long time = System.nanoTime();
        if(time > endTime) {
            remove();
            return;
        }
        if(fadeIn) getImage().setTransparency((int)(255 * (1 - (time - startTime) / (double)duration)));
        else getImage().setTransparency((int)(255 * (time - startTime) / (double)duration));
    }

    @Override
    protected void assignDefaultColorMappings() {
        mapColor("fade-color", 1, false);
    }

    @Override
    protected void regenerateImages() {
        if(getMap().isEmpty()) setImage((Image)null);
        Image image = new Image(getMap().get().getWidth(), getMap().get().getHeight());
        image.setColor(color);
        image.fill();
        setImage(image);
    }
}
