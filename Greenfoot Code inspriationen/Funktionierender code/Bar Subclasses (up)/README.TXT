PROJECT TITLE: Bar Sub-classes
PURPOSE OF PROJECT: To provide support for the Bar class created by danpost, giving the bar various
    functionalities
VERSION or DATE: 2.0 (July 4, 2012)
AUTHOR: danpost
USER INSTRUCTIONS: To make use of a special Bar object in one of your scenarios, you must either copy both the
    Bar JAVA file and the JAVA file of the special Bar(s) you want to use into your 'Greenfoot/lib/greenfoot/common'
    folder and import the classes into your scenario; or create the classes in your scenario and copy/paste the
    codes into them (replacing anything that might already be in them)
    
                                               **********  MseActBar  **********

 * CLASS 'MseActBar':
    A subclass of the Bar class that gives the bar user click functionality (the user can adjust the value of the
    bar as he/she sees fit); the boolean 'isChanged' method should be called from an act method (or a method it calls)
    to continuously check on any value changes.

   FIELDS:
    private int delay: default is four; the number of cycles between each mouse button held down value change
    private boolean changed: default is 'false'; used to signify a change in the value of the bar
    private boolean changing: default is 'false'; used to signify the value is currently being changed
    private int timer: default is zero; measures the delay between changes while mouse button is held down
    private int changeValue: default is zero; indicates the direction of change while mouse button is held down
    private Color altSafeColor: default is Color.blue; the safeColor of bar while mouse is hovering over it
    private Color altDangerColor: default is Color.magenta; the dangerColor of bar while mouse is hovering over it
    private Color altTextColor: default is Color.blue; the textColor of bar while mouse is hovering over it
    private Color altBackgroundColor: default is new Color(0, 0, 0, 0); the backgroundColor of bar while mouse is
        hovering over it
    private boolean mouseOn: default is 'false'; the current status of mouse hovering over the bar
    
   CONSTRUCTORS:
    * public MseActBar(String ref, String units, int val, int max)
     calls the super-class 'Bar' constructor with the same parameters
     
     @param 'ref': the title for the bar (displayed on the left side of the bar
     @param 'units': the unit of measure text (displayed after the value text on right side of the bar)
     @param 'val': the initial value of the bar 
     @param 'max': the maximum value of the bar
    
    METHODS:
     * public void act():
      allows the user to perform mouse presses and clicks on and beside the bar to change its value.
      
      The following four places can be clicked at:
       1) Outside, to left of color bar: minimizes value, and begins incrementing if mouse press held
       2) Outside, to right of color bar: maximizes value, and begins decrementing if mouse press held
       3) Inside, on left side of color bar: decrements value, and decrements more if mouse press held
       4) Inside, on right side of color bar: increments value, and increments more if mouse press held
       
      This method performs the following tasks:
       1) checks for mouse hovering on bar object and adjusts colors accordingly
       2) checks for mouse button pressing/holding/clicking, and performs the value changing accordingly
       
     * public void setAltSafeColor(Color color):
      sets a new mouse hovering safe color

      @param 'color': the new color to use as safeColor when mouse hovers over the bar

     * public Color getAltSafeColor():
      gets the current mouse hovering safe color

      @return: the current mouse hovering safe color
    
     * public void setAltDangerColor(Color color):
      sets a new mouse hovering danger color

      @param 'color': the new color to use as dangerColor when mouse hovers over the bar
    
     * public Color getAltDangerColor():
      gets the current mouse hovering danger color

      @return: the current mouse hovering danger color

     * public void setAltTextColor(Color color):
      sets a new mouse hovering text color

      @param 'color': the new color to use as textColor when mouse hovers over the bar

     * public Color getAltTextColor():
      gets the current mouse hovering text color

      @return: the current mouse hovering text color

     * public void setAltBackgroundColor(Color color):
      sets a new mouse hovering background color

      @param 'color': the new color to use as backgroundColor when mouse hovers over the bar

     * public Color getAltBackgroundColor():
      gets the current mouse hovering background color

      @return: the current mouse hovering background color

     * public boolean isChanged():
      gets the current status of whether the value has been changed or not (the value of 'changed' is
      automatically reset to 'false' when this method is called); this method should be called by some
      other class (usually the world, but can also be an actor) in its act() method to check on any
      value changes

      @return: the current true/false value of 'changed'

     * public void setDelay(int newDelay):
      sets a new number of cycles between each change in the value while the mouse button is held down
      
      @param 'newDelay': the new number of cycles to wait between each mouse button held down value change
      
      
                                               ************  SwitchBar  *************
                                              
 * CLASS 'SwitchBar':
    A subclass of the Bar class that gives the bar funtionality as like a switch (on/off) and can be clicked on by
    the user to change its status; the boolean 'isChanged' method should be called from an act method (or from a
    method that an act method calls) to continuously check on any value changes.  If just getting its value when
    needed, use the 'getState' method.  The actual value of the bar at all times is one; the true/false value comes
    from the 'breakLow' boolean value (which, when changed, fills or un-fills the bar image; and, when assigned
    alternating text, switches the text of the bar).

   FIELDS:
    private boolean changed: default is 'false'; used to signify a change in the value of the bar
    private Color altSafeColor: default is Color.blue; the safeColor of bar while mouse is hovering over it
    private Color altDangerColor: default is Color.gray; the dangerColor of bar while mouse is hovering over it
    private Color altTextColor: default is Color.blue; the textColor of bar while mouse is hovering over it
    private Color altBackgroundColor: default is new Color(0, 0, 0, 0); the backgroundColor of bar while mouse is
        hovering over it
    private boolean mouseOn: default is 'false'; the current status of mouse hovering over the bar
    private boolean alternatingText: default is 'false'; flags the use of seperate texts for the switch states
    private String[] valueText: default is { "", "" }; the seperate texts for the switch states, when used
    
   CONSTRUCTORS:
    * public SwitchBar(String ref, boolean status)
     calls the super-class 'Bar' constructor with the required parameters and adjust the bars characteristics
     to look and act more like a switch.
     
     @param 'ref': the title for the bar (displayed on the left side of the bar
     @param 'status': a flag indicating the initial status (true or false [on/off], [yes/no])
     
   METHODS:
    * public void act():
     allows the user to change the value of the switch by clicking on the bar object

     This method performs the following tasks:
      1) checks for mouse hovering on bar object and adjusts colors accordingly
      2) checks for mouse button clicks on bar object and performs the 'value' changing (switch-state)

    * public void setAltSafeColor(Color color):
     sets a new mouse hovering safe color

     @param 'color': the new color to use as safeColor when mouse hovers over the bar

    * public Color getAltSafeColor():
     gets the current mouse hovering safe color

     @return: the current mouse hovering safe color
    
    * public void setAltDangerColor(Color color):
     sets a new mouse hovering danger color

     @param 'color': the new color to use as dangerColor when mouse hovers over the bar
    
    * public Color getAltDangerColor():
     gets the current mouse hovering danger color

     @return: the current mouse hovering danger color

    * public void setAltTextColor(Color color):
     sets a new mouse hovering text color

     @param 'color': the new color to use as textColor when mouse hovers over the bar

    * public Color getAltTextColor():
     gets the current mouse hovering text color

     @return: the current mouse hovering text color

    * public void setAltBackgroundColor(Color color):
     sets a new mouse hovering background color

     @param 'color': the new color to use as backgroundColor when mouse hovers over the bar

    * public Color getAltBackgroundColor():
     gets the current mouse hovering background color

     @return: the current mouse hovering background color

    * public boolean isChanged():
     returns the current status of whether the value has been changed or not (the value of 'changed' is
     automatically reset to 'false' when this method is called); this method should be called by some
     other class (usually the world, but can also be an actor) in its act() method, or a method that the
     act method calls, to check on any value changes

     @return: the current true/false value of 'changed'
     
    * public boolean getState():
     returns the state of the switch, 'on' or 'off' (as 'true' or 'false' values)
     
     @return: the current true/false value of the switch (determined by the 'breakLow' boolean value)

     
                                              *********  TimerBar  ***********
                                                  
 * CLASS 'TimerBar':
    A subclass of the Bar class that gives the bar funtionality as like a timer (counting up or down); 
    it can count either milliseconds or frames (act cycles).
    
   FIELDS:
    private boolean countDownward: default is 'true'; used to signify the counting direction (up or down)
    private boolean countingFrames: default is 'true'; specifies what is counted (frames or milliseconds)
    private int delayCount: default is 100; the number of milliseconds or frames between value changes
    private int count: default is 0; the number of milliseconds or frames since the last value change
    private boolean countExpired: default is 'false'; indicates expiration status of the count of the bar
    private boolean isRunning: default is 'false'; the current status of the timer (running or not running)
    private long startTime: default is time initialized; used when counting milliseconds to calculate time intervals
    
   CONSTRUCTORS:
    * public TimerBar(String refText, String uomText, int maxValue, int delayCt, boolean ctDownward,
       boolean ctByFrame))
     calls the super-class 'Bar' constructor with the required parameters and saves the needed specifications
     
     @param 'refText': the title for the bar (displayed on the left side of the bar
     @param 'uomText': the unit of measure text for the text value of the bar
     @param 'maxValue': the maximum number of frame or millisecond sets to count
     @param 'delayCt': the number of frames or milliseconds neede to make one count set
     @param 'ctDownward': a flag indicating the direction of the count (downward or upward)
     @param 'ctByFrame': a flag indication what it will count (frames or milliseconds)
     
   METHODS:
    * public void act():
     runs the timer
     
    * public void begin():
     resets the timer and starts it
     
    * public void stop():
     stops the timer (basically, putting it on hold -- pausing it)
     
    * public void restart():
     if timer was 'stop'ped, the counting is resumed (un-pausing it)
     
    * public boolean isExpired()
     gets the expiration status of the timer (returns 'true' if the timer has completed its count)
     
     @return: the expiration status of the timer
     
    * public boolean getCountDownward()
     gets the current counting direction of the timer (up or down)
     
     @return: the current counting direction of the timer
     
    * public void setCountDownward(boolean ctDownward)
     sets the counting direction of the timer (up or down)
     
     @param 'ctDownward': the new counting direction of the timer
     
    * public int getDelayCount()
     gets the current number of frames or milliseconds per count change of the timer
     
     @return: the current number of frames or milliseconds used between each value change of the timer
     
    * public void setDelayCount(int delayCt)
     sets a new number of frames or milliseconds per count change of the timer
     
     @return: the new number of frames or milliseconds to use between each value change of the timer
     
                                              **********  END  *************


