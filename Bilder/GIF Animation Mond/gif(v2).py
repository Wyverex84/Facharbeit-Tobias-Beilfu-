from PIL import Image, ImageDraw

def draw_phase(draw, size, phase_index):
    """
    Zeichnet eine der 8 festen Mondphasen im Pixel-Art-Stil auf das 'draw'-Objekt.
    phase_index: 0 bis 7, wobei:
      0 = Neumond,
      1 = Zunehmende Sichel,
      2 = Erstes Viertel,
      3 = Zunehmender Dreiviertelmond,
      4 = Vollmond,
      5 = Abnehmender Dreiviertelmond,
      6 = Letztes Viertel,
      7 = Abnehmende Sichel.
    """
    cx, cy = size // 2, size // 2
    R = size // 2 - 2  # kleiner Rand für den Umriss

    # Zuerst den Mond als dunkle Scheibe zeichnen
    dark_color = (80, 80, 80, 255)
    draw.ellipse((cx - R, cy - R, cx + R, cy + R),
                 fill=dark_color, outline="black")
    
    # Je nach Phase wird der "beleuchtete" Teil hinzugefügt
    if phase_index == 0:
        # 0) Neumond: gar keine weiße Fläche
        pass
    elif phase_index == 1:
        # 1) Zunehmende Sichel: schmale weiße Sichel rechts
        # Hier mit pieslice: Winkel anpassen, um einen schmalen Schein zu erzeugen
        draw.pieslice((cx - R, cy - R, cx + R, cy + R),
                      start=-50, end=50, fill="white", outline="black")
    elif phase_index == 2:
        # 2) Erstes Viertel: rechte Hälfte weiß
        draw.rectangle((cx, cy - R, cx + R, cy + R), fill="white", outline="black")
    elif phase_index == 3:
        # 3) Zunehmender Dreiviertelmond: überwiegend weiß, aber links wird ein dunkler Bogen überlagert
        draw.ellipse((cx - R, cy - R, cx + R, cy + R),
                     fill="white", outline="black")
        draw.pieslice((cx - R, cy - R, cx + R, cy + R),
                      start=100, end=260, fill=dark_color, outline=dark_color)
    elif phase_index == 4:
        # 4) Vollmond: kompletter weißer Kreis
        draw.ellipse((cx - R, cy - R, cx + R, cy + R),
                     fill="white", outline="black")
    elif phase_index == 5:
        # 5) Abnehmender Dreiviertelmond: überwiegend weiß, aber rechts wird ein dunkler Bogen überlagert
        draw.ellipse((cx - R, cy - R, cx + R, cy + R),
                     fill="white", outline="black")
        draw.pieslice((cx - R, cy - R, cx + R, cy + R),
                      start=-80, end=80, fill=dark_color, outline=dark_color)
    elif phase_index == 6:
        # 6) Letztes Viertel: linke Hälfte weiß
        draw.rectangle((cx - R, cy - R, cx, cy + R), fill="white", outline="black")
    elif phase_index == 7:
        # 7) Abnehmende Sichel: schmale weiße Sichel links
        draw.pieslice((cx - R, cy - R, cx + R, cy + R),
                      start=130, end=230, fill="white", outline="black")

def upscale_image(img, factor):
    """Skaliert das Bild mit Nearest-Neighbor, um den Pixel-Art-Look zu erhalten."""
    return img.resize((img.width * factor, img.height * factor), resample=Image.NEAREST)

def main():
    # Basisgröße und Upscale-Faktor definieren
    size = 32    # Basisbildgröße in Pixeln
    factor = 4   # Vergrößerungsfaktor (sorgt für den typischen Pixel-Art-Stil)
    
    frames = []
    # Erzeuge 8 Frames für die Mondphasen 0 bis 7
    for phase_index in range(8):
        img = Image.new("RGBA", (size, size), (0, 0, 0, 0))  # Transparenten Hintergrund
        draw = ImageDraw.Draw(img)
        draw_phase(draw, size, phase_index)
        frames.append(upscale_image(img, factor))
    
    # Speichere das animierte GIF, das endlos wiederholt
    frames[0].save(
        "moon_cycle_pixelart.gif",
        save_all=True,
        append_images=frames[1:],
        duration=500,  # Dauer pro Frame in Millisekunden
        loop=0,
        disposal=2
    )
    print("GIF wurde als 'moon_cycle_pixelart.gif' gespeichert.")

if __name__ == "__main__":
    main()
