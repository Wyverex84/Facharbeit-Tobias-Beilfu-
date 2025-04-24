import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SliderWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SliderWorld  extends World
{
    private final int rows = 6, rowHeight = getHeight()/(rows*2+2);
    private final int cols = 3, colWidth = getWidth()/cols;
    private Slider s = new Slider();

    /**
     * Constructor for objects of class SliderWorld.
     * 
     */
    public SliderWorld()
    {    
        // Create a new world with 20x20 cells with a cell size of 10x10 pixels.
        super(600, 400, 1);
        addObject(new Label(getWidth(), getHeight()/2), getWidth()/2, getHeight()/4);
        //final Slider s = new Slider();
        s.showValue(true);
        //s.setValue(50);
        addObject(s, getWidth()/2, (getHeight()+rowHeight)/2);
        addButton("setValue(0)", 1, 1);
        addButton("setValue(1)", 1, 2);
        addButton("setValue(100)", 1, 3);
        addButton("setMaximumValue(1)", 1, 4);
        addButton("setMaximumValue(100)", 1, 5);
        addButton("showValue(true)", 2, 1);
        addButton("showValue(false)", 2, 2);
        addButton("showPercentage(true)", 2, 3);
        addButton("showPercentage(false)", 2, 4);
        addButton("setMajorSections(0)", 3, 1);
        addButton("setMajorSections(1)", 3, 2);
        addButton("setMajorSections(2)", 3, 3);
        addButton("setMinorSections(0)", 3, 4);
        addButton("setMinorSections(2)", 3, 5);
        /*Button b = new Button("100%"){
            public void buttonPressed(boolean pressed){
                s.setValue(100);
            }
        };
        addObject(b, colWidth, rowHeight*6);
        Button b2 = new Button("show value"){
            public void buttonPressed(boolean pressed){
                s.showValue(pressed);
            }
        };
        addObject(b2, 100, 150);
        Button b3 = new Button("major sections"){
            public void buttonPressed(boolean pressed){
                if(pressed){
                    s.setMajorSections(2);
                }else{
                    s.setMajorSections(0);
                }
            }
        };
        addObject(b3, 100, 250);
        Button b4 = new Button("minor sections"){
            public void buttonPressed(boolean pressed){
                if(pressed){
                    s.setMinorSections(2);
                }else{
                    s.setMinorSections(0);
                }
            }
        };
        addObject(b4, 100, 350);
        Button b5 = new Button("max value"){
            public void buttonPressed(boolean pressed){
                if(pressed){
                    s.setMaximumValue(1);
                }else{
                    s.setMaximumValue(100);
                }
            }
        };
        addObject(b5, 300, 50);
        Button b6 = new Button("percentage"){
            public void buttonPressed(boolean pressed){
                if(pressed){
                    s.showPercentage(true);
                }else{
                    s.showPercentage(false);
                }
            }
        };
        addObject(b6, 300, 150);*/
    }
    
    private void addButton(String text, int col, int row){
        row++;
        addObject(new Button(text, s), colWidth*(col-1)+colWidth/2, rowHeight*(row+rows+1)+rowHeight/2);
    }
}
