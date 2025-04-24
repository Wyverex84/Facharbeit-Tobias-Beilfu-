package com.github.rccookie.greenfoot.ui;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.rccookie.greenfoot.event.KeyListener;
import com.github.rccookie.greenfoot.event.MouseListener;
import com.github.rccookie.greenfoot.event.NavigatonListener;
import com.github.rccookie.geometry.Vector;
import com.github.rccookie.greenfoot.core.Color;
import com.github.rccookie.greenfoot.core.GameObject;
import com.github.rccookie.greenfoot.core.Image;
import com.github.rccookie.greenfoot.core.KeyState;
import com.github.rccookie.greenfoot.core.Map;
import com.github.rccookie.greenfoot.ui.util.Interactable;
import com.github.rccookie.greenfoot.ui.util.Theme;

public class UINavigator extends GameObject {

    public static final int BORDER = 3;


    private Interactable focused = null;
    private final Map map;

    private final Theme theme = new Theme(Color.BLACK, Color.WHITE);
    private final NavigatonListener navi;
    private final KeyListener spaceListener;
    private final KeyListener tabListener;
    private final KeyListener escListener;
    private final MouseListener mouseListener;


    public UINavigator(Map map) {
        Objects.requireNonNull(map);
        this.map = map;

        map.add(this, Vector.of());
        
        navi = new NavigatonListener();
        navi.addNaviListener(k -> {
            switch (k) {
                case "up":
                    focus(getAbove());
                    break;
                case "down":
                    focus(getBelow());
                    break;
                case "left":
                    focus(getLeft());
                    break;
                case "right":
                    focus(getRight());
                    break;
            }
        });

        spaceListener = new KeyListener("space");
        spaceListener.addListener(() -> focused.click());

        tabListener = new KeyListener("tab");
        tabListener.addListener(() -> {
            focused = KeyState.of("shift").down ? getLast() : getNext();
            updateImage();
        });

        escListener = new KeyListener("escape");
        escListener.addListener(() -> focus(null));

        mouseListener = new MouseListener();
        mouseListener.addListener(m -> focus(null));

        updateImage();
    }

    public UINavigator(Interactable focused) {
        this(focused.getMap().get());
        this.focused = focused;
        setLocation(focused);
        updateImage();
    }



    @Override
    public void update() {
        tabListener.update();
        if(focused != null) escListener.update();
        if(focused != null) mouseListener.update();
        if(focused != null) {
            navi.update();
            spaceListener.update();
            setLocation(focused.getLocation());
            setRotation(focused.getAngle());
        }
    }



    private Interactable getAbove() {
        Set<Interactable> buttons = focused != null ? focused.getFocusable() : map.findAll(Interactable.class);
        if(buttons.size() == 0) return null;
        removeIf(buttons, b -> {
            if(b == focused) return true;
            Vector between = Vector.between(getLocation(), b.getLocation());
            double angle = between.angle();
            return angle < -135 || angle > -45; 
        });
        if(buttons.size() == 0) return focused;
        return buttons.stream().sorted(getComparator()).findFirst().get();
    }

    private Interactable getBelow() {
        Set<Interactable> buttons = focused != null ? focused.getFocusable() : map.findAll(Interactable.class);
        if(buttons.size() == 0) return null;
        removeIf(buttons, b -> {
            if(b == focused) return true;
            Vector between = Vector.between(getLocation(), b.getLocation());
            double angle = between.angle();
            return angle < 45 || angle > 135; 
        });
        if(buttons.size() == 0) return focused;
        return buttons.stream().sorted(getComparator()).findFirst().get();
    }

    private Interactable getLeft() {
        Set<Interactable> buttons = focused != null ? focused.getFocusable() : map.findAll(Interactable.class);
        if(buttons.size() == 0) return null;
        removeIf(buttons, b -> {
            if(b == focused) return true;
            Vector between = Vector.between(getLocation(), b.getLocation());
            double angle = between.angle();
            return !(angle <= -135 || angle > 135); 
        });
        if(buttons.size() == 0) return focused;
        return buttons.stream().sorted(getComparator()).findFirst().get();
    }

    private Interactable getRight() {
        Set<Interactable> buttons = focused != null ? focused.getFocusable() : map.findAll(Interactable.class);
        if(buttons.size() == 0) return null;
        removeIf(buttons, b -> {
            if(b == focused) return true;
            Vector between = Vector.between(getLocation(), b.getLocation());
            double angle = between.angle();
            return angle < -45 || angle > 45;
        });
        if(buttons.size() == 0) return focused;
        return buttons.stream().sorted(getComparator()).findFirst().get();
    }

    private Interactable getNext() {
        Set<Interactable> buttons = focused != null ? focused.getFocusable() : map.findAll(Interactable.class);
        if(buttons.size() == 0) return null;
        try {
            List<Interactable> asList = List.copyOf(buttons);
            return asList.get(asList.indexOf(focused) + 1);
        } catch(Exception e) { return buttons.stream().findAny().get(); }
    }

    private Interactable getLast() {
        Set<Interactable> buttons = focused != null ? focused.getFocusable() : map.findAll(Interactable.class);
        if(buttons.size() == 0) return null;
        try {
            List<Interactable> asList = List.copyOf(buttons);
            return asList.get(asList.indexOf(focused) - 1);
        } catch(Exception e) { return List.copyOf(buttons).get(buttons.size() - 1); }
    }



    /**
     * Because Greenfoot does not support the method for list online.
     */
    private void removeIf(Set<Interactable> buttons, Predicate<Interactable> filter) {
        Set<Interactable> removed = new HashSet<>();
        for(Interactable i : buttons) if(filter.test(i)) removed.add(i);
        buttons.removeAll(removed);
    }



    private Comparator<Interactable> getComparator() {
        return (a, b) -> {
            double dA = Vector.between(getLocation(), a.getLocation()).sqrAbs();
            double dB = Vector.between(getLocation(), b.getLocation()).sqrAbs();
            if(dA < dB) return -1;
            if(dB < dA) return 1;
            return 0;
        };
    }


    private void updateImage() {
        if(focused == null) {
            setImage((Image)null);
            return;
        }
        Image image = new Image(focused.getImage().getWidth() + 2 * BORDER, focused.getImage().getHeight() + 2 * BORDER);
        image.setColor(theme.main());
        for(int i=0; i<BORDER - 1; i++) image.drawRect(i, i, image.getWidth() - 2 * i - 1, image.getHeight() - 2 * i - 1);
        image.setColor(theme.second());
        image.drawRect(BORDER - 1, BORDER - 1, focused.getImage().getWidth() + 1, focused.getImage().getHeight() + 1);
        setImage(image);
    }


    public void focus(Interactable button) {
        focused = (button != null && button.getMap().get() == map) ? button : null;
        updateImage();
    }



    public void allowNaviHolding(boolean flag) {
        navi.allowNaviHolding(flag);
    }

    public boolean getAllowNaviHolding() {
        return navi.getAllowHolding();
    }


    public void allowTabHolding(boolean flag) {
        tabListener.allowHolding = flag;
    }

    public boolean getAllowTabHolding() {
        return tabListener.allowHolding;
    }
}
