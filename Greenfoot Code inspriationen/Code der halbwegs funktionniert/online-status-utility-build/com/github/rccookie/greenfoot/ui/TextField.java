package com.github.rccookie.greenfoot.ui;

import com.github.rccookie.greenfoot.core.Color;
import com.github.rccookie.greenfoot.core.Core;

public class TextField extends TextButton {

    public static final int BORDER = 1;
    public static final int GAP_BEFORE_TEXT = 2;
    public static final int Y_TIGHTENING = 1;
    public static final Color OUTLINE_COLOR = Color.DARK_GRAY;
    public static final Color DEF_BACKGROUND_COLOR = Color.GRAY;


    public TextField(Text defaultText) {
        this(defaultText, 100);
    }
    public TextField(Text defaultText, int width) {
        this(defaultText, width, defaultText.getImage().getHeight() + 2 * BORDER - 2 * Y_TIGHTENING);
    }
    public TextField(Text defaultText, int x, int y) {
        super(defaultText);
        setMinWidth(x).setMaxWidth(x);
        setMinHeight(y).setMaxHeight(y);
        setDrawOutline(true);
        setupAction(defaultText.getTitle() + ":");
    }

    protected void setupAction(String question) {
        addOnClick(() -> getText().setTitle(Core.ask(question)));
    }



    /*@Override
    protected Image createMainImage() {
        // Create correct sized image
        Image image = new Image(get, minY);

        // Fill background around text with text background color or default color, if null
        if(getText().getBackgroundColor() == null) {
            image.setColor(DEF_BACKGROUND_COLOR);
            image.fillRect(BORDER, BORDER, minX - 2 * BORDER, minY - 2 * BORDER);
        }
        else {
            image.setColor(getText().getBackgroundColor());
            if(minX > getText().getImage().getWidth() + 2 * BORDER) {
                int delta = minX - getText().getImage().getWidth() - BORDER - GAP_BEFORE_TEXT;
                image.fillRect(BORDER, BORDER, GAP_BEFORE_TEXT, getText().getImage().getHeight());
                image.fillRect(image.getWidth() - delta, BORDER, delta, image.getHeight() - 2 * BORDER);
            }
            if(minY > getText().getImage().getHeight() + 2 * BORDER){
                int deltaY = minY - 2 * BORDER - getText().getImage().getHeight();
                image.fillRect(BORDER, BORDER, getText().getImage().getWidth() - 2 * BORDER, deltaY / 2);
                image.fillRect(BORDER, image.getHeight() - deltaY / 2 - BORDER, getText().getImage().getWidth() - 2 * BORDER, deltaY / 2);
            }
        }

        // Draw text onto background
        image.drawImage(getText().getImage(), BORDER + GAP_BEFORE_TEXT, (minY - getText().getImage().getHeight()) / 2);

        // Draw outline
        image.setColor(OUTLINE_COLOR);
        for(int i=0; i<BORDER; i++) image.drawRect(i, i, minX - 1 - 2 * i, minY - 1 - 2 * i);

        return image;
    }*/
}
