from PIL import Image, ImageDraw

def create_moon_phase(size, p, waxing=True):
    """
    Erzeugt ein Bild des Mondes in einer bestimmten Phase.
    
    Parameter:
      size: Bildgröße (Quadrat).
      p: Beleuchtungsanteil von 0 (Neumond) bis 1 (Vollmond).
      waxing: True für zunehmende Phasen (weißer Teil rechts), False für abnehmende (weiß links).
    """
    # Neues Bild mit transparentem Hintergrund
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    cx, cy = size // 2, size // 2
    R = size // 2 - 2  # kleiner Rand für den Umriss

    # Zeichne den dunklen Hintergrund des Mondes
    dark_color = (80, 80, 80, 255)
    ImageDraw.Draw(img).ellipse((cx - R, cy - R, cx + R, cy + R), fill=dark_color, outline="black")
    
    # Sonderfälle: Neumond (p=0) und Vollmond (p=1)
    if p <= 0:
        return img
    if p >= 1:
        ImageDraw.Draw(img).ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
        return img

    # Erstelle ein Bild mit dem weißen (beleuchteten) Mond
    lit = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    ImageDraw.Draw(lit).ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
    
    # Erstelle eine Maske, um den sichtbaren (weißen) Anteil zu bestimmen
    mask = Image.new("L", (size, size), 0)
    mask_draw = ImageDraw.Draw(mask)
    
    # Berechne den horizontalen Versatz d.
    # Bei zunehmenden Phasen (waxing=True) liegt der weiße Teil rechts,
    # bei abnehmenden (waxing=False) links.
    # Formel: d = (1 - 2*p)*R  bzw. -d für waning.
    d = (1 - 2 * p) * R if waxing else -(1 - 2 * p) * R

    mask_draw.ellipse((cx + d - R, cy - R, cx + d + R, cy + R), fill=255)
    
    # Füge den weißen Teil mithilfe der Maske in das Bild ein
    img.paste(lit, (0, 0), mask)
    return img

def upscale_image(img, factor):
    """Skaliert das Bild mit Nearest-Neighbor, um den Pixel-Art-Look zu erhalten."""
    return img.resize((img.width * factor, img.height * factor), resample=Image.NEAREST)

def main():
    size = 64    # Basisbildgröße in Pixeln
    factor = 4   # Upscale-Faktor für Pixel-Art-Optik

    # Definiere die 8 Mondphasen:
    # Phase-Index : (p, waxing)
    # 0: Neumond (🌑)          -> p = 0.0 (keine Beleuchtung)
    # 1: Zunehmende Sichel (🌒) -> p = 0.25, waxing=True
    # 2: Erstes Viertel (🌓)    -> p = 0.5, waxing=True
    # 3: Zunehmender Dreiviertelmond (🌔) -> p = 0.75, waxing=True
    # 4: Vollmond (🌕)         -> p = 1.0
    # 5: Abnehmender Dreiviertelmond (🌖) -> p = 0.75, waxing=False
    # 6: Letztes Viertel (🌗)   -> p = 0.5, waxing=False
    # 7: Abnehmende Sichel (🌘)  -> p = 0.25, waxing=False
    phases = [
        (0.0, True),
        (0.25, True),
        (0.5, True),
        (0.75, True),
        (1.0, True),
        (0.75, False),
        (0.5, False),
        (0.25, False)
    ]
    
    frames = []
    for p, waxing in phases:
        frame = create_moon_phase(size, p, waxing)
        frame = upscale_image(frame, factor)
        frames.append(frame)
    
    # Speichere die Animation als GIF, das endlos wiederholt wird
    frames[0].save(
        "moon_cycle.gif",
        save_all=True,
        append_images=frames[1:],
        duration=500,  # Dauer pro Frame (ms)
        loop=0,
        disposal=2
    )
    print("GIF wurde als 'moon_cycle.gif' gespeichert.")

if __name__ == "__main__":
    main()
