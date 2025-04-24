import os
from PIL import Image, ImageDraw, ImageChops

def get_circle_mask(size, center, R):
    """
    Erzeugt eine kreisförmige Maske (Modus "L") in der Größe size x size,
    weiß (255) innerhalb eines Kreises mit Radius R um center, sonst schwarz.
    """
    mask = Image.new("L", (size, size), 0)
    draw = ImageDraw.Draw(mask)
    cx, cy = center
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=255)
    return mask

def get_rect_mask(size, rect):
    """
    Erzeugt eine rechteckige Maske (Modus "L") in der Größe size x size.
    rect ist die Bounding-Box (x0, y0, x1, y1) des Rechtecks, das weiß (255) wird.
    """
    mask = Image.new("L", (size, size), 0)
    draw = ImageDraw.Draw(mask)
    draw.rectangle(rect, fill=255)
    return mask

def get_phase_mask_intermediate(phase, size):
    """
    Erzeugt eine Maske (Modus "L") für eine der 16 fest definierten Mondphasen.
    Die Maske ist immer die Schnittmenge (logisches UND) zwischen dem Basis-Mondkreis
    (Zentrum (cx,cy) mit Radius R) und einem zusätzlich festgelegten Maskenausschnitt.
    
    Die 16 Phasen sind folgendermaßen definiert:
      0  = Neumond (keine Beleuchtung)
      1  = Frühe Waxing Crescent: Kreis mit Zentrum (cx + 0.95·R, cy), Radius 0.75·R
      2  = Waxing Crescent: Kreis mit Zentrum (cx + 1.00·R, cy), Radius 0.80·R
      3  = Späte Waxing Crescent: Kreis mit Zentrum (cx + 1.05·R, cy), Radius 0.85·R
      4  = Erstes Viertel: Basis ∩ (alle Punkte mit x ≥ cx) (rechte Hälfte)
      5  = Frühe Waxing Gibbous: Kreis mit Zentrum (cx + 0.25·R, cy), Radius 1.00·R
      6  = Waxing Gibbous: Kreis mit Zentrum (cx + 0.15·R, cy), Radius 1.00·R
      7  = Späte Waxing Gibbous: Kreis mit Zentrum (cx + 0.05·R, cy), Radius 1.00·R
      8  = Vollmond: kompletter Basis-Mondkreis
      9  = Frühe Waning Gibbous: Kreis mit Zentrum (cx - 0.05·R, cy), Radius 1.00·R
      10 = Waning Gibbous: Kreis mit Zentrum (cx - 0.15·R, cy), Radius 1.00·R
      11 = Späte Waning Gibbous: Kreis mit Zentrum (cx - 0.25·R, cy), Radius 1.00·R
      12 = Letztes Viertel: Basis ∩ (alle Punkte mit x ≤ cx) (linke Hälfte)
      13 = Frühe Waning Crescent: Kreis mit Zentrum (cx - 1.05·R, cy), Radius 0.85·R
      14 = Waning Crescent: Kreis mit Zentrum (cx - 1.00·R, cy), Radius 0.80·R
      15 = Späte Waning Crescent: Kreis mit Zentrum (cx - 0.95·R, cy), Radius 0.75·R
    """
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    base_mask = get_circle_mask(size, (cx, cy), R)
    
    # Definiere feste Parameter für die 16 Phasen:
    phases_def = {
       0: ("none", None, None),
       1: ("circle", 0.95, 0.75),
       2: ("circle", 1.00, 0.80),
       3: ("circle", 1.05, 0.85),
       4: ("rect", "right", None),
       5: ("circle", 0.25, 1.00),
       6: ("circle", 0.15, 1.00),
       7: ("circle", 0.05, 1.00),
       8: ("full", None, None),
       9: ("circle", -0.05, 1.00),
       10: ("circle", -0.15, 1.00),
       11: ("circle", -0.25, 1.00),
       12: ("rect", "left", None),
       13: ("circle", -1.05, 0.85),
       14: ("circle", -1.00, 0.80),
       15: ("circle", -0.95, 0.75)
    }
    method, offset, radius_factor = phases_def.get(phase, ("none", None, None))
    
    if method == "none":
         return Image.new("L", (size, size), 0)
    elif method == "full":
         return base_mask
    elif method == "circle":
         center_extra = (int(cx + offset * R), cy)
         extra = get_circle_mask(size, center_extra, int(radius_factor * R))
         return ImageChops.multiply(base_mask, extra)
    elif method == "rect":
         if offset == "right":
              rect = (cx, cy - R, cx + R, cy + R)
         else:  # "left"
              rect = (cx - R, cy - R, cx, cy + R)
         rect_mask = get_rect_mask(size, rect)
         return ImageChops.multiply(base_mask, rect_mask)

def draw_phase_image(size, phase):
    """
    Erzeugt das Mondphasen-Bild (RGBA) in der Größe size x size.
    Der Basis-Mondkreis (grauer Kreis mit schwarzem Rand) wird gezeichnet,
    und anschließend wird der weiße, beleuchtete Bereich mithilfe der
    fest definierten Maske für die jeweilige Phase eingefügt.
    """
    img = Image.new("RGBA", (size, size), (0,0,0,0))
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    dark_color = (80,80,80,255)
    draw = ImageDraw.Draw(img)
    draw.ellipse((cx-R, cy-R, cx+R, cy+R), fill=dark_color, outline="black")
    
    if phase == 0:
         return img  # Neumond: kein weißer Anteil
    
    white_layer = Image.new("RGBA", (size, size), "white")
    mask = get_phase_mask_intermediate(phase, size)
    img.paste(white_layer, (0, 0), mask)
    return img

def upscale_image(img, factor):
    """Skaliert das Bild per Nearest-Neighbor, um den Pixel-Art-Look zu erhalten."""
    return img.resize((img.width*factor, img.height*factor), resample=Image.NEAREST)

def save_phases_as_png(size=64, factor=4, output_folder="moon_phases"):
    """
    Erzeugt für jede Mondphase (Phase 0 bis 15) ein PNG und speichert es im Ordner output_folder.
    Liefert eine Liste der (upgegelten) Bilder zurück.
    """
    if not os.path.exists(output_folder):
         os.makedirs(output_folder)
    
    images = []
    for phase in range(16):
         img = draw_phase_image(size, phase)
         filename = os.path.join(output_folder, f"moon_phase_{phase:02d}.png")
         img.save(filename)
         print(f"{filename} gespeichert.")
         images.append(upscale_image(img, factor))
    return images

def create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=50):
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
    size = 64       # Basisbildgröße in Pixeln
    factor = 4      # Upscale-Faktor für den Pixel-Art-Look
    duration = 50   # Kürzere Dauer pro Frame für eine flüssigere Animation
    folder = "moon_phases"
    
    images = save_phases_as_png(size=size, factor=factor, output_folder=folder)
    create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=duration)

if __name__ == "__main__":
    main()
