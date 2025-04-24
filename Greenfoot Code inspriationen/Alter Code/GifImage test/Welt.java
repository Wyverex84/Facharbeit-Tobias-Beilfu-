import greenfoot.*;

public class Welt extends World
{
    public Welt()
    {
        super(400, 240, 1);
        String text = "Animation object";
        GreenfootImage txt = new GreenfootImage(text, 20, null, null);
        getBackground().drawImage(txt, getWidth()/4-txt.getWidth()/2, 5);
        text = "GifImage object";
        txt = new GreenfootImage(text, 20, null, null);
        getBackground().drawImage(txt, getWidth()*3/4-txt.getWidth()/2, 5);
        addObject(new Smiley(), getWidth()/4, getHeight()/2-20);
        addObject(new Smiley2(), getWidth()*3/4, getHeight()/2-20);
        text = "Pausing and starting or changing speeds\n will demonstrate some behavioral differences.";
        txt = new GreenfootImage(text, 20, null, null);
        getBackground().drawImage(txt, (getWidth()-txt.getWidth())/2, getHeight()-5-txt.getHeight());
    }
}