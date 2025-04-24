import greenfoot.*;

public class Speed extends World
{
    public Speed()
    {
        super(300, 100, 1);
    /** vvvvvvvvvvvvv    Just include the Fps class and add the following line   vvvvvvvvvvv */
        addObject(new Fps(), 150, 50); // whatever location
    /** ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */
    }
}