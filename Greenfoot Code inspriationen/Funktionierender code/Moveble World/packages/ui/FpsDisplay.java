package packages.ui;

import packages.tools.Time;
/**
 * The fps display shows the current fps in one of two modes: either
 * in realtime (based on only the last frame) or the average (of the
 * last second) (as calculated in packages.tools.Time). When clicked
 * on it it switches modes.
 * 
 * @see packages.tools.Time
 * @author RcCookie
 * @version 1.0
 */
public class FpsDisplay extends packages.ui.Button{
    /**
     * The time object that actually calculats the fps.
     */
    protected Time time;

    /**
     * Weather the output is the average(=true) or realtime (=false).
     */
    boolean stableMode;


    /**
     * Constructs a new fps display in stable mode.
     */
    public FpsDisplay(){
        super(new Text("FPS: --", null), 70, 20);
        time = new Time();
        stableMode = true;
    }

    /**
     * Constructs a new fps display in the given mode.
     * 
     * @param stableMode Weather the display shows stable fps by default
     */
    public FpsDisplay(boolean stableMode){
        super(new Text("FPS: --", null), 70, 20);
        time = new Time();
        this.stableMode = stableMode;
    }


    /**
     * Runs the time object and updates the text output.
     */
    @Override 
    public void run(){
        time.act();
        getText().setContent("FPS: " + currentModeFps());
    }
    

    /**
     * Returns the current realtime fps.
     * 
     * @return The current realtime fps
     */
    public int fps(){
        return time.fps();
    }

    /**
     * Returns the current average fps.
     * 
     * @return The current average fps
     */
    public int stableFps(){
        return time.stableFps();
    }

    /**
     * Returns the fps in the current mode.
     * @return The current fps in the current mode
     */
    public int currentModeFps(){
        if(stableMode) return stableFps();
        else return fps();
    }


    /**
     * Returns the duration of the last frame as a fraction of a second.
     * 
     * @return The duration of the last frame as a fraction of a second
     * @see packages.tools.Time.deltaTime()
     */
    public double deltaTime(){
        return time.deltaTime();
    }
    

    /**
     * Switches modes. Called by the button class whenever the button was clicked.
     */
    @Override
    public void onClick(){
        stableMode = !stableMode;
    }
}