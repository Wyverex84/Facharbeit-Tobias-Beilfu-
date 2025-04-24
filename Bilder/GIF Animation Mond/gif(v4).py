from PIL import Image, ImageDraw

def create_moon_phase(size, p, waxing=True):
    """
    Erzeugt ein Bild des Mondes in einer bestimmten Phase.
    
    Parameter:
      size: Bildgröße (Quadrat).
      p: Beleuchtungsanteil von 0 (Neumond) bis 1 (Vollmond).
      waxing: True = zunehmend (weißer Teil rechts), False = abnehmend (weißer Teil links).
    """
    # Neues Bild mit transparentem Hintergrund
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    
    cx, cy = size // 2, size // 2
    R = size // 2 - 2  # Mond-Radius mit kleinem Rand

    # Dunkler Mond-Hintergrund (grauer Kreis)
    dark_color = (80, 80, 80, 255)
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=dark_color, outline="black")
    
    # Sonderfälle: Neumond / Vollmond
    if p <= 0:
        # p=0 => kein beleuchteter Teil
        return img
    if p >= 1:
        # p=1 => komplett weiß
        draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
        return img
    
    # "Voll" beleuchteter Mond als eigenes Bild
    lit = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    ImageDraw.Draw(lit).ellipse((cx - R, cy - R, cx + R, cy + R),
                                fill="white", outline="black")
    
    # Maske definieren, um nur einen Teil des weißen Kreises zu zeigen
    mask = Image.new("L", (size, size), 0)
    mask_draw = ImageDraw.Draw(mask)

    # NEUE Formel für den Versatz d:
    #
    # Idee: Bei p=0.5 (Halbmond) soll der beleuchtete Kreis-Mittelpunkt exakt
    # auf der Mitte liegen (Überlappung = 50%).
    # Bei p=0.25 (Sichel) wird die Überlappung kleiner.
    #
    # offset d = -(0.5 - p) * R, wenn waxing=True
    # offset d =  (0.5 - p) * R, wenn waxing=False
    #
    # => Für p=0.25 (zunehmend): d = -(0.5 - 0.25)*R = -0.25*R (leichter Versatz nach links),
    #    so dass rechts nur ein Viertel beleuchtet wird.
    # => Für p=0.75 (zunehmend): d = -(0.5 - 0.75)*R = +0.25*R (leichter Versatz nach rechts)
    #    => 3/4 der Fläche werden überlappt.
    #
    # Du kannst den Faktor noch anpassen (z.B. * 2), wenn du eine extremere Sichel wünschst.
    
    if waxing:
        d = -(0.5 - p) * R
    else:
        d = (0.5 - p) * R

    # Maske = Ellipse, die durch Versatz definiert wird
    # Bounding-Box der "weißen" Ellipse
    x0 = cx + d - R
    x1 = cx + d + R
    y0 = cy - R
    y1 = cy + R

    mask_draw.ellipse((x0, y0, x1, y1), fill=255)
    
    # Weißen Teil in das Bild einfügen (mit Maske)
    img.paste(lit, (0, 0), mask)
    return img

def upscale_image(img, factor):
    """Skaliert das Bild mit Nearest-Neighbor, um den Pixel-Art-Look zu erhalten."""
    return img.resize((img.width * factor, img.height * factor), resample=Image.NEAREST)

def main():
    size = 64    # Basis-Bildgröße in Pixeln
    factor = 4   # Upscale-Faktor für Pixel-Art
    
    # Acht Mondphasen im Zyklus:
    # (p, waxing):
    #  0: 🌑 Neumond (p=0.0)
    #  1: 🌒 Zunehmende Sichel (p=0.25, True)
    #  2: 🌓 Erstes Viertel (p=0.5, True)
    #  3: 🌔 Zunehmender Dreiviertelmond (p=0.75, True)
    #  4: 🌕 Vollmond (p=1.0)
    #  5: 🌖 Abnehmender Dreiviertelmond (p=0.75, False)
    #  6: 🌗 Letztes Viertel (p=0.5, False)
    #  7: 🌘 Abnehmende Sichel (p=0.25, False)
    phases = [
        (0.0, True),    # Neumond
        (0.25, True),   # Zunehmende Sichel
        (0.5, True),    # Erstes Viertel
        (0.75, True),   # Zunehmender Dreiviertelmond
        (1.0, True),    # Vollmond
        (0.75, False),  # Abnehmender Dreiviertelmond
        (0.5, False),   # Letztes Viertel
        (0.25, False)   # Abnehmende Sichel
    ]
    
    frames = []
    for p, waxing in phases:
        moon_img = create_moon_phase(size, p, waxing)
        frames.append(upscale_image(moon_img, factor))
    
    # Animiertes GIF speichern
    frames[0].save(
        "moon_cycle.gif",
        save_all=True,
        append_images=frames[1:],
        duration=500,  # ms pro Frame
        loop=0,
        disposal=2
    )
    print("Die Mondphasen-Animation 'moon_cycle.gif' wurde erstellt.")

if __name__ == "__main__":
    main()
