package com.github.rccookie.greenfoot.widgets.prefabs;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import com.github.rccookie.greenfoot.widgets.*;

import greenfoot.GreenfootImage;
import greenfoot.UserInfo;

/**
 * A login page that supports logging in, registering, resetting your
 * password and using a guest account. It uses the last integer slot
 * of the {@link UserInfo} for the password of each user with an
 * account.
 */
public class Login extends Dimension {

    static final String LOGIN_PAGE = "login_home_page", RESET_PASSWORD_PAGE = "login_reset_password_page", REGISTER_PAGE = "login_register_page";
    static final String RESET_SUCCESS_PAGE = "login_reset_success_page", REGISTER_SUCCESS_PAGE = "login_register_success_page";
    static final String WELCOME_PAGE = "login_wecome_page", WELCOME_TEXT = "login_welcome_text", WELCOME_IMAGE = "login_welcome_user_image";
    static final String USERNAME = "login_username_button", PASSWORD = "login_password_button";
    static final String PASSWORD_RESET_1 = "login_reset_password_button_1", PASSWORD_RESET_2 = "login_reset_password_button_2";
    static final String PASSWORD_REGISTER_1 = "login_register_password_button_1", PASSWORD_REGISTER_2 = "login_register_password_button_2";
    static final String ERROR = "login_error_text";
    static final String EXTRA_BUTTON = "login_extra_function_button";

    static final int INPUT_BUTTON_HEIGHT = 26;
    static final greenfoot.Color BUTTON_COLOR = new greenfoot.Color(242, 242, 242);
    static final long WELCOME_TIME = 1000;

    private State state;
    private UserInfo user = null;

    /**
     * Creates a new login page.
     * 
     * @param onLogin The code to run if the user logged in. As argument
     *                there will be passed the user that logged in, or
     *                {@code null} if a guest logged in
     */
    @SuppressWarnings("unchecked")
    public Login(Consumer<UserInfo> onLogin) {
        super(250, 250);
        final UserInfo user = UserInfo.getMyInfo();
        if(user == null) state = State.GUEST;
        else state = user.getInt(UserInfo.NUM_INTS - 1) == 0 ? State.NEEDS_REGISTERING : State.REGISTERED;
        addChildren(
            new Color(new greenfoot.Color(0, 0, 0, 50)),
            new Offset(
                0.5,
                0.45,
                new Dimension(
                    0,
                    18,
                    new BigText(
                        "",
                        Color.RED
                    ).setId(ERROR).setVisible(false)
                )
            ),
            new Folder(
                new Page(
                    LOGIN_PAGE,
                    new Title("Login"),
                    new Offset(
                        0.5,
                        0.65,
                        new Dimension(
                            150,
                            2 * INPUT_BUTTON_HEIGHT + 15,
                            Align.top(
                                new SizedButton(
                                    new TextInputButton("Username", "Enter your username:").setId(USERNAME).as(Button.class),
                                    0,
                                    INPUT_BUTTON_HEIGHT
                                )
                            ),
                            Align.bottomleft(
                                new PasswordInputButton(PASSWORD, -INPUT_BUTTON_HEIGHT, "Password", "Enter your password:")
                            ),
                            Align.bottomright(
                                new SizedButton(
                                    new TextButton(
                                        "\u2794",
                                        Color.DARK_GRAY,
                                        new greenfoot.Color(220, 220, 220),
                                        () -> {
                                            String username = find(USERNAME).as(TextInputButton.class).getEnteredText();
                                            String p = find(PASSWORD).as(TextInputButton.class).getEnteredText();
                                            Text error = find(ERROR).as(Text.class);
                                            if(username == null || p == null) {
                                                error.setTitle("Please enter username and password.").setVisible(true);
                                                return;
                                            }
                                            int entered = passwordHash(p);
                                            int actual = -1;
                                            UserInfo actualUser = null;
                                            if(user != null && Objects.equals(user.getUserName(), username)) {
                                                actual = (actualUser = user).getInt(UserInfo.NUM_INTS - 1);
                                            }
                                            else {
                                                List<UserInfo> users = UserInfo.getTop(100000);
                                                for(UserInfo u : users) {
                                                    if(Objects.equals(u.getUserName(), username)) {
                                                        actual = (actualUser = u).getInt(UserInfo.NUM_INTS - 1);
                                                        break;
                                                    }
                                                }
                                            }
                                            if(entered != actual) {
                                                error.setTitle("Incorrect username or password.").setVisible(true);
                                                return;
                                            }
                                            this.user = actualUser;
                                            find(Folder.class).setPage(WELCOME_PAGE);
                                        }
                                    ),
                                    INPUT_BUTTON_HEIGHT,
                                    INPUT_BUTTON_HEIGHT
                                )
                            )
                        )
                    ),
                    new Offset(
                        0.5,
                        0.9
                    ).setId(EXTRA_BUTTON)
                ).addOnLoad(() -> {
                    find(PASSWORD).as(TextInputButton.class).clear();
                    find(ERROR).setVisible(false).as(Text.class).setTitle("");
                }),
                new Page(
                    RESET_PASSWORD_PAGE,
                    new Title("Reset password"),
                    new Offset(
                        0.5,
                        0.65,
                        new Dimension(
                            150,
                            2 * INPUT_BUTTON_HEIGHT + 15,
                            Align.top(
                                new PasswordInputButton(PASSWORD_RESET_1, 0, "New Password", "Enter your new password:")
                            ),
                            Align.bottomleft(
                                new PasswordInputButton(PASSWORD_RESET_2, -INPUT_BUTTON_HEIGHT, "Repeat", "Enter your new password again:")
                            ),
                            Align.bottomright(
                                new SizedButton(
                                    new TextButton(
                                        "\u2794",
                                        Color.RED,
                                        new greenfoot.Color(220, 220, 220),
                                        () -> {
                                            Text error = find(ERROR).setVisible(false).as(Text.class).setTitle("").as(Text.class);
                                            String p1 = find(PASSWORD_RESET_1).as(TextInputButton.class).getEnteredText();
                                            String p2 = find(PASSWORD_RESET_2).as(TextInputButton.class).getEnteredText();
                                            if(p1 == null || p2 == null) {
                                                error.setTitle("Please enter both fields.").setVisible(true);
                                                return;
                                            }
                                            if(!p1.equals(p2)) {
                                                error.setTitle("The two passwords differ.").setVisible(true);
                                                return;
                                            }
                                            if(p1.length() < 5) {
                                                error.setTitle("The password needs at least 5 chars.").setVisible(true);
                                                return;
                                            }
                                            if(p1.length() > 30) {
                                                error.setTitle("The password can have at most 30 chars.").setVisible(true);
                                                return;
                                            }
                                            try {
                                                user.setInt(UserInfo.NUM_INTS - 1, passwordHash(p1));
                                                user.store();
                                                find(Folder.class).setPage(RESET_SUCCESS_PAGE);
                                            } catch(Exception e) {
                                                error.setTitle("Something went wrong. Try again later.").setVisible(true);
                                                e.printStackTrace(System.out);
                                            }
                                        }
                                    ),
                                    INPUT_BUTTON_HEIGHT,
                                    INPUT_BUTTON_HEIGHT
                                )
                            )
                        )
                    ),
                    new Offset(
                        0.5,
                        0.9,
                        new SizedButton(
                            new Button(
                                () -> find(Folder.class).setPage(LOGIN_PAGE),
                                new BigText("Back", Color.WHITE)
                            ),
                            60,
                            15
                        )
                    )
                ).addOnLoad(() -> {
                    find(PASSWORD_RESET_1).as(TextInputButton.class).clear();
                    find(PASSWORD_RESET_2).as(TextInputButton.class).clear();
                    find(ERROR).setVisible(false).as(Text.class).setTitle("");
                }),
                new Page(
                    RESET_SUCCESS_PAGE,
                    new Text("Successfully changed password.", Color.WHITE),
                    new Offset(
                        0.5,
                        0.9,
                        new SizedButton(
                            new Button(
                                () -> find(Folder.class).setPage(LOGIN_PAGE),
                                new BigText("Back", Color.WHITE)
                            ),
                            60,
                            15
                        )
                    )
                ),
                new Page(
                    REGISTER_PAGE,
                    new Title("Register '" + (user != null ? user.getUserName() : "") + "'"),
                    new Offset(
                        0.5,
                        0.9,
                        new SizedButton(
                            new Button(
                                () -> find(Folder.class).setPage(LOGIN_PAGE),
                                new BigText("Back", Color.WHITE)
                            ),
                            60,
                            15
                        )
                    ),
                    new Offset(
                        0.5,
                        0.65,
                        new Dimension(
                            150,
                            2 * INPUT_BUTTON_HEIGHT + 15,
                            Align.top(
                                new PasswordInputButton(PASSWORD_REGISTER_1, 0, "New Password", "Enter your new password:")
                            ),
                            Align.bottomleft(
                                new PasswordInputButton(PASSWORD_REGISTER_2, -INPUT_BUTTON_HEIGHT, "Repeat", "Enter your new password again:")
                            ),
                            Align.bottomright(
                                new SizedButton(
                                    new TextButton(
                                        "\u2794",
                                        Color.DARK_GRAY,
                                        new greenfoot.Color(220, 220, 220),
                                        () -> {
                                            Text error = find(ERROR).setVisible(false).as(Text.class).setTitle("").as(Text.class);
                                            String p1 = find(PASSWORD_REGISTER_1).as(TextInputButton.class).getEnteredText();
                                            String p2 = find(PASSWORD_REGISTER_2).as(TextInputButton.class).getEnteredText();
                                            if(p1 == null || p2 == null) {
                                                error.setTitle("Please enter both fields.").setVisible(true);
                                                return;
                                            }
                                            if(!p1.equals(p2)) {
                                                error.setTitle("The two passwords differ.").setVisible(true);
                                                return;
                                            }
                                            if(p1.length() < 5) {
                                                error.setTitle("The password needs at least 5 chars.").setVisible(true);
                                                return;
                                            }
                                            if(p1.length() > 30) {
                                                error.setTitle("The password can have at most 30 chars.").setVisible(true);
                                                return;
                                            }
                                            try {
                                                user.setInt(UserInfo.NUM_INTS - 1, passwordHash(p1));
                                                user.store();
                                                find(Folder.class).setPage(REGISTER_SUCCESS_PAGE);
                                                state = State.REGISTERED;
                                            } catch(Exception e) {
                                                error.setTitle("Something went wrong. Try again later.").setVisible(true);
                                            }
                                        }
                                    ),
                                    INPUT_BUTTON_HEIGHT,
                                    INPUT_BUTTON_HEIGHT
                                )
                            )
                        )
                    )
                ),
                new Page(
                    REGISTER_SUCCESS_PAGE,
                    new Text("Successfully created accout.", Color.WHITE),
                    new Offset(
                        0.5,
                        0.9,
                        new SizedButton(
                            new Button(
                                () -> {
                                    find(Folder.class).setPage(LOGIN_PAGE);
                                    Structure extraButton = find(EXTRA_BUTTON).as(Structure.class);
                                    extraButton.removeChild(SizedButton.class);
                                    extraButton.addChild(resetPasswordButton());
                                    find(USERNAME).as(TextInputButton.class).enterText(user.getUserName());
                                },
                                new BigText("Back", Color.WHITE)
                            ),
                            60,
                            15
                        )
                    )
                ),
                new Page(
                    WELCOME_PAGE,
                    new Dimension(
                        0,
                        20,
                        new BigText("Welcome!", Color.WHITE).setId(WELCOME_TEXT),
                        new Script(new Runnable() {
                            boolean executed = false;
                            long start = -1;
                            public void run() {
                                if(start == -1) {
                                    start = System.currentTimeMillis();
                                    find(WELCOME_TEXT).as(BigText.class)
                                        .setTitle("Welcome" + (Login.this.user != null ? " " + Login.this.user.getUserName() : "") + "!");
                                    if(Login.this.user != null) {
                                        GreenfootImage userImage = Login.this.user.getUserImage();
                                        find(WELCOME_IMAGE).as(Image.class).setImage(userImage);
                                        find(WELCOME_IMAGE).getParent().setVisible(true);
                                    }
                                }
                                else if(!executed && System.currentTimeMillis() - start >= WELCOME_TIME) {
                                    onLogin.accept(Login.this.user);
                                    start = -1;
                                    find(Folder.class).setPage(LOGIN_PAGE);
                                }
                            }
                        })
                    ),
                    new Offset(
                        0.5,
                        0.25,
                        new Dimension(
                            56,
                            56,
                            new Color(new greenfoot.Color(50, 50, 50)),
                            new Image(new GreenfootImage(1, 1)).setId(WELCOME_IMAGE)
                        ).setVisible(false)
                    )
                ).addOnLoad(() -> find(ERROR).setVisible(false))
            )
        );



        if(state == State.NEEDS_REGISTERING) {
            find(EXTRA_BUTTON).as(Structure.class).addChild(
                new SizedButton(
                    new Button(
                        () -> find(Folder.class).setPage(REGISTER_PAGE),
                        new BigText("Register", Color.WHITE)
                    ),
                    100,
                    15
                )
            );
        }
        else if(state == State.REGISTERED) {
            find(USERNAME).as(TextInputButton.class).enterText(user.getUserName());
            find(EXTRA_BUTTON).as(Structure.class).addChild(resetPasswordButton());
        }
        else {
            find(EXTRA_BUTTON).as(Structure.class).addChildren(
                new Offset(
                    0.5,
                    0.5,
                    0,
                    -10,
                    new SizedButton(
                        new Button(
                            () -> find(Folder.class).setPage(WELCOME_PAGE),
                            new BigText("Log in as guest", Color.WHITE)
                        ),
                        100,
                        15
                    )
                ),
                new Offset(
                    0.5,
                    0.5,
                    0,
                    10,
                    new Dimension(
                        -10,
                        13,
                        new BigText("You need to be logged in into Greenfoot to register", new greenfoot.Color(220, 220, 220))
                    )
                )
            );
        }
    }

    private final Widget resetPasswordButton() {
        return new SizedButton(
            new Button(
                () -> find(Folder.class).setPage(RESET_PASSWORD_PAGE),
                new BigText("Reset password", Color.WHITE)
            ).useHoverOutline(false),
            100,
            15
        );
    }



    public static final int passwordHash(String password) {
        int hash = password.length() + 1;
        for(int i=0; i<password.toCharArray().length; i++) {
            hash += (i+1) * password.charAt(i);
        }
        return hash;
    }



    private enum State {
        GUEST,
        NEEDS_REGISTERING,
        REGISTERED
    }

    private class PasswordInputButton extends SizedButton {

        PasswordInputButton(String id, int width, String title, String prompt) {
            super(
                new TextInputButton(title, prompt) {
                    String text = "";
                    public TextInputButton enterText(String text) {
                        super.enterText(text);
                        this.text = text != null ? text : "";
                        if(isSelected()) {
                            StringBuilder passwordText = new StringBuilder(text.length());
                            for(int i=0; i<text.length(); i++) passwordText.append('\u2022');
                            find(TEXT).as(Text.class).setTitle(passwordText.toString());
                        }
                        return this;
                    }
                    public String getEnteredText() {
                        return isSelected() ? text : null;
                    };
                }.setId(id).as(Button.class),
                width,
                INPUT_BUTTON_HEIGHT
            );
        }
    }
}
