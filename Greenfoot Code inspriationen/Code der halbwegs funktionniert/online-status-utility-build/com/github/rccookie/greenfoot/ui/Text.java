package com.github.rccookie.greenfoot.ui;

import java.util.Objects;

import com.github.rccookie.greenfoot.core.FontStyle;
import com.github.rccookie.greenfoot.core.Image;
import com.github.rccookie.util.Console;
import com.github.rccookie.greenfoot.core.Color;
import com.github.rccookie.greenfoot.ui.util.Design;
import com.github.rccookie.greenfoot.ui.util.Theme;
import com.github.rccookie.greenfoot.ui.util.UIElement;

/**
 * The text class is used to store and display some text and to control
 * text elements in other ui elements.
 * 
 * @author RcCookie
 * @version 2.0
 */
public class Text extends UIElement implements Cloneable {

    protected static final int DEFAULT_FONT_SIZE = 20;
    protected static final FontStyle DEFAULT_FONT = FontStyle.modern(DEFAULT_FONT_SIZE);
    protected static final String DEFAULT_CONTENT = "";

    private static final String TEXT = "text";


    /**
     * The text of the text.
     */
    private String title = DEFAULT_CONTENT;

    /**
     * The font of the text drawn.
     */
    private FontStyle font = DEFAULT_FONT;



    /**
     * Constructs an empty text.
     */
    public Text() { }

    /**
     * Constructs a new text with the given title.
     * 
     * @param title The text of the text
     */
    public Text(String title) {
        setTitle(title);
    }

    /**
     * Creates a new Text of the given one. The design instance will not be
     * cloned.
     * 
     * @param copy The text to create a copy of
     */
    public Text(Text copy) {
        this(copy.getTitle());
        setFont(copy.getFont());
        setDesign(copy.getDesign());
    }



    @Override
    public Text clone() {
        return new Text(this);
    }



    /**
     * Updates the image of the text according to the current settings.
     */
    @Override
    protected void regenerateImages() {
        if(title == null || title.length() == 0) {
            Console.debug("Text title is empty, using 1x1 cube");
            setImage(new Image(1, 1));
        }
        else {
            //Console.debug("Text title is '{}', rendering text image", title);
            setImage(Image.text(title, font.getSize(), elementColor(TEXT)));
            //setImage(Image.text(title, elementColor("text"), font));
            //Image image = new Image(Math.max(1, font.getWidth(title)), Math.max(1, font.getHeight(title)));
            ////image.fill(Color.LIGHT_GRAY); // For debugging purposes
            //image.drawString(title, 0, (int)(font.getSize() * 0.75), elementColor(TEXT), font);
            //setImage(image);
        }
    }



    /**
     * Sets the text of the text to the given string.
     * 
     * @param title The new text
     */
    public Text setTitle(String title) {
        if(Objects.equals(this.title, title)) return this;
        this.title = title;
        imageChanged();
        return this;
    }

    /**
     * Sets the font of this text.
     * 
     * @param font The new font
     * @return This text
     */
    public Text setFont(FontStyle font) {
        if(Objects.equals(this.font, font)) return this;
        this.font = font;
        imageChanged();
        return this;
    }

    /**
     * Sets the font size of the text (also of the already written stuff) to
     * the given one.
     * 
     * @param fontSize The new font size
     */
    public Text setSize(int fontSize) {
        return setFont(font.deriveFont(fontSize));
    }

    @Override
    public Text setDesign(Design design) {
        super.setDesign(design);
        return this;
    }

    /**
     * Sets the text theme to the given one.
     */
    public Text setTheme(Theme theme) {
        Objects.requireNonNull(theme, "The theme must not be null");
        return setDesign(new Design(getDesign().theme(), theme));
    }

    /**
     * Sets a new, modified version of the current design with the given
     * main text color.
     */
    public Text setColor(Color color) {
        return setTheme(getTheme().modified(0, color));
    }



    /**
     * Returns the title of the text.
     * 
     * @return The text of the text
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the font size of the text.
     * 
     * @return The font size of the text
     */
    public FontStyle getFont() {
        return font;
    }



    @Override
    protected void assignDefaultColorMappings() {
        mapColor(TEXT, 0, true);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Text)) return false;
        return Objects.equals(font, ((Text)obj).font) && Objects.equals(title, ((Text)obj).title);
    }
}
