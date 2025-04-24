package com.github.rccookie.greenfoot.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NavigatonListener {

    private static final long TIME_BEFORE_CLICKING = 500;


    private boolean allowHolding = true;

    private KeyListener up = new KeyListener("up");
    private KeyListener down = new KeyListener("down");
    private KeyListener left = new KeyListener("left");
    private KeyListener right = new KeyListener("right");

    private String lastKey = "";
    private long timeAtPress = 0;

    private List<Consumer<String>> naviListeners = new ArrayList<>();

    public NavigatonListener() {
        up.addListener(key -> onClick(key));
        up.addReleaseListener(key -> onRelease());
        down.addListener(key -> onClick(key));
        down.addReleaseListener(key -> onRelease());
        left.addListener(key -> onClick(key));
        left.addReleaseListener(key -> onRelease());
        right.addListener(key -> onClick(key));
        right.addReleaseListener(key -> onRelease());
    }


    private void onClick(String key) {
        lastKey = key;
        timeAtPress = System.currentTimeMillis();
        runListeners(key);
    }

    private void onRelease() {
        lastKey = "";
    }


    private void runListeners(String key) {
        for(Consumer<String> listener : naviListeners) listener.accept(key);
    }


    public void update() {
        up.update();
        down.update();
        left.update();
        right.update();

        if(allowHolding && !lastKey.equals("")) {
            if(System.currentTimeMillis() - timeAtPress >= TIME_BEFORE_CLICKING) runListeners(lastKey);
        }
    }


    public void addNaviListener(Consumer<String> listener) {
        naviListeners.add(listener);
    }

    public void removeNaviListener(Consumer<String> listener) {
        naviListeners.remove(listener);
    }


    public void allowNaviHolding(boolean flag) {
        if(allowHolding == flag) return;
        up.allowHolding = flag;
        down.allowHolding = flag;
        left.allowHolding = flag;
        right.allowHolding = flag;
    }

    public boolean getAllowHolding() {
        return allowHolding;
    }
}
