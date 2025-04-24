import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Label here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Label  extends Actor
{
    public Label(int width, int height){
        GreenfootImage image = new GreenfootImage(width, height);
        image.drawString("This is a slider to allow the user to select a number", 10, 20);
        image.drawString("Use the buttons below to test each of the methods on the slider.", 10, 40);
        image.drawString("The user can select a value between zero and the maximum value (inclusive).", 10, 55);
        image.drawString("The selected value can be displayed next to slider as a number or a percentage.", 10, 70);
        image.drawString("You can set the slider to mark sections. Major sections have major (tall) markers between", 10, 85);
        image.drawString("them and at both ends of the scale. Minor sections have minor (small) markers between them", 10, 100);
        image.drawString("and are repeated for every major section. Experiment with the methods below to understand", 10, 115);
        image.drawString("section markers better.", 10, 130);
        image.drawString("To use a slider in your own project, create an Actor class called 'Slider', download the", 10, 150);
        image.drawString("source code for this demo, and replace all the code in your Slider class with the entire", 10, 165);
        image.drawString("contents of the Slider class from this demo", 10, 180);
        setImage(image);
    }
}
