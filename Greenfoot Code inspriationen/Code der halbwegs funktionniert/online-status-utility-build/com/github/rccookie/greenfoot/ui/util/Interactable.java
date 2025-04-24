package com.github.rccookie.greenfoot.ui.util;

import java.util.Set;

import com.github.rccookie.greenfoot.core.Color;
import com.github.rccookie.greenfoot.core.Image;
import com.github.rccookie.util.ClassTag;
import com.github.rccookie.util.Console;

/**
 * A class that can be focused and clicked on using keyboard navigation. This can be toggled on or off.
 */
public abstract class Interactable extends UIElement {
    
    static {
        ClassTag.tag(Interactable.class, "ui");
    }

    /**
     * The color filter if the object is clicked on.
     */
    protected static final Color CLICK_COLOR_CORRECTION = Color.BLACK.setAlpha(60);

    /**
     * The color filter if the object is clicked on.
     */
    protected static final Color DISABLED_COLOR_CORRECTION = Color.WHITE.setAlpha(50);

    /**
     * The factor to scale the image by if the object is clicked on.
     */
    protected static final double CLICK_SCALE = 0.98;

    /**
     * The color of the outline if the object is hovered on.
     */
    protected static final Color HOVER_OUTLINE_COLOR = new Color(128, 128, 128);

    /**
     * The standart image of the of the object. This is always asigned properly.
     */
    private Image image;

    /**
     * The objects image when hovered over it with the mouse. May be {@code null}
     * if not yet requested.
     */
    private Image hoverImage;

    /**
     * The objects image when it is being clicked. May be {@code null}
     * if not yet requested.
     */
    private Image clickImage;

    /**
     * The objects image when it is not enabled. May be {@code null}
     * if not yet requested.
     */
    private Image disabledImage;

    /**
     * Flag indicating weather this element is currently enabled for interactions.
     */
    private boolean enabled = true;

    /**
     * Returns the navigatables that should be able to be focused by keyboard commands,
     * including this object itself. By default this returns all navigatables in the world.
     * 
     * @return The navigatables that should be able to be focused by keyboard commands
     */
    public Set<Interactable> getFocusable() {
        return getMap().get().findAll(Interactable.class);
    }


    @Override
    public void click() {
        if(isEnabled()) super.click();
    }

    @Override
    protected void onPress() {
        if(isEnabled()) super.onPress();
    }

    @Override
    protected void onRelease() {
        if(isEnabled()) super.onRelease();
    }


    /**
     * Sets weather this element should be enabled for interaction or not.
     * 
     * @param flag Weather the element should be enabled
     */
    public void setEnabled(boolean flag) {
        enabled = flag;
    }

    /**
     * Returns whether this interactable is currently enabled.
     * 
     * @return {@code true} if this interactable is currently enabled
     */
    public boolean isEnabled() {
        return enabled;
    }





    /**
     * Creates the images for all press states and sets the appropriate one.
     */
    @Override
    protected void regenerateImages() {
        image = createMainImage();

        // Indicates they need to be rendered if requested
        hoverImage = clickImage = disabledImage = null;
        updateImageSelection();
    }

    /**
     * Creates and returns the main image for this object.
     * 
     * @return The main image for this object
     */
    protected abstract Image createMainImage();

    /**
     * Edits the given image to fit the hover state.
     * 
     * @param base The base image to edit (no need to copy), will not be null
     */
    protected void createHoverImage(Image base) {
        base.setColor(HOVER_OUTLINE_COLOR);
        base.drawRect(0, 0, base.getWidth() - 1, base.getHeight() - 1);
        base.drawRect(1, 1, base.getWidth() - 3, base.getHeight() - 3);
    }

    /**
     * Edits the given image to fit the click state.
     * 
     * @param base The base image to edit (no need to copy), will not be null
     */
    protected void createClickImage(Image base) {
        base.setColor(CLICK_COLOR_CORRECTION);
        base.scale((int)(base.getWidth() * CLICK_SCALE), (int)(base.getHeight() * CLICK_SCALE));
        base.fill();
    }

    /**
     * Edits the given image to fit the disabled state.
     * 
     * @param base The base image to edit (must not be copied), will not be null
     */
    protected void createDisabledImage(Image base) {
        base.setColor(CLICK_COLOR_CORRECTION);
        base.scale((int)(base.getWidth() * CLICK_SCALE), (int)(base.getHeight() * CLICK_SCALE));
        base.fill();
    }

    /**
     * Sets the image of the button to the currently matching animation state.
     */
    protected void updateImageSelection() {
        if(!isEnabled()) super.setImage(getDisabledImage());
        else if(hovered()) {
            if(pressed()) super.setImage(getClickImage());
            else super.setImage(getHoverImage());
        }
        else super.setImage(image);
    }

    private Image getHoverImage() {
        if(hoverImage == null && image != null) {
            createHoverImage(hoverImage = image.clone());
            Console.debug("Rendering hover image");
        }
        return hoverImage;
    }

    private Image getClickImage() {
        if(clickImage == null && image != null) {
            createClickImage(clickImage = image.clone());
            Console.debug("Rendering click image");
        }
        return clickImage;
    }

    private Image getDisabledImage() {
        if(disabledImage == null && image != null) {
            createDisabledImage(disabledImage = image.clone());
            Console.debug("Rendering hover image");
        }
        return disabledImage;
    }


    @Override
    protected void physicsUpdate() {
        super.physicsUpdate();
        updateImageSelection();
    }
}
