package com.github.rccookie.greenfoot.util;

import java.util.ArrayList;

import greenfoot.*;
import com.github.rccookie.common.util.ClassTag;
import com.github.rccookie.common.util.Console;
import com.github.rccookie.common.util.Line;
import com.github.rccookie.common.util.Lists;
import com.github.rccookie.common.util.Map;
import com.github.rccookie.common.geometry.Vector2D;
import com.github.rccookie.greenfoot.event.*;
import com.github.rccookie.greenfoot.game.*;
import com.github.rccookie.greenfoot.game.physics.*;
import com.github.rccookie.greenfoot.game.util.*;
import com.github.rccookie.greenfoot.ui.advanced.*;
import com.github.rccookie.greenfoot.ui.basic.*;

public class Compiler {
    public static void testStuff(){
        Console.log("Starting test...\n");

        Console.log("rccookie.util.Console");
        Console.log();

        /*Console.log("Initializing Greenfoot");
        WorldHandler.initialise();
        Simulation.initialize();
        GreenfootUtil.initialise(GreenfootUtilDelegateIDE.getInstance());
        Console.log("Successfully initialized Greenfoot");*/

        Console.log("event"); {
            Console.log("KeyListener");
            new KeyListener("a");
        }

        Console.log("game"); {
            Console.log("Advanced Actor");
            new AdvancedActor() { private static final long serialVersionUID = 1L; };

            Console.log("physics"); {
                Console.log("CircleCollider");
                new CircleCollider(1, new Vector2D());
                Console.log("Collider");
                new BoxCollider(new Actor() { });
                Console.log("Cylinder");
                new Cylinder(1, 1);
                Console.log("PhysicalActor");
                new PhysicalActor() { private static final long serialVersionUID = 1L; };
            }

            Console.log("util"); {
                Console.log("ActorTable");
                new ActorTable<Actor>(1, 1);
            }
        }

        // Rendering

        Console.log("ui"); {
            Console.log("basic"); {
                Console.log("Text");
                new Text();
                Console.log("Button");
                new TextButton("Hello World!");
                Console.log("Fade");
                Fade.fadeIn(Color.BLACK, 1);
                Console.log("ObjectAdder");
                new ObjectAdder(new Actor() { });
                Console.log("Slider");
                new Slider();
                Console.log("UIPanel");
                new UIPanel(new greenfoot.World(1, 1, 1) { });
                Console.log("UIWorld");
                new UIWorld(100, 100, 1) { private static final long serialVersionUID = 1L; };
            }

            Console.log("advanced"); {
                Console.log("Container");
                new Container(10, 10, 1);
                Console.log("Camera");
                Console.log(">>" + Camera.BACKGROUND);
                Console.log("DropDownMenu");
                new DropDownMenu("Hello", new String[] {"Option 1", "Option 2", "Option 3"});
                Console.log("FpsDisplay");
                new FpsDisplay();
                Console.log("ExponentialSlider");
                new ExponentialSlider(0, 1, 100, Color.BLACK, Color.BLACK);
                Console.log("TextField");
                Console.log(">>" + TextField.BORDER);
                //new TextField(new Text("Hello"), 100, 50);
                Console.log("NumberField");
                Console.log(">>" + NumberField.DEFAULT_RETURN);
                //new NumberField();
                Console.log("Scoreboard");
                new Scoreboard(100, 100);
                Console.log("Switch");
                new Switch();
            }
        }

        Console.log("util"); {
            Console.log("TagTable/ ClassTag");
            ClassTag.tag(Compiler.class, "util");
            Console.log("Lists");
            Lists.any(new ArrayList<>(), info -> true);
            Console.log("Grid/ AbstractTable/ Table/ ActorTable");
            new ActorTable<>(1, 1);
            Console.log("InfinitiveGrid/ Plane/ Map");
            new Map<>();
            Console.log("Line");
            Console.log(">>", Line.COMPARATOR);
            /*Console.log("TreeLine");
            new com.github.rccookie.common.util.TreeLine<Integer, Object>() {
                static final long serialVersionUID = 1L;
                public Integer add(Object value) { return null; }
                public Integer addDown(Object value) { return null; }
                public Integer next(Integer index) { return null; }
                public Integer nextDown(Integer index) { return null; }
                protected IntLine<Object> clone() { return null; }
            };
            /*Console.log("Line/ TreeLine/ IntLine");
            new IntLine<>();
            Console.log("DoubleLine");
            new DoubleLine<>(1);*/
        }

        Console.newLine();
        Console.log("Successfully testet.");
    }
}
