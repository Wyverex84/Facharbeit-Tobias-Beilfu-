import greenfoot.Greenfoot;
import rccookie.game.GameWorld;
import rccookie.ui.basic.Text;
public class MyWorld extends GameWorld {
    public MyWorld(){
        super(600, 400, 1);
        setGameDuration(5);
        addGameStartAction(time -> add(new Text("Click space as often as you can", TEXT_COLOR_1), 0.5, 0.5) );
        addPausedAction(time -> System.out.println("Paused at " + time));
    }

    @Override
    public void gameUpdate() {
        if("space".equals(getKey())) addScore(.5);
    }

    @Override
    public void restart() { Greenfoot.setWorld(new MyWorld()); }

    @Override
    public void quit() { Greenfoot.setWorld(new CodeExample()); }
}
