------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE: 
    Sonden Simmulation

PURPOSE OF PROJECT: 
    Facharbeit von Tobias Beilfuß.

VERSION or DATE: 
    0.1.3

HOW TO START THIS PROJECT: 
    Den Run Button Drücken

AUTHORS: 
    Tobias Beilfuß

Beinhaltet Code Von/importierte Classen: 
    GifImage(von Michael Berry, Neil Brown), 
    GamePad/Direction(),

USER INSTRUCTIONS:

Änderungen:
    18.04.2025:
    von Import Class GifImage importiert damit ich gifs abspielen kann.
    Welten Endscreen, Game und Startscreen Erstellt.
    
    20.04.2025:
    in der Welt unterklasse Klasse Game() die Methode prepare() Erstellt womit das Objekt
    Sonde() Hinzugefügt Wird.
    
    Der in der Actor klasse wurde die unterklasse Sonde() erstellt mit dem methoden 
    handleInput() womit man die Sonde Mit [w,a,s,d,left,right,up,down] steuern kann, mit der Methode applyGravity()
    wird die Schwerkraft hinzugefügt damit die Sonde richtung Boden Fällt 
    und in der Methode moveSonde() werden dann die Kordinaten bestimmt und verändert
    wenn in der Methode das objekt eine der welt wände berührt dann kann man nicht mehr steuern.
    
    22.04.2025:
    Die Klasse Commen wurde erstellt darin die Mehtode Hintergrund() um der welten ein hintergrund hinzufügen.
    Dokumentations Tablle hinzugefügt zu den Klassen und Objekten.
    
    