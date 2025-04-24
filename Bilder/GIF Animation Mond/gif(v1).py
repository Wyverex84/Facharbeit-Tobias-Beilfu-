from PIL import Image, ImageDraw

def create_moon_frame(size, p, waxing=True):
    """
    Erzeugt ein Bild mit dem Mond in einer bestimmten Phase.
    
    Parameter:
      size: Basisgröße des Bildes (Quadrat).
      p: Beleuchtungsanteil von 0 (neu) bis 1 (voll).
      waxing: Boolean, ob es sich um eine zunehmende (True) oder abnehmende (False) Phase handelt.
    """
    # Bild mit transparentem Hintergrund
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    
    # Mittelpunkt und Radius (mit Rand für den schwarzen Umriss)
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    
    # Zeichne den vollen Mond als dunkle Scheibe (unbeleuchtet)
    dark_color = (80, 80, 80, 255)  # dunkles Grau
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=dark_color, outline="black")
    
    # Sonderfälle: Neuer Mond (keine Beleuchtung) bzw. Vollmond (voll beleuchtet)
    if p <= 0:
        return img
    if p >= 1:
        draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
        return img

    # Erstelle ein Bild für die beleuchtete Scheibe (weiß)
    lit = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    lit_draw = ImageDraw.Draw(lit)
    lit_draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
    
    # Erstelle eine Maske, die den beleuchteten Anteil definiert.
    # Die Maske basiert auf dem Prinzip, dass sich zwei Kreise überlappen.
    mask = Image.new("L", (size, size), 0)
    mask_draw = ImageDraw.Draw(mask)
    
    # Berechne den horizontalen Versatz d.
    # Für zunehmende Phasen: d = (1 - 2*p)*R, für abnehmende: d = -(1 - 2*p)*R.
    if waxing:
        d = (1 - 2 * p) * R
    else:
        d = -(1 - 2 * p) * R

    # Zeichne einen zweiten Kreis (in der Maske) mit dem Versatz.
    mask_draw.ellipse((cx + d - R, cy - R, cx + d + R, cy + R), fill=255)
    
    # Kopiere den beleuchteten Teil in das ursprüngliche Bild mithilfe der Maske.
    img.paste(lit, (0, 0), mask)
    return img

def upscale_image(img, factor):
    """Skaliert das Bild mit nearest-neighbor für den Pixel-Art-Look."""
    return img.resize((img.width * factor, img.height * factor), resample=Image.NEAREST)

def main():
    # Basisgröße und Upscale-Faktor festlegen
    size = 64       # Basisbildgröße
    factor = 4      # Vergrößerungsfaktor (Pixel Art-Look)
    
    # Definiere die Mondphasen: (p, waxing)
    # p = 0   → neuer Mond (🌑)
    # p = 0.25→ zunehmende Sichel (🌒)
    # p = 0.5 → erstes Viertel (🌓)
    # p = 0.75→ zunehmender Dreiviertelmond (🌔)
    # p = 1   → Vollmond (🌕)
    # Danach abnehmend:
    # p = 0.75→ abnehmender Dreiviertelmond (🌖)
    # p = 0.5 → letztes Viertel (🌗)
    # p = 0.25→ abnehmende Sichel (🌘)
    phases = [
        (0.0, True),   # 🌑
        (0.25, True),  # 🌒
        (0.5, True),   # 🌓
        (0.75, True),  # 🌔
        (1.0, True),   # 🌕
        (0.75, False), # 🌖
        (0.5, False),  # 🌗
        (0.25, False)  # 🌘
    ]
    
    frames = []
    for p, waxing in phases:
        frame = create_moon_frame(size, p, waxing)
        frame = upscale_image(frame, factor)
        frames.append(frame)
    
    # Speichere das GIF (endlos wiederholend)
    frames[0].save(
        "moon_cycle.gif",
        save_all=True,
        append_images=frames[1:],
        duration=500,   # Dauer pro Frame in Millisekunden
        loop=0,
        disposal=2
    )

if __name__ == "__main__":
    main()
    print("GIF erstellt: moon_cycle.gif")