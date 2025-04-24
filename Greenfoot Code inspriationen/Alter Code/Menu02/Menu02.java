//34567890123456789012345678901234567890123456789012345678
import greenfoot.*;

/**
 * The purpose of this class is to create a world in 
 * which the behavior of a custom GUI menu is 
 * demonstrated. For more information, see the Scenario
 * Information in the IDE.
 * @author Dick Baldwin
 * @version 07/19/08
 */
public class Menu02 extends World{
  int margin = 10;//left edge of menu
  int topMargin = 5;
  public Menu02(){  
    super(400,260,1);
    addObject(new Driver(),0,0);
    setBackground(new GreenfootImage("sand2.jpg"));
  }//end constructor
}//end class Menu02
