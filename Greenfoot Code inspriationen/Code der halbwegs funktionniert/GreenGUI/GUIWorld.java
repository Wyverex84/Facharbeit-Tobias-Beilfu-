import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 *
 * @author Ed Parrish
 * @version 1 on 11/25/2017
 */
public class GUIWorld extends World
{
    /**
     * Constructor for objects of class MyWorld.
     *
     */
    public GUIWorld()
    {
        super(600, 400, 1);
        prepare();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Label label = new Label();
        addObject(label, 115, 50);
        Label label2 = new Label("Label 24", 24);
        addObject(label2, 115, 100);
        Label label3 = new Label("Multiline\nLabel 36", 36,
                Color.YELLOW, Color.BLUE);
        addObject(label3, 115, 200);
        Font font = new Font(true, true, 24);
        Label label4 = new Label("Testing font 24", font);
        addObject(label4, 115, 300);
        GreenfootImage img = new GreenfootImage("person.png");
        Label label5 = new Label(img);
        addObject(label5, 115, 350);
        Button button = new Button("Click me");
        addObject(button, 350, 50);
        Button button2 = new Button("Button rounded 24", 24,
            Color.BLACK, Button.Style.ROUNDED);
        addObject(button2, 350, 100);
        Button button3 = new Button("Button\ncircle 32", 32,
            Color.BLACK, Button.Style.CIRCLE);
        addObject(button3, 350, 200);
        Button button4 = new Button("Button with font 24", font, Color.BLACK);
        addObject(button4, 350, 300);
        ButtonChecker buttonchecker = new ButtonChecker();
        addObject(buttonchecker, 500, 50);
    }
}
