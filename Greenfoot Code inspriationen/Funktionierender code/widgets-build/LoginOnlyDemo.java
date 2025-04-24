import com.github.rccookie.greenfoot.widgets.*; // This imports all the core elements for widgets
import com.github.rccookie.greenfoot.widgets.prefabs.Login; // This contains some more advanced widgets
                                                            // build from the core widgets, including 'Login'

import greenfoot.Greenfoot; // For setting the world
import greenfoot.GreenfootImage; // For setting the background
import greenfoot.UserInfo; // For storing the current user
import greenfoot.World; // Well, this import should be obvious

/**
 * A demo class how to use the login system without
 * any more widgets.
 */
public class LoginOnlyDemo extends World {

    // Your normal world constructur
    public LoginOnlyDemo() {
        super(600, 400, 1); // The size may differ, for it to look good it should be at least 250x250

        new WidgetHolder( // Widgets aren't actors, so this actor shows some widgets as it's image.
                          // You can move this one around like a normal Actor.

            this, // This tells the widgetholder that it should cover this world. This is the world itself

            // Whenever you create a widget or a widgetholder you tell it right away what other widgets it
            // contains. This is what we do below:
            new Structure( // A Structure is a widget that simply holds multiple widgets. Order of the
                           // subwidgets is important: it's the draw order from bottom to top

                new Color(new greenfoot.Color(200, 100, 50)), // The background (also a widget). Color is
                                                              // not the normal greenfoot color class, to
                                                              // access that one you have to write
                                                              // greenfoot.Color instead

                new Login( // This is the login page itself. It only needs to know one thing: what to do
                           // if someone logged in. It will call the given method and pass as argument the
                           // UserInfo of the user that logged in, or null if a guest logged in.
                           // The login page is also just collection of more basic widgets. You can look
                           // into its source of you're interested!

                    user -> { // This is a 'lambda statement' and is executed when the user logs in.
                              // 'user' is like a method argument and will contain the userinfo of that
                              // person.

                        // Inside here is a normal method. It will not be executed right away but only if
                        // someone logged in.
                        // You can copy all this and just replace everything inside of the curly brackets { }

                        if(user != null) { // The user logged in has an account so we can access its username
                            System.out.println("Hello " + user.getUserName());
                        }
                        else System.out.println("Hello guest, you should register!"); // The user uses the
                                                                                      // guest login so we
                                                                                      // cannot get his name
                        Greenfoot.setWorld(new LoggedInWorld(user)); // Finally we tell Greenfoot to switch
                                                                     // to another world, and pass the
                                                                     // UserInfo of the current user
                    }
                )
            )
        );

        // The WidgetHolder will add itself to the world automatically, so you don't have to

        // The buttons that are being used by the login page need to be updated regularly.
        // I reccomend at least 40fps (~45 as Greenfoot speed) but higher is always better.
        // If your scenario requires a lower framerate you will hava to change that after
        // the user is logged in.
        Greenfoot.setSpeed(50); // Sets the max framerate to ~60fps (yes, 60 not 50, but 100 is also not 120 but unlimited)

        // Starts the act loop of this scenario, like clicking on 'Run'. Without the act loop running
        // the login page ain't work.
        Greenfoot.start();
    }



    /**
     * An example world to switch to once logged in.
     */
    private class LoggedInWorld extends World {

        // The user that logged in. You CANNOT user UserInfo.getMyInfo() to get the UserInfo, because there
        // may be someone else logged in on a different account, so store it.
        private final UserInfo currentUser;

        public LoggedInWorld(UserInfo currentUser) { // Takes the current userinfo as input
            super(600, 400, 1); // This can change, just an example

            this.currentUser = currentUser; // Save the current user globally

            // Sets the background red or green depending on weather an account or a guest is logged in
            GreenfootImage background = new GreenfootImage(getWidth(), getHeight());
            if(currentUser != null) background.setColor(Color.RED); // This still works, even though 'Color'
                                                                    // is not greenfoot.Color, but its the
                                                                    // same constants
            else background.setColor(Color.GREEN);
            background.fill();
            setBackground(background);
        }


        @Override
        public void act() {

            // Simply prints the current user. Simply an examply how to later access the UserInfo
            if(Math.random() < 0.01)
                System.out.println((currentUser != null ? currentUser.getUserName() : "Guest") + " is logged in!");
        }
    }
}
