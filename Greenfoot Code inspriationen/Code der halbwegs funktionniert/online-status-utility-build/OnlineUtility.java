import java.awt.image.BufferedImage;

/**
 * A utility class to get information about weather the scenario is currently running
 * on the website or in the Greenfoot application.
 *
 * @author RcCookie
 * @version 1.0
 */
public class OnlineUtility {

    /**
     * All the methods are static, you don't need any instances of this class.
     */
    private OnlineUtility() {
        throw new UnsupportedOperationException("This class is not intended to be initialized, all its members are static");
    }



    /**
     * This variable stores the 'measured' online state. When its value is first requested
     * the test will run once.
     */
    private static final boolean REAL_ONLINE_STATE = testOnlineState();

    /**
     * A simple test to figure out weather this scenario is currently running online or offline.
     * More precisely, it actually rather tests if the scenario is running on native java like
     * it does on the Greenfoot application. If you somehow manage to run Java in your browser
     * like it was possible long ago this may also return 'offline'. By default the webside runs
     * the JavaScript conversion of your scenario on the website, and because that conversion
     * isn't so great it does not have all the classes and sometimes also produces other results
     * for the same tasks. This is how this test works.
     *
     * @return Weather the scenario is running online
     */
    private static boolean testOnlineState() {
        try {
            // BufferedImage is a java class from the java.awt package. It does not exist online and this statement will
            // therefore throw an exception (which we catch below).
            new BufferedImage(1, 1, 2);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Stores the emulated online state.
     *
     * <p>Note that this is not of the primitive type 'boolean' but of its so named wrapper class
     * 'Boolean'. Aside from 'true' and 'false' variables of this type can also have the value
     * 'null' like a normal object. In this case null indicates that we don't want to override
     * the real online state, and that is also the default value.
     */
    private static Boolean emulatedOnlineState = null;



    /**
     * Emulates the online state to be the given one. Passing 'null' will not emulate the
     * online state and return the 'real' state.
     *
     * <p>This is mainly intended for debugging purposes. It allows you to let your program
     * think its online and react accordingly, so you don't have to upload your scenario
     * just test it online.
     *
     * @param state The online state to emulate, 'null' for the real state
     */
    public static void emulateOnlineState(Boolean state) {
        emulatedOnlineState = state;
    }

    /**
     * Returns the online state of this session. If the state was emulated using
     * emulateOnlineState(Boolean) the emulated state will be returned. Otherwise (by default)
     * the real online state will be returned.
     *
     * <p>Knowing the online state of a session may be helpful in a number of ways, for example
     * for showing different content, avoiding bug and missing classes online or adjusting some
     * properties that have a different effect online (i.e. speed set to 50 online is only about
     * as fast as the speed 45 offline).
     *
     * @return The (possibly emulated) online state
     */
    public static boolean isOnline() {
        return emulatedOnlineState != null ? emulatedOnlineState : REAL_ONLINE_STATE;
    }

    /**
     * Returns the real online state, independent of the emulated one.
     *
     * <p><b>This method should only be used for very crucial functionality. Otherwise 'isOnline()'
     * should be preferred as it allows testing using emulation!</b>
     *
     * @return The real online state
     */
    public static boolean isRealOnline() {
        return REAL_ONLINE_STATE;
    }
}
