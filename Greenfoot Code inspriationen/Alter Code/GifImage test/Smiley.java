import greenfoot.*;

public class Smiley extends Actor
{
    private Animation animation;
    
    public Smiley()
    {
        java.util.List<GreenfootImage> imgs = new GifImage("images/smileytop.gif").getImages();
        GreenfootImage[] images = new GreenfootImage[imgs.size()];
        for (int i=0; i<imgs.size(); i++) images[i] = (GreenfootImage)imgs.get(i);
        animation = new Animation(this, images);
        animation.setCycleActs(148);
        animation.run();
        animation.setActiveState(true);
    }

    public void act()
    {
        animation.run();
    }
}