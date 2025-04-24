package packages.input;
import greenfoot.Greenfoot;

public class KeyListener{

    public final String key;
    private boolean down, pressed, released;

    public KeyListener(String key){
        this.key = key;
        update();
    }

    public void update(){
        boolean temp = down;
        down = Greenfoot.isKeyDown(key);
        if(temp!=down){
            pressed = down;
            released = !down;
            if(down) onPress();
            else onRelease();
        }
        else pressed = false;
    }

    public boolean down(){
        return down;
    }
    public boolean pressed(){
        return pressed;
    }
    public boolean released(){
        return released;
    }

    public void onPress(){}
    public void onRelease(){}
}