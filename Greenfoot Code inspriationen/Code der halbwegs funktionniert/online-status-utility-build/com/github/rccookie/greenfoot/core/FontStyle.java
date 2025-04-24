package com.github.rccookie.greenfoot.core;

import java.util.Objects;

import greenfoot.Font;

/**
 * A subclass of {@link Font} that adds the ability to calculate
 * the size of text that is printed.
 * 
 * @author RcCookie
 * @version 0.1
 */
public abstract class FontStyle extends Font {

    static {
        Core.initialize();
    }

    private final double newLineDim;
    private final double onlineScale;

    private FontStyle(String name, boolean bold, boolean italic, int size, double newLineDim, double onlineScale) {
        super(name, bold, italic, size);
        this.newLineDim = newLineDim;
        this.onlineScale = Core.isOnline() ? onlineScale : 1;
    }

    /**
     * Returns the width of the specified string in pixels when written with
     * this font.
     * 
     * @param string The string to calculate the length for
     * @return The length of the string, in pixels
     */
    public int getWidth(String string) {
        int maxWidth = 0;
        double current = 0;
        for(int i=0; i<string.length(); i++) {
            char c = string.charAt(i);
            if(c == '\n') {
                if(current > maxWidth) maxWidth = (int)current;
                current = 0;
            }
            else current += getCharWidth(c) * getSize();
        }
        return (int)(Math.max(maxWidth, current) * onlineScale);
    }

    /**
     * Returns the width of the given char relative to the fint size.
     * 
     * @param c The char to get the width for
     * @return The char's witdh
     */
    protected abstract double getCharWidth(char c);

    /**
     * Returns the height of the specified string in pixels when written with
     * this font.
     * 
     * @param string The string to calculate the width for
     * @return The width of the string, in height
     */
    public int getHeight(String string) {
        int nLines = numberOfLines(string);
        return getSize() * nLines + (int)(getSize() * (nLines - 1) * newLineDim);
    }

    @Override
    public FontStyle deriveFont(float size) {
        return new FontStyle(getName(), isBold(), isItalic(), (int)size, newLineDim, onlineScale) {
            @Override
            protected double getCharWidth(char c) {
                return FontStyle.this.getCharWidth(c);
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof FontStyle)) return false;
        FontStyle f = (FontStyle)obj;
        return Objects.equals(getName(), f.getName())
            && isBold() == f.isBold()
            && isItalic() == f.isItalic()
            && newLineDim == f.newLineDim
            && onlineScale == f.onlineScale;
    }



    /**
     * Creates a new FontStyle from the given {@link Font}. The returned instance will
     * throw an exception if the width is requested. If the given font already is an
     * instance of FontStyle, it will be returned itself.
     * 
     * @param gFont The greenfoot.Font to convert to a FontStyle
     * @return A FontStyle representing the given font
     */
    public static final FontStyle of(Font gFont) {
        if(gFont instanceof FontStyle) return (FontStyle)gFont;
        return new GreenfootFontFontStyle(gFont);
    }

    private static final class GreenfootFontFontStyle extends FontStyle {

        private GreenfootFontFontStyle(Font gFont) {
            super(gFont.getName(), gFont.isBold(), gFont.isItalic(), gFont.getSize(), 1, 1);
        }

        @Override
        protected double getCharWidth(char c) {
            throw new UnsupportedOperationException("No character widths defined for font " + getName());
        }
    }



    /**
     * Returns a monostace styled font.
     * 
     * @param size The font size
     * @return The monospace font
     */
    public static final FontStyle monospace(int size) {
        return monospace(size, false, false);
    }

    /**
     * Returns a monostace styled font.
     * 
     * @param size The font size
     * @param bold Weather the font should be bold
     * @param italic Weather the font should be italic
     * @return The monospace font
     */
    public static final FontStyle monospace(int size, boolean bold, boolean italic) {
        return new FontStyle("Consolas", bold, italic, size, 0.2, 0.975) {
            @Override
            protected double getCharWidth(char c) {
                return 0.567;
            }
        };
    }



    /**
     * Returns a modern styled font.
     * 
     * @param size The font size
     * @return The monospace font
     */
    public static final FontStyle modern(int size) {
        return modern(size, false, false);
    }

    /**
     * Returns a modern styled font.
     * 
     * @param size The font size
     * @param bold Weather the font should be bold
     * @param italic Weather the font should be italic
     * @return The monospace font
     */
    public static final FontStyle modern(int size, boolean bold, boolean italic) {
        return new FontStyle("Segoe UI", bold, italic, size, 0.38, 1.025) {
            @Override
            protected double getCharWidth(char c) {
                // I know that switch exists but its ugly in Java 11
                if(bold) {
                    if(c == ' ') return 0.3; // is actually bigger
                    if(c == 'a') return 0.55;
                    if(c == 'b') return 0.6;
                    if(c == 'c') return 0.5;
                    if(c == 'd') return 0.6;
                    if(c == 'e') return 0.55;
                    if(c == 'f') return 0.4;
                    if(c == 'g') return 0.6;
                    if(c == 'h') return 0.6;
                    if(c == 'i') return 0.3;
                    if(c == 'j') return 0.3;
                    if(c == 'k') return 0.55;
                    if(c == 'l') return 0.3;
                    if(c == 'm') return 0.9;
                    if(c == 'n') return 0.6;
                    if(c == 'o') return 0.6;
                    if(c == 'p') return 0.6;
                    if(c == 'q') return 0.6;
                    if(c == 'r') return 0.4;
                    if(c == 's') return 0.45;
                    if(c == 't') return 0.4;
                    if(c == 'u') return 0.6;
                    if(c == 'v') return 0.55;
                    if(c == 'w') return 0.8;
                    if(c == 'x') return 0.55;
                    if(c == 'y') return 0.55;
                    if(c == 'z') return 0.5;
                    if(c == 'A') return 0.7;
                    if(c == 'B') return 0.65;
                    if(c == 'C') return 0.6;
                    if(c == 'D') return 0.75;
                    if(c == 'E') return 0.55;
                    if(c == 'F') return 0.5;
                    if(c == 'G') return 0.7;
                    if(c == 'H') return 0.75;
                    if(c == 'I') return 0.3;
                    if(c == 'J') return 0.45;
                    if(c == 'K') return 0.65;
                    if(c == 'L') return 0.5;
                    if(c == 'M') return 0.95;
                    if(c == 'N') return 0.8;
                    if(c == 'O') return 0.75;
                    if(c == 'P') return 0.6;
                    if(c == 'Q') return 0.75;
                    if(c == 'R') return 0.65;
                    if(c == 'S') return 0.55;
                    if(c == 'T') return 0.6;
                    if(c == 'U') return 0.7;
                    if(c == 'V') return 0.65;
                    if(c == 'W') return 1;
                    if(c == 'X') return 0.65;
                    if(c == 'Y') return 0.6;
                    if(c == 'Z') return 0.6;
                    if(c == '0') return 0.6;
                    if(c == '1') return 0.6;
                    if(c == '2') return 0.6;
                    if(c == '3') return 0.6;
                    if(c == '4') return 0.6;
                    if(c == '5') return 0.6;
                    if(c == '6') return 0.6;
                    if(c == '7') return 0.6;
                    if(c == '8') return 0.6;
                    if(c == '9') return 0.6;
                    if(c == '.') return 0.25;
                    if(c == ',') return 0.25;
                    if(c == '!') return 0.35;
                    if(c == '?') return 0.45;
                    if(c == ':') return 0.25;
                    if(c == ';') return 0.25;
                    if(c == '-') return 0.4;
                    if(c == '_') return 0.4;
                    if(c == '"') return 0.5;
                    if(c == '\'')return 0.3;
                    if(c == '%') return 0.85;
                    if(c == '&') return 0.85;
                    if(c == '/') return 0.45;
                    if(c == '\\')return 0.45;
                    if(c == '(') return 0.35;
                    if(c == ')') return 0.35;
                    if(c == '[') return 0.35;
                    if(c == ']') return 0.35;
                    if(c == '{') return 0.35;
                    if(c == '}') return 0.35;
                    if(c == '=') return 0.7;
                    if(c == '#') return 0.6;
                    if(c == '+') return 0.7;
                    if(c == '*') return 0.45;
                    if(c == '~') return 0.7;
                    if(c == '<') return 0.7;
                    if(c == '>') return 0.7;
                    if(c == '|') return 0.35;
                    if(c == '^') return 0.7;
                    if(c == '\u00df') return 0.65; // ß
                    if(c == '\u00e4') return 0.55; // ä
                    if(c == '\u00f6') return 0.6; // ö
                    if(c == '\u00fc') return 0.6; // ü
                    if(c == '\u00C4') return 0.7; // Ä
                    if(c == '\u00d6') return 0.75; // Ö
                    if(c == '\u00dc') return 0.7; // Ü
                    if(c == '\u00b0') return 0.4; // °
                    return 0.5;
                }

                if(c == ' ') return 0.25;
                if(c == 'a') return 0.5;
                if(c == 'b') return 0.6;
                if(c == 'c') return 0.45;
                if(c == 'd') return 0.58;
                if(c == 'e') return 0.5;
                if(c == 'f') return 0.3;
                if(c == 'g') return 0.6;
                if(c == 'h') return 0.55;
                if(c == 'i') return 0.25;
                if(c == 'j') return 0.25;
                if(c == 'k') return 0.5;
                if(c == 'l') return 0.25;
                if(c == 'm') return 0.85;
                if(c == 'n') return 0.55;
                if(c == 'o') return 0.6;
                if(c == 'p') return 0.6;
                if(c == 'q') return 0.6;
                if(c == 'r') return 0.35;
                if(c == 's') return 0.4;
                if(c == 't') return 0.35;
                if(c == 'u') return 0.55;
                if(c == 'v') return 0.5;
                if(c == 'w') return 0.7;
                if(c == 'x') return 0.45;
                if(c == 'y') return 0.5;
                if(c == 'z') return 0.45;
                if(c == 'A') return 0.65;
                if(c == 'B') return 0.55;
                if(c == 'C') return 0.6;
                if(c == 'D') return 0.7;
                if(c == 'E') return 0.5;
                if(c == 'F') return 0.5;
                if(c == 'G') return 0.7;
                if(c == 'H') return 0.7;
                if(c == 'I') return 0.25;
                if(c == 'J') return 0.35;
                if(c == 'K') return 0.6;
                if(c == 'L') return 0.45;
                if(c == 'M') return 0.9;
                if(c == 'N') return 0.75;
                if(c == 'O') return 0.75;
                if(c == 'P') return 0.55;
                if(c == 'Q') return 0.75;
                if(c == 'R') return 0.6;
                if(c == 'S') return 0.55;
                if(c == 'T') return 0.5;
                if(c == 'U') return 0.7;
                if(c == 'V') return 0.6;
                if(c == 'X') return 0.6;
                if(c == 'Y') return 0.55;
                if(c == 'Z') return 0.55;
                if(c == '0') return 0.55;
                if(c == '1') return 0.55;
                if(c == '2') return 0.55;
                if(c == '3') return 0.55;
                if(c == '4') return 0.55;
                if(c == '5') return 0.55;
                if(c == '6') return 0.55;
                if(c == '7') return 0.55;
                if(c == '8') return 0.55;
                if(c == '9') return 0.55;
                if(c == '.') return 0.2;
                if(c == ',') return 0.2;
                if(c == '!') return 0.3;
                if(c == '?') return 0.45;
                if(c == ':') return 0.2;
                if(c == ';') return 0.2;
                if(c == '-') return 0.4;
                if(c == '_') return 0.4;
                if(c == '"') return 0.4;
                if(c == '\'')return 0.25;
                if(c == '%') return 0.8;
                if(c == '&') return 0.8;
                if(c == '/') return 0.4;
                if(c == '\\')return 0.4;
                if(c == '(') return 0.3;
                if(c == ')') return 0.3;
                if(c == '[') return 0.3;
                if(c == ']') return 0.3;
                if(c == '{') return 0.3;
                if(c == '}') return 0.3;
                if(c == '=') return 0.7;
                if(c == '#') return 0.6;
                if(c == '+') return 0.7;
                if(c == '*') return 0.4;
                if(c == '~') return 0.7;
                if(c == '<') return 0.7;
                if(c == '>') return 0.7;
                if(c == '|') return 0.25;
                if(c == '^') return 0.7;
                if(c == '\u00df') return 0.55; // ß
                if(c == '\u00e4') return 0.5; // ä
                if(c == '\u00f6') return 0.6; // ö
                if(c == '\u00fc') return 0.55; // ü
                if(c == '\u00C4') return 0.65; // Ä
                if(c == '\u00d6') return 0.75; // Ö
                if(c == '\u00dc') return 0.7; // Ü
                if(c == '\u00b0') return 0.4; // °
                return 0.5;
            }
        };
    }



    private static final int numberOfLines(String string) {
        int count = 1;
        while(string.contains("\n")) {
            string = string.replaceFirst("\n", "");
            count++;
        }
        return count;
    }
}
