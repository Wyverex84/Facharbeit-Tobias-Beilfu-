package com.github.rccookie.common.util;

import java.util.ArrayList;

import com.github.rccookie.common.app.*;
import com.github.rccookie.common.data.*;
import com.github.rccookie.common.data.json.*;
import com.github.rccookie.common.event.*;
import com.github.rccookie.common.geometry.*;
import com.github.rccookie.common.learning.*;

public final class PlainCompiler {
    private PlainCompiler() { }

    public static void testStuff(){

        Console.log("Starting test...\n");

        Console.log("com.github.rccookie.common.util.Console");
        Console.log();

        Console.log("app"); {
            Console.log("Item");
            new Item() { };
            Console.log("World");
            new com.github.rccookie.common.app.World(1, 1);
            Console.log("Application");
            new Application("Hello").dispose();
        }

        Console.log("data"); {
            Console.log("Saveable");
            new Saveable(){
				private static final long serialVersionUID = 1L;
                public String getSaveName() { return ""; }
                public void setSaveName(String n) { }
            };
            Console.log("SerialTools");
            @SuppressWarnings("unused")
            Object o = SerialTools.class;
            Console.log("json"); {
                Console.log("Json");
                Json.validateName("name");
            }
        }

        Console.log("event"); {
            Console.log("Threads");
            Threads.runParralel(info -> { });
        }

        Console.log("learning"); {
            Console.log("OnOffFunction");
            new OnOffFunction();
            Console.log("SigmoidFunction");
            new SigmoidFunction();
            Console.log("Neuron");
            new Neuron();
            Console.log("Edge");
            new com.github.rccookie.common.learning.Edge(null);
            // KeyDownNeuron relies on KeyListener, which is Greenfoot-dependents
            Console.log("StaticNeuron");
            new StaticNeuron();
            Console.log("StaticEdge");
            new StaticEdge(null);
            Console.log("Network");
            new Network(new ArrayList<>(), 1);
            Console.log("Generation");
            new Generation(new ArrayList<>()){};
        }

        Console.log("geometry"); {
            Console.log("Vector3D");
            new Vector3D();
            Console.log("Vector2D");
            new Vector2D();
            Console.log("Ray2D");
            new Ray2D(new Vector2D(1));
            Console.log("Ray3D");
            new Ray3D(new Vector3D(1));
            Console.log("Edge2D");
            new Edge2D(new Vector2D(), new Vector2D(1));
            Console.log("Edge3D");
            new Edge3D(new Vector3D(), new Vector3D(1));
            Console.log("Transform");
            new Transform3D();
            Console.log("Transform2D");
            new Transform2D();
            Console.log("Rotation");
            new Rotation();
            Console.log("Geomentry");
            Geometry.floor(1, 1);
            Console.log("Line");
            new com.github.rccookie.common.geometry.Ray3D(new Vector3D(1));
            Console.log("Physics");
            Physics.Mechanics.distTraveled(1, 1);
            Physics.Relativity.kineticEnergy(1, 1);
            Physics.Gravitation.temperatur(1);
        }

        // Console.log("rendering")

        Console.log("util"); {
            Console.log("TagTable/ ClassTag");
            Console.log("Lists");
            Lists.any(new ArrayList<>(), info -> true);
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


    public static void main(String[] args) {
        testStuff();
    }
}
