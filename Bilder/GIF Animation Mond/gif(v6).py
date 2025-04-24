import os
from PIL import Image, ImageDraw

def create_moon_phase(size, p, waxing=True, offset_factor=1.4):
    """
    Erzeugt ein Bild des Mondes in einer bestimmten Phase.
    
    Parameter:
      size: Bildgröße (Quadrat).
      p: Beleuchtungsanteil von 0 (Neumond) bis 1 (Vollmond).
      waxing: True = zunehmend (weißer Teil rechts), False = abnehmend (weißer Teil links).
      offset_factor: Faktor, um den Offset zu verstärken (für ausgeprägtere Sichelphasen).
    """
    # Neues Bild mit transparentem Hintergrund
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    
    cx, cy = size // 2, size // 2
    R = size // 2 - 2  # kleiner Rand für den Umriss

    # Dunkler Mond-Hintergrund (grauer Kreis)
    dark_color = (80, 80, 80, 255)
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=dark_color, outline="black")
    
    # Sonderfälle: Neumond und Vollmond
    if p <= 0:
        return img
    if p >= 1:
        draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
        return img

    # "Voll" beleuchteter Mond als separates Bild
    lit = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    ImageDraw.Draw(lit).ellipse((cx - R, cy - R, cx + R, cy + R),
                                fill="white", outline="black")
    
    # Maske definieren, um nur einen Teil des weißen Kreises zu zeigen
    mask = Image.new("L", (size, size), 0)
    mask_draw = ImageDraw.Draw(mask)

    # Berechnung des Versatzes: 
    # Standard: base_d = (1 - 2*p) * R, multipliziert mit offset_factor.
    base_d = (1 - 2 * p) * R
    d = base_d * offset_factor
    # Bei abnehmenden Phasen wird der Versatz gespiegelt.
    if not waxing:
        d = -d

    # Bounding-Box für den verschobenen weißen Kreis
    x0 = cx + d - R
    x1 = cx + d + R
    y0 = cy - R
    y1 = cy + R

    mask_draw.ellipse((x0, y0, x1, y1), fill=255)
    img.paste(lit, (0, 0), mask)
    return img

def upscale_image(img, factor):
    """Skaliert das Bild mit Nearest-Neighbor, um den Pixel-Art-Look zu erhalten."""
    return img.resize((img.width * factor, img.height * factor), resample=Image.NEAREST)

def save_phases_as_png(size=64, offset_factor=1.4, factor=4, output_folder="moon_phases"):
    """
    Erzeugt für jede Mondphase ein PNG und speichert diese im Ordner 'output_folder'.
    Außerdem wird eine Liste der (upgegelten) Bilder zurückgegeben.
    """
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    # Definition der 8 Mondphasen:
    # (p, waxing, Dateiname-Zusatz)
    phases = [
        (0.0, True, "Neumond"),                 # 🌑
        (0.25, True, "Zunehmende_Sichel"),       # 🌒
        (0.5, True, "Erstes_Viertel"),           # 🌓
        (0.75, True, "Zunehmender_Dreiviertel"),  # 🌔
        (1.0, True, "Vollmond"),                 # 🌕
        (0.75, False, "Abnehmender_Dreiviertel"), # 🌖
        (0.5, False, "Letztes_Viertel"),         # 🌗
        (0.25, False, "Abnehmende_Sichel")       # 🌘
    ]
    
    images = []
    for p, waxing, name in phases:
        img = create_moon_phase(size, p, waxing, offset_factor)
        # Optional: direkt im Original speichern
        filename = os.path.join(output_folder, f"moon_{name}.png")
        img.save(filename)
        print(f"{filename} gespeichert.")
        
        # Upscaling für Pixel-Art-Look
        img_upscaled = upscale_image(img, factor)
        images.append(img_upscaled)
    
    return images

def create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=500):
    """
    Erzeugt ein animiertes GIF aus einer Liste von Bildern.
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
    size = 64           # Basisbildgröße (in Pixel)
    offset_factor = 1.4 # Offset-Faktor (anpassbar, um Sichelphasen zu verstärken)
    factor = 4          # Upscale-Faktor für Pixel-Art
    duration = 500      # Dauer pro Frame in Millisekunden
    png_folder = "moon_phases"

    # Erstelle und speichere die PNGs für jede Mondphase
    images = save_phases_as_png(size, offset_factor, factor, output_folder=png_folder)
    
    # Erstelle aus den PNG-Bildern ein animiertes GIF
    create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=duration)

if __name__ == "__main__":
    main()
