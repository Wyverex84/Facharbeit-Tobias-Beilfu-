package rccookie.greenfoot.ui.basic;

import greenfoot.GreenfootImage;
import rccookie.greenfoot.ui.util.Interactable;

/**
 * A button that shows an icon rather than a text.
 */
public class IconButton extends Interactable {

    private static final long serialVersionUID = -7601612940355287878L;

    private GreenfootImage icon;

    public IconButton(GreenfootImage icon) {
        this.icon = icon;
    }

    @Override
    protected GreenfootImage createMainImage() {
        return icon;
    }

    public GreenfootImage getIcon() {
        return icon;
    }

    public void setIcon(GreenfootImage icon) {
        this.icon = icon;
        imageChanged();
    }

    @Override
    protected void assignDefaultColorMappings() { }
}
