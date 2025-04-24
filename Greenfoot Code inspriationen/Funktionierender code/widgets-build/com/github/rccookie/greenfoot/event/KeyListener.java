package com.github.rccookie.greenfoot.event;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.function.Consumer;

import com.github.rccookie.common.event.Time;

public class KeyListener implements EventListener {

    private static final long MIN_MULTICLICK_DELAY = 500;
    private static final double MULTICLICK_DELAY = 0.02;

    public final String key;
    private boolean down, pressed, released;
    private int count = 0;

    public boolean allowHolding = false;

    private long pressTimeStart;
    private double timeSinceLastPress;
    private Time time = new Time();

    private List<Consumer<String>> listeners = new ArrayList<Consumer<String>>();
    private List<Consumer<String>> releaseListeners = new ArrayList<Consumer<String>>();

    public KeyListener(String key){
        this.key = key;
        update();
    }

    public void update() {
        boolean temp = down;
        down = Input.keyState(key);
        if(temp != down) {
            pressed = down;
            released = !down;
            if(down) {
                count++;
                pressTimeStart = System.currentTimeMillis();
                timeSinceLastPress = 0;
                onPress();
            }
            else onRelease();
        }
        else{
            pressed = false;
            if(allowHolding && down && System.currentTimeMillis() - pressTimeStart >= MIN_MULTICLICK_DELAY) {
                timeSinceLastPress += time.deltaTime();
                while(timeSinceLastPress >= MULTICLICK_DELAY) {
                    timeSinceLastPress -= MULTICLICK_DELAY;
                    onRelease();
                    onPress();
                }
            }
        }
    }

    public void click(int count){
        for(int i=0; i<count; i++){
            click();
        }
    }
    public void click(){
        count++;
        onPress();
        onRelease();
    }

    public boolean down(){
        return down;
    }
    public boolean pressed(){
        return pressed;
    }
    public boolean released(){
        return released;
    }
    public int count(){
        return count;
    }


    private void onPress() {
        for(Consumer<String> listener : listeners) listener.accept(key);
    }

    private void onRelease() {
        for(Consumer<String> listener : releaseListeners) listener.accept(key);
    }


    public void addListener(Consumer<String> listener) {
        listeners.add(listener);
    }

    public void removeListener(Consumer<String> listener) {
        listeners.remove(listener);
    }

    public void addReleaseListener(Consumer<String> listener) {
        releaseListeners.add(listener);
    }

    public void removeReleaseListener(Consumer<String> listener) {
        releaseListeners.remove(listener);
    }
}