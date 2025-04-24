import greenfoot.Color;
import greenfoot.Greenfoot;
import rccookie.ui.basic.Text;
import rccookie.ui.basic.UIWorld;

public class CodeExample extends UIWorld {
    public CodeExample() {
        super(800, 400, 1);

        setBackground("images/code.png");
        add(new Text("Full game code", 30, Color.RED), 0.5, 0.1);
        Greenfoot.stop();
    }
}
