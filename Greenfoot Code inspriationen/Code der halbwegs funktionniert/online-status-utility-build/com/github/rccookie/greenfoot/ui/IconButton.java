package com.github.rccookie.greenfoot.ui;

import com.github.rccookie.greenfoot.core.Image;
import com.github.rccookie.greenfoot.ui.util.Interactable;

/**
 * A button that shows an icon rather than a text.
 */
public class IconButton extends Interactable {

    private Image icon;

    public IconButton(Image icon) {
        this.icon = icon;
    }

    @Override
    protected Image createMainImage() {
        return icon;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
        imageChanged();
    }

    @Override
    protected void assignDefaultColorMappings() { }
}
