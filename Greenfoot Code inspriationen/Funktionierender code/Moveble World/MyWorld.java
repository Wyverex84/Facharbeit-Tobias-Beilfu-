import greenfoot.*;
import packages.twoD.Container;
import packages.physics.AdvancedActor;
import packages.ui.UIWorld;
public class MyWorld extends UIWorld {
    Container container;
    AdvancedActor actor;
    public MyWorld() {
        super(600, 400, 1);
        
        
        container = new Container(200, 200, 1);
        addObject(container, 300, 200);

        GreenfootImage background = new GreenfootImage(200, 200);
        background.setColor(new Color(255, 0, 0, 100));
        background.fill();
        container.setBackground(background);

        actor = new AdvancedActor() {};
        GreenfootImage image = new GreenfootImage(20, 20);
        image.setColor(Color.BLUE);
        image.fill();
        actor.setImage(image);
        container.addObject(actor, 100, 100);

        addFps();

        Greenfoot.start();
    }

    @Override
    public void act() {
        int speed = Greenfoot.isKeyDown("shift") ? 150 : 60;

        if(Greenfoot.isKeyDown("up")) actor.fixedMove(speed);
        if(Greenfoot.isKeyDown("down")) actor.fixedMove(-speed);
        if(Greenfoot.isKeyDown("left")) actor.fixedTurn(-90);
        if(Greenfoot.isKeyDown("right")) actor.fixedTurn(90);

        
        if(Greenfoot.isKeyDown("w")) container.fixedMove(speed);
        if(Greenfoot.isKeyDown("s")) container.fixedMove(-speed);
        if(Greenfoot.isKeyDown("a")) container.fixedTurn(-90);
        if(Greenfoot.isKeyDown("d")) container.fixedTurn(90);
    }
}