import com.github.rccookie.greenfoot.core.CoreWorld;
import com.github.rccookie.greenfoot.core.FontStyle;
import com.github.rccookie.greenfoot.core.Image;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;

/**
 * A world containing an explained demo for how wo use the Image class that
 * allowes for different fonts.
 * 
 * <p>DemoWorld is extending CoreWorld, which is a more advanced world but shares
 * all of its features. If is included in the package.
 */
public class DemoWorld extends CoreWorld {

    /**
     * Constructor for DemoWorld.
     */
    public DemoWorld() {
        // Creates a 600x400 pixels world with cell size of 1. This is possible without the cellsize because of CoreWorld.
        super(600, 400);


        // The string that is shown as title
        String titleString = "Demo - look into the code of DemoWorld";
        // The color of the title. A normal greenfoot color
        Color color = Color.RED;

        // Boolean values indicating that the title text should be bold but nut italic
        boolean bold = true, italic = false;
        // Creates a FontStyle, a subclass of greenfoot.Font. The font is monostyle, the alternative would be 'modern'.
        // The font size is 25, and bold and italic from above specify the exact font style
        FontStyle fontStyle = FontStyle.monospace(25, bold, italic);

        // Creates the actual image with the text on it using the variables that were defined above. The image will be
        // exactly as big as the text and have a transparent background.
        // 'Image' is a subclass of GreenfootImage. That means that you can threat it as a normal GreenfootImage, but it
        // also has soem more methods.
        Image title = Image.text(titleString, color, fontStyle);

        // To display the image in the world, we need to either draw it into the background image or use an actor that
        // has this image as its image. That's what we are doing below. The Image class has a handy method 'asActor()'
        // that does exactly that.
        Actor titleDisplayActor = title.asActor();
        // Now simply add the actor into the world.
        addObject(titleDisplayActor, 300, 50);


        // Above we saved everything into variables, but it is much shorter to just do it all in one line. That's what
        // I've been doing below.


        // Some normal text with the font 'modern' in dark gray and the size 15
        addObject(Image.text("Normal text", Color.DARK_GRAY, FontStyle.modern(15)).asActor(), 300, 120);
        // Normal text that is bold, by setting the first boolean to 'true'
        addObject(Image.text("Bold text", Color.DARK_GRAY, FontStyle.modern(15, true, false)).asActor(), 300, 140);
        // Normal text that is italic, by setting the second boolean to 'true'
        addObject(Image.text("Italic text", Color.DARK_GRAY, FontStyle.modern(15, false, true)).asActor(), 300, 160);
        // Normal text that is both bold and italic, by setting the both boolean values to 'true'
        addObject(Image.text("You can also do both", Color.DARK_GRAY, FontStyle.modern(15, true, true)).asActor(), 300, 180);

        // Like anywhere else in Java newlines are creates using the newline character as shown after 'newlines'.
        // If you actually want to print '\n' you can escape the \ of \n using another \ so its \\n
        addObject(Image.text("You can introduce newlines\nby adding the '\\n' newline character.", Color.DARK_GRAY, FontStyle.modern(15)).asActor(), 300, 220);


        // Here are some examples in different colors and aith the monospace font
        addObject(Image.text("Of course", Color.RED, FontStyle.monospace(15)).asActor(), 300, 270);
        addObject(Image.text("all of this", Color.ORANGE, FontStyle.monospace(15, true, false)).asActor(), 300, 290);
        addObject(Image.text("also works with", Color.GREEN, FontStyle.monospace(15, false, true)).asActor(), 300, 310);
        addObject(Image.text("the monospace\nfont", Color.BLUE, FontStyle.monospace(15, true, true)).asActor(), 300, 340);



        // Now an example showing that you can use Image just like GreenfootImage, but better ;-)

        // Create an Image with text
        Image image = Image.text("Hello World!", Color.MAGENTA, FontStyle.monospace(15));
        // Drawing a rectangle at the bottom of the image.
        // You may notice that I don't have to set the color before but can do that within the method call. That's also
        // part of the Image class
        image.fillRect(0, image.getHeight() - 2, 100, 2, Color.CYAN);

        // Now we create a normal 100x100 pixels GreenfootImage
        // (You can also create an Image the same way)
        GreenfootImage gImage = new GreenfootImage(100, 100);
        //Filling the image with light gray color
        gImage.setColor(Color.LIGHT_GRAY);
        gImage.fill();
        // Drawing the image with the text like a normal image onto the GreenfootImage
        gImage.drawImage(image, 0, 0);

        // This is basically what 'asActor()' from Image does. However this is just a GreenfootImage
        // so we have to do it ourself
        Actor gImageActor = new Actor() { };
        gImageActor.setImage(gImage);
        // Add the actor with the image into the world
        addObject(gImageActor, 500, 300);
    }
}
