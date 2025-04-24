import com.github.rccookie.greenfoot.core.Color;
import com.github.rccookie.greenfoot.core.Core;
import com.github.rccookie.greenfoot.core.Map;
import com.github.rccookie.greenfoot.ui.Switch;
import com.github.rccookie.greenfoot.ui.Text;
import com.github.rccookie.greenfoot.ui.TextButton;
import com.github.rccookie.util.Console;

public class ShowMap extends Map {

    /**
     * You can call this button by clicking onto the example button or by right-clicking
     * onto 'ShowMap' and selecting 'usageExample()'.
     */
    public static void usageExample() {

        // Simply get the current online state
        if(OnlineUtility.isOnline()) System.out.println("We are online");
        else System.out.println("We are offline");

        // Here we tell the program to think we are online
        OnlineUtility.emulateOnlineState(true);
        if(OnlineUtility.isOnline()) System.out.println("We are online (at least we think we are)");
        else System.out.println("We are offline (at least we think we are)");

        // We can always use this to get the real online state. However, use isOnline wherever you can to be
        // able to test it using emulation
        System.out.println("Are we actually online: " + OnlineUtility.isRealOnline());

        // Emulating 'null' means we go back to the real state
        OnlineUtility.emulateOnlineState(null);
        if(OnlineUtility.isOnline()) System.out.println("We are online (at least we think we are)");
        else System.out.println("We are offline (at least we think we are)");
    }


    /**
     * This method is called whenever a switch was changed. It updates the title of the text elements.
     */
    private void updateText() {
        // This updates the title of the upper text
        find(Text.class, "onlineStateText").ifPresent(t -> t.setTitle("Online: " + OnlineUtility.isOnline()));
        // This updates the title of the lower text. It ain't change, but to prove that it's being updated anyways
        find(Text.class, "realStateText").ifPresent(t -> t.setTitle("Really online: " + OnlineUtility.isRealOnline()));
    }































    // --------------------------------------------------------
    // Don't look here...
    // --------------------------------------------------------




    public ShowMap() {

        Console.getFilter(Core.class).setEnabled("info", false);

        Core.setFps(60);
        OnlineUtility.emulateOnlineState(null);

        addRelative(new Text().setId("onlineStateText"), 0.5, 0.25);
        addRelative(new Text().setColor(Color.GRAY).setId("realStateText"), 0.5, 0.3);

        addRelative(new Switch().addSwitchAction(state -> {
            find(Switch.class, "emulatedState").ifPresent(s -> {
                s.setEnabled(state);
                OnlineUtility.emulateOnlineState(state ? s.getState() : null);
            });
            updateText();
        }), 0.5, 0.65);
        addRelative(new Text("Emulate online state?"), 0.5, 0.7);

        addRelative(new Switch().addSwitchAction(state -> {
            OnlineUtility.emulateOnlineState(state);
            updateText();
        }).setId("emulatedState"), 0.5, 0.85);
        find(Switch.class, "emulatedState").ifPresent(s -> {
            s.setEnabled(false);
            s.setState(OnlineUtility.isOnline());
        });
        addRelative(new Text("Emulated online state"), 0.5, 0.9);

        updateText();

        if(!OnlineUtility.isOnline())
            addRelative(new TextButton("Run example").addOnClick(ShowMap::usageExample), 0.75, 0.5);
    }
}
