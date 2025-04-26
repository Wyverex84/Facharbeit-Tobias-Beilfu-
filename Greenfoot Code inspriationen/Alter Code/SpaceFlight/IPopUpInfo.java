import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * IPopUpInfo exposes a method to pass drawn info of an object
 * to the information panel.
 * 
 * @author Michael Lastufka
 * @version Dec 2008
 */
public interface IPopUpInfo
{
    /**
     * Draws information for the popup info panel.
     * @param width Info panel width in pixels.
     * @param height Info panel height in pixels.
     * @return the image to display on the Info panel
     */
    public GreenfootImage getInfo(int width, int height);

    /**
     * Removes the PopUoInfo and PopUpImage for an object.
     */
    public void releaseInfo();

}
