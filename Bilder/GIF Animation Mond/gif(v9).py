import os
from PIL import Image, ImageDraw

def create_moon_phase(size, p, waxing=True, offset_factor=1.0):
    """
    Erzeugt ein Bild des Mondes in einer bestimmten Phase.
    
    Parameter:
      size: Bildgröße (Quadrat).
      p: Beleuchtungsanteil von 0 (Neumond) bis 1 (Vollmond).
      waxing: True für zunehmende Phasen (weiß rechts), False für abnehmende (weiß links).
      offset_factor: Multiplikator, um den Versatz (und damit die Sichelkrümmung) anzupassen.
                      Für Sichelphasen (p=0.25) empfiehlt sich ein höherer Wert (z.B. 1.8).
    """
    # Neues Bild mit transparentem Hintergrund
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    cx, cy = size // 2, size // 2
    R = size // 2 - 2  # kleiner Rand für den Umriss
    
    # Zeichne den Mond als dunkle Scheibe (grau) mit schwarzem Rand
    dark_color = (80, 80, 80, 255)
    draw = ImageDraw.Draw(img)
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=dark_color, outline="black")
    
    # Sonderfälle: Neumond und Vollmond
    if p <= 0:
        return img
    if p >= 1:
        draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
        return img

    # Erstelle ein weißes Layer (kompletter weißer Kreis)
    white_layer = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    white_draw = ImageDraw.Draw(white_layer)
    white_draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
    
    # Berechne den Versatz d.
    # Standardformel: base_d = (1 - 2*p) * R
    # Wir multiplizieren diesen Wert mit offset_factor.
    base_d = (1 - 2 * p) * R
    # Für zunehmende Phasen: verschieben wir den weißen Kreis nach rechts (positiver d)
    # Für abnehmende Phasen: spiegeln wir den Versatz.
    d = base_d * offset_factor if waxing else -base_d * offset_factor

    # Definiere die Bounding-Box für den verschobenen weißen Kreis
    x0 = cx + d - R
    y0 = cy - R
    x1 = cx + d + R
    y1 = cy + R
    
    # Erstelle die Maske: Zuerst einen schwarzen Hintergrund
    mask = Image.new("L", (size, size), 0)
    mask_draw = ImageDraw.Draw(mask)
    # Zeichne einen weißen Kreis, der verschoben ist
    mask_draw.ellipse((x0, y0, x1, y1), fill=255)
    
    # Nun: Der weiße Teil wird nur dort übernommen, wo der verschobene Kreis (Maske)
    # mit dem ursprünglichen Mond-Kreis (implizit im white_layer) übereinstimmt.
    img.paste(white_layer, (0, 0), mask)
    return img

def upscale_image(img, factor):
    """Skaliert das Bild per Nearest-Neighbor, um den Pixel-Art-Look zu erhalten."""
    return img.resize((img.width * factor, img.height * factor), resample=Image.NEAREST)

def save_phases_as_png(size=64, factor=4, output_folder="moon_phases"):
    """
    Erzeugt für jede Mondphase ein PNG, speichert es im Ordner 'output_folder'
    und liefert eine Liste der (upgegelten) Bilder zurück.
    """
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    # Definition der 8 Mondphasen: (p, waxing, offset_factor, Name)
    phases = [
        (0.0, True, 1.0, "Neumond"),                  # 🌑
        (0.25, True, 1.8, "Zunehmende_Sichel"),        # 🌒
        (0.5, True, 1.0, "Erstes_Viertel"),            # 🌓
        (0.75, True, 1.4, "Zunehmender_Dreiviertel"),   # 🌔
        (1.0, True, 1.0, "Vollmond"),                  # 🌕
        (0.75, False, 1.4, "Abnehmender_Dreiviertel"),  # 🌖
        (0.5, False, 1.0, "Letztes_Viertel"),          # 🌗
        (0.25, False, 1.8, "Abnehmende_Sichel")        # 🌘
    ]
    
    images = []
    for p, waxing, off_factor, name in phases:
        img = create_moon_phase(size, p, waxing, offset_factor=off_factor)
        filename = os.path.join(output_folder, f"moon_{name}.png")
        img.save(filename)
        print(f"{filename} gespeichert.")
        images.append(upscale_image(img, factor))
    
    return images

def create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=500):
    """
    Erstellt ein animiertes GIF aus einer Liste von Bildern.
    """
    if images:
        images[0].save(
            gif_filename,
            save_all=True,
            append_images=images[1:],
            duration=duration,
            loop=0,
            disposal=2
        )
        print(f"Das GIF '{gif_filename}' wurde erstellt.")

def main():
    # Parameter
    size = 64           # Basisbildgröße in Pixel
    factor = 4          # Upscale-Faktor für den Pixel-Art-Look
    duration = 500      # Dauer pro Frame in Millisekunden
    png_folder = "moon_phases"

    # Erstelle und speichere die PNGs für jede Mondphase
    images = save_phases_as_png(size=size, factor=factor, output_folder=png_folder)
    
    # Erstelle aus den PNGs ein animiertes GIF
    create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=duration)

if __name__ == "__main__":
    main()
