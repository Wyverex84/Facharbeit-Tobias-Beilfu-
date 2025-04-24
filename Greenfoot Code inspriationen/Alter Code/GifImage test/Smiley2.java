import greenfoot.*;

public class Smiley2 extends Actor
{
    private GifImage gi;
    
    public Smiley2()
    {
        gi = new GifImage("images/smileytop.gif");
        setImage(gi.getCurrentImage());
    }

    public void act()
    {
        setImage(gi.getCurrentImage());
    }
}