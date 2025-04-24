import com.github.rccookie.greenfoot.ui.basic.UIWorld;
import com.github.rccookie.greenfoot.widgets.*;
import com.github.rccookie.greenfoot.widgets.prefabs.*;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.UserInfo;

public class TestWorld extends UIWorld {

    private static final long serialVersionUID = 7191679430557755504L;

    WidgetHolder h;

    public TestWorld() {
        super(600, 400, 1);
        Greenfoot.setSpeed(50);
        Greenfoot.start();

        // Ignore this part, it's only neccecary due to a change of where the passwords are saved (Basically copying accounts to new system)
        UserInfo user = UserInfo.getMyInfo();
        user.setInt(UserInfo.NUM_INTS - 1, user.getScore());
        user.store();

        h = new WidgetHolder(
            this,
            new Folder(
                new Page(
                    "login",
                    new Color(new greenfoot.Color(100, 120, 255)),
                    new Login(u -> {
                        h.find(Folder.class).setPage("desktop");
                        h.find("logout").as(Text.class).setTitle("Log out" + (u != null ? " " + u.getUserName() : ""));
                    }).setId("login")
                ),
                new Page(
                    "desktop",
                    new OSFrame()
                ).addOnLoad(() -> {
                    h.find(OSFrame.class).find("os_windows").as(Container.class).removeChild(Window.class);
                    h.find(OSFrame.class).addWindow(
                        new Window(
                            "A window",
                            new Color(new greenfoot.Color(230, 230, 230)),
                            new SizedButton(
                                new Button(
                                    () -> h.find(Folder.class).setPage("login"),
                                    new Color(Color.LIGHT_GRAY),
                                    new Text("Log out").setId("logout")
                                )
                            )
                        )
                    );
                })
            ).setId("main")
        );

        addObject(
            h,
            300,
            200
        );
    }



    public void test() {
        h.find("window_dimension").as(Dimension.class).setMaxWidth(0);
    }

    public void clear() {
        setBackground(new GreenfootImage(600, 400));
    }
}
