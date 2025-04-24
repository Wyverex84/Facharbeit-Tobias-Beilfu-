import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import rccookie.box2d.Body;
import rccookie.box2d.GBox2DWorld;
import rccookie.ui.basic.Button;
import rccookie.ui.basic.Slider;
import rccookie.ui.basic.Text;
import rccookie.ui.basic.UIPanel;


public class MyWorld extends GBox2DWorld {

    private Slider speed, size, density, bounce;

    public MyWorld() {
        super(600, 400, 20, true, true);
        addFps();
        Greenfoot.start();

        add(new ControlPanel(), 1, 0.5, -75, 0);

        GreenfootImage image = new GreenfootImage(2 * (int)UNIT, 2 * (int)UNIT);
        image.setColor(new Color(220, 220, 220));
        image.fillRect(0, 0, (int)UNIT - 1, (int)UNIT - 1);
        image.fillRect((int)UNIT, (int)UNIT, (int)UNIT - 1, (int)UNIT - 1);
        setBackground(image);
    }

    @Override
    public void update() {
        try{
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(mouse.getActor() != null) return;
            if(mouse.getButton() == 1)
                addCircle(mouse.getX(), mouse.getY());
            else if(mouse.getButton() == 2)
                addBox(mouse.getX(), mouse.getY());
            else if(mouse.getButton() == 3)
                addStaticBox(mouse.getX(), mouse.getY());
        }
        catch(Exception e) {}
        time().setTimeScale(speed.getValue());
    }



    private void addCircle(int x, int y) {
        addObject(new Circle((float)size.getValue(), (float)density.getValue(), (float)bounce.getValue()), x, y);
    }
    private void addBox(int x, int y) {
        addObject(new Box(2 * UNIT, 2 * UNIT), x, y);
    }
    private void addStaticBox(int x, int y) {
        addObject(new StaticBox(2 * UNIT, 2 * UNIT), x, y);
    }
    private void clear() {
        removeObjects(getObjects(Body.class));
    }






    class ControlPanel extends UIPanel {

        public ControlPanel() {
            super(140, MyWorld.this.getHeight() - 10, new Color(60, 60, 60, 200));

            add(size = new Slider(0.1, 5), 0.5, 0.3);
            addAddedAction(w -> size.setValue(1));
            add(new Text("Size"), 0.5, 0.33);
            
            add(density = new Slider(0.1, 5), 0.5, 0.4);
            addAddedAction(w -> density.setValue(1));
            add(new Text("Density"), 0.5, 0.43);
            
            add(bounce = new Slider(0, 1.1), 0.5, 0.5);
            addAddedAction(w -> bounce.setValue(0.5));
            add(new Text("Bounce"), 0.5, 0.53);
            
            add(speed = new Slider(0, 10), 0.5, 0.85);
            addAddedAction(w -> speed.setValue(1));
            add(new Text("Simulation speed"), 0.5, 0.88);

            add(new Button("Clear").addClickAction(m -> clear()), 0.5, 0.95);
        }
    }
}
