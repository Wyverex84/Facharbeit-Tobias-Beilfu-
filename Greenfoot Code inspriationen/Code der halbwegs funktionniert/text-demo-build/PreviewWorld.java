import com.github.rccookie.greenfoot.core.CoreWorld;
import com.github.rccookie.greenfoot.core.FontStyle;
import com.github.rccookie.greenfoot.core.Image;
import com.github.rccookie.greenfoot.ui.basic.TextButton;

import greenfoot.Color;
import greenfoot.Greenfoot;

public class PreviewWorld extends CoreWorld {

    String p1 = "This scenario adds options to use different fonts than the default one\nused by Greenfoot. While the default font may look decent offline,\nit's a different one online that looks aweful. You have always been\nable to use different fonts, however there is one major downside\nto using other fonts: When using the default font you can create an\nimage with some text on it and it will automatically have the fitting\nsize. However to use a custom font you have to print on existing\nimage which requires you to know the dimensions that that text will\nhave. To work around that problem I have started to - its great fun -\nmeasure each character from a font and create some classes that then \nuse that measured size. So far I have got this font and a monospace\nfont which was simple.";
    String p2 = "This text uses the monospace font, 'Consolas', a coding\nfont used by Visual Studio Code.\n\n>> System.out.println(\'Hello World!\");";

    public PreviewWorld() {
        super(600, 400);
        Greenfoot.start();

        addRelative(Image.text(p1, Color.DARK_GRAY, FontStyle.modern(15)).asActor(), 0.5, 0.35);
        addRelative(Image.text(p2, Color.BLUE, FontStyle.monospace(15)).asActor(), 0.5, 0.8);

        if(!IS_ONLINE) addRelative(new TextButton("Show demo").addClickAction(() -> {
            Greenfoot.setWorld(new DemoWorld());
            Greenfoot.stop();
        }), 0.5, 0.95);
    }
}
