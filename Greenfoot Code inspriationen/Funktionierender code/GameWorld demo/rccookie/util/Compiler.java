package rccookie.util;

import java.util.ArrayList;

import greenfoot.*;
import rccookie.data.*;
import rccookie.data.json.*;
import rccookie.event.*;
import rccookie.game.*;
import rccookie.game.physics.*;
import rccookie.game.util.*;
import rccookie.geometry.*;
import rccookie.learning.*;
//import rccookie.rendering.*;
import rccookie.ui.advanced.*;
import rccookie.ui.basic.*;

public class Compiler {
    public static void testStuff(){
        System.out.println("Starting test...\n");
        
        System.out.println("data"); {
            System.out.println("RuntimeLoadable");
            new RuntimeLoadable(){
                public Object getSaveData() { return null; }
                public String getSaveDir() { return null; }
                public String getSaveName() { return null; }
                public void load(Object data) { }
            };
            
            System.out.println("json"); {
                System.out.println("Json");
                Json.validateName("name");
                System.out.println("JsonLoadable");
                new JsonLoadable(){
                    public Object getSaveData() { return null; }
                    public String getSaveDir() { return null; }
                    public String getSaveName() { return null; }
                    public void load(JsonObject data) { }
                };
                System.out.println("JsonObject");
                new JsonObject();
            }
        }

        System.out.println("event"); {
            System.out.println("KeyListener");
            new KeyListener("a");
            System.out.println("Threads");
            Threads.runParralel(info -> { });
        }
        
        System.out.println("game"); {
            System.out.println("Advanced Actor");
            new AdvancedActor() { };

            System.out.println("physics"); {
                System.out.println("CircleCollider");
                new CircleCollider(1, new Vector2D());
                System.out.println("Collider");
                new BoxCollider(new Actor() { });
                System.out.println("Cylinder");
                new Cylinder(1, 1);
                System.out.println("PhysicalActor");
                new PhysicalActor() { };
            }

            System.out.println("util"); {
                System.out.println("Time");
                new Time();
                System.out.println("ActorTable");
                new ActorTable<Actor>(1, 1);
                System.out.println("RaycastCondition");
                new RaycastCondition(){
                    public boolean collidingAt(int x, int y, World world, int length) { return false; }
                };
                System.out.println("RaycastResult");
                new RaycastResult(0, 0, 0, 0, null, true);
                System.out.println("Raycast");
                new Raycast() { };
            }
        }
        
        System.out.println("learning"); {
            System.out.println("OnOffFunction");
            new OnOffFunction();
            System.out.println("SigmoidFunction");
            new SigmoidFunction();
            System.out.println("Neuron");
            new Neuron();
            System.out.println("Edge");
            new Edge(null);
            System.out.println("KeyDownNeuron");
            new KeyDownNeuron("space");
            System.out.println("StaticNeuron");
            new StaticNeuron();
            System.out.println("StaticEdge");
            new StaticEdge(null);
            System.out.println("Network");
            new Network(new ArrayList<>(), 1);
            System.out.println("Generation");
            new Generation(new ArrayList<>()){};
        }

        System.out.println("geometry"); {
            System.out.println("Vector");
            new Vector();
            System.out.println("Vector2D");
            new Vector2D();
            System.out.println("Transform");
            new Transform();
            System.out.println("Transform2D");
            new Transform2D();
            System.out.println("Rotation");
            new Rotation();
            System.out.println("Geomentry");
            Geometry.floor(1, 1);
            System.out.println("Line");
            new Line(new Vector(1));
            System.out.println("Physics");
            Physics.Mechanics.distTraveled(1, 1);
            Physics.Relativity.kineticEnergy(1, 1);
            Physics.Gravitation.temperatur(1);
        }

        // Rendering

        System.out.println("ui"); {
            System.out.println("basic"); {
                System.out.println("Text");
                new Text();
                System.out.println("Button");
                new Button("Hello World!");
                System.out.println("Fade");
                Fade.fadeIn(Color.BLACK, 1);
                System.out.println("ObjectAdder");
                new ObjectAdder(new Actor() { });
                System.out.println("Slider");
                new Slider();
                System.out.println("UIPanel");
                new UIPanel(new World(1, 1, 1) { });
                System.out.println("UIWorld");
                new UIWorld(100, 100, 1);
            }

            System.out.println("advanced"); {
                System.out.println("Container");
                new Container(10, 10, 1);
                System.out.println("Camera");
                System.out.println(">>" + Camera.BACKGROUND);
                System.out.println("DropDownMenu");
                String[] temp = {"World", "!"};
                new DropDownMenu("Hello", temp);
                System.out.println("FpsDisplay");
                new FpsDisplay();
                System.out.println("ExponentialSlider");
                new ExponentialSlider(0, 1, 100, Color.BLACK, Color.BLACK);
                System.out.println("TextField");
                System.out.println(">>" + TextField.BORDER);
                //new TextField(new Text("Hello"), 100, 50);
                System.out.println("NumberField");
                System.out.println(">>" + NumberField.DEFAULT_RETURN);
                //new NumberField();
                System.out.println("Scoreboard");
                new Scoreboard(100, 100);
                System.out.println("Switch");
                new Switch();
            }

            System.out.println("util"); {
                System.out.println("TagTable/ ClassTag");
                ClassTag.tag(Compiler.class, "util");
                System.out.println("Lists");
                Lists.any(new ArrayList<>(), info -> true);
                System.out.println("Grid/ AbstractTable/ Table/ ActorTable");
                new ActorTable<>(1, 1);
                System.out.println("InfinitiveGrid/ Plane/ Map");
                new Map<>();
            }
        }
        
        System.out.println("\nSuccessfully testet.");
    }
}
