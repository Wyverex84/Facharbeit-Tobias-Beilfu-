import greenfoot.*;

public class BackGround extends World
{
    //  Alter the parameters in the following statement to suit your needs
    //  The first string is like a title, the second is the units used in the bar
    //  The first integer is the initial value and the second is the maximum value
    public Bar bar = new Bar("Player 1", "Health Points", 25, 100);
    
    public BackGround()
    {    
        super(500, 520, 1);
        addObject(bar, 250, 40);
        GreenfootImage bg = getBackground();
        bg.setColor(Color.BLACK);
//   Un-comment the next to lines, as well as the commented lines in Bar class for a different view
//         bg.fill();
//         bg.setColor(Color.WHITE);
//   Do not un-comment this line.  If the two lines above are active, activate those commented in Bar class
        bg.drawString("SAMPLE PROGRESS BAR/HEALTH METER", 140, 12);
        bg.drawString("Use left/right arrow keys to change the value (color change at 20 percent)", 50, 80);
        bg.drawString("Click on the smiley to change the value from an actor class (random up/down)", 50, 95);
        bg.drawString("Constructor:  new Bar(String referenceName, String unitOfMeasure,", 50, 115);
        bg.drawString("                      int initialValue, int maximumValue);", 50, 130);
        bg.drawString("The Bar class has the following methods:", 50, 150);
        bg.drawString("void add(int addAmt)", 10, 165);
        bg.drawString("void subtract(int subAmt)", 260, 165); 
        bg.drawString("int getValue()", 10, 180);
        bg.drawString("void setValue(int newValue)", 260, 180);
        bg.drawString("String getReferenceName() ['Player 1' above]", 10, 195);
        bg.drawString("void setReferenceName(String refName)", 260, 195);
        bg.drawString("String getUnitOfMeasure() ['Health Points']", 10, 210);
        bg.drawString("void setUnitOfMeasure(String uom)", 260, 210);
        bg.drawString("int getMaximumValue() [100]", 10, 225);
        bg.drawString("void setMaximumValue(int maxValue)", 260, 225);
        bg.drawString("int getMinimumValue() [0]", 10, 240);
        bg.drawString("void setMinimumValue(int minValue)", 260, 240);
        bg.drawString("int getBarWidth() [100]", 10, 255);
        bg.drawString("void setBarWidth(int barWide)", 260, 255);
        bg.drawString("int getBarHeight() [10]", 10, 270);
        bg.drawString("void setBarHeight(int barHigh)", 260, 270);
        bg.drawString("int getBreakPercent() [20]", 10, 285);
        bg.drawString("void setBreakPercent(int breakPct)", 260, 285);
        bg.drawString("int getBreakValue() [na]", 10, 300);
        bg.drawString("void setBreakValue(int breakVal)", 260, 300);
        bg.drawString("boolean getBreakLow() [true]", 10, 315);
        bg.drawString("void setBreakLow(boolean brkLow)", 260, 315);
        bg.drawString("Color getSafeColor() [Color.GREEN]", 10, 330);
        bg.drawString("void setSafeColor(Color cSafe)", 260, 330);
        bg.drawString("Color getDangerColor() [Color.RED]", 10, 345);
        bg.drawString("void setDangerColor(Color cDanger)", 260, 345);
        bg.drawString("Color getTextColor() [Color.BLACK]", 10, 360);
        bg.drawString("void setTextColor(Color cText)", 260, 360);
        bg.drawString("Color getBackgroundColor() [Color(0,0,0,0)]", 10, 375);
        bg.drawString("void setBackgroundColor(Color cBack)", 260, 375);
        bg.drawString("float getFontSize() [18f]", 10, 390);
        bg.drawString("void setFontSize(float size)", 260, 390);
        bg.drawString("boolean getShowTextualUnits() [true]", 10, 405);
        bg.drawString("void setShowTextualUnits(boolean show)", 260, 405);
        bg.drawString("boolean getUsingBreakValue() [false] -- see comment on this below", 10, 420);
        bg.drawString("barWidth and barHeight are for the color portion of the bar only", 10, 445);
        bg.drawString("Setting BREAK_PERCENT to zero will give constant color to bar", 10, 460);
        bg.drawString("Using setBreakValue or setBreakPercent changes usingBreakValue accordingly", 10, 475);
        bg.drawString("Added methods for more flexibility (minimumValue and breakValue)", 10, 495);
        bg.drawString("Author: danpost         Version: 2.1       Last modified: Feb. 9, 2012", 10, 515);
        addObject(new Smiley(), 30, 30);
    }
    
    public void act()
    {
        if (bar.getValue() == bar.getMinimumValue())
        {
            if (getObjects(GameOver.class).isEmpty()) showGameOver();
            return;
        }
        String key = Greenfoot.getKey();
        if ("left".equals(key)) bar.subtract(1);
        if ("right".equals(key)) bar.add(1);
    }
    
    private void showGameOver()
    {
        addObject(new GameOver(), getWidth() / 2, getHeight() / 2);
    }
}
