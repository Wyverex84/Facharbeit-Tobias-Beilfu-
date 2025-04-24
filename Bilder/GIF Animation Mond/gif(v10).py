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

def get_phase_mask(phase, size):
    """
    Erzeugt eine Maske (Modus "L") für die jeweilige Mondphase.
    Die Maske wird immer als Schnittmenge (logisches UND) zwischen dem 
    Basis-Mondkreis (Zentrum (cx,cy) und Radius R) und einem zusätzlichen
    Maskenausschnitt definiert, der die beleuchteten Bereiche darstellt.
    
    Die Parameter der Phasen sind fest vorgegeben:
      0 = Neumond: keine Beleuchtung
      1 = Zunehmende Sichel: Schnittmenge von Basiskreis und Kreis mit Zentrum (cx+0.9·R,cy)
      2 = Erstes Viertel: Basiskreis ∩ (alle Punkte mit x ≥ cx)
      3 = Zunehmender Dreiviertelmond: Basiskreis ∩ Kreis mit Zentrum (cx+0.3·R,cy)
      4 = Vollmond: ganzer Basiskreis
      5 = Abnehmender Dreiviertelmond: Basiskreis ∩ Kreis mit Zentrum (cx-0.3·R,cy)
      6 = Letztes Viertel: Basiskreis ∩ (alle Punkte mit x ≤ cx)
      7 = Abnehmende Sichel: Basiskreis ∩ Kreis mit Zentrum (cx-0.9·R,cy)
    """
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    
    # Basis-Mondkreis-Maske
    base_mask = get_circle_mask(size, (cx, cy), R)
    
    if phase == 0:
        # Neumond: leere Maske
        return Image.new("L", (size, size), 0)
    
    elif phase == 1:
        # Zunehmende Sichel: kleiner Schnitt, Kreis mit Zentrum fast ganz rechts
        extra = get_circle_mask(size, (int(cx + 0.9 * R), cy), R)
        mask = ImageChops.multiply(base_mask, extra)
        return mask
    
    elif phase == 2:
        # Erstes Viertel: rechte Hälfte
        rect = (cx, cy - R, cx + R, cy + R)
        rect_mask = get_rect_mask(size, rect)
        mask = ImageChops.multiply(base_mask, rect_mask)
        return mask
    
    elif phase == 3:
        # Zunehmender Dreiviertelmond: fast voll, aber ein kleiner dunkler Bereich links.
        extra = get_circle_mask(size, (int(cx + 0.3 * R), cy), R)
        mask = ImageChops.multiply(base_mask, extra)
        return mask
    
    elif phase == 4:
        # Vollmond: kompletter Mondkreis
        return base_mask
    
    elif phase == 5:
        # Abnehmender Dreiviertelmond: fast voll, aber ein kleiner dunkler Bereich rechts.
        extra = get_circle_mask(size, (int(cx - 0.3 * R), cy), R)
        mask = ImageChops.multiply(base_mask, extra)
        return mask
    
    elif phase == 6:
        # Letztes Viertel: linke Hälfte
        rect = (cx - R, cy - R, cx, cy + R)
        rect_mask = get_rect_mask(size, rect)
        mask = ImageChops.multiply(base_mask, rect_mask)
        return mask
    
    elif phase == 7:
        # Abnehmende Sichel: kleiner Schnitt, Kreis mit Zentrum fast ganz links
        extra = get_circle_mask(size, (int(cx - 0.9 * R), cy), R)
        mask = ImageChops.multiply(base_mask, extra)
        return mask

def draw_phase_image(size, phase):
    """
    Erzeugt das Mondphasen-Bild (RGBA) in der Größe size x size.
    Der dunkle Mond (grauer Kreis mit schwarzem Rand) wird als Basis gezeichnet,
    und anschließend wird mit der jeweiligen Phase-Maske der weiße (beleuchtete) Teil
    hinzugefügt.
    """
    # Erzeuge Basisbild mit transparentem Hintergrund
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    dark_color = (80, 80, 80, 255)
    draw = ImageDraw.Draw(img)
    # Basis-Mondkreis
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=dark_color, outline="black")
    
    if phase == 0:
        # Neumond: nichts weiter
        return img
    
    # Erzeuge den weißen Layer
    white_layer = Image.new("RGBA", (size, size), "white")
    # Hole die Maske für die jeweilige Phase
    mask = get_phase_mask(phase, size)
    # Füge den weißen Bereich nur dort ein, wo die Maske 255 hat
    img.paste(white_layer, (0, 0), mask)
    return img

def upscale_image(img, factor):
    """Skaliert das Bild per Nearest-Neighbor, um den Pixel-Art-Look zu erhalten."""
    return img.resize((img.width * factor, img.height * factor), resample=Image.NEAREST)

def save_phases_as_png(size=64, factor=4, output_folder="moon_phases"):
    """
    Erzeugt für jede Mondphase (0 bis 7) ein PNG und speichert diese im Ordner output_folder.
    Liefert eine Liste der (upgegelten) Bilder zurück.
    """
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    phases = [
        (0, "Neumond"),
        (1, "Zunehmende_Sichel"),
        (2, "Erstes_Viertel"),
        (3, "Zunehmender_Dreiviertel"),
        (4, "Vollmond"),
        (5, "Abnehmender_Dreiviertel"),
        (6, "Letztes_Viertel"),
        (7, "Abnehmende_Sichel")
    ]
    
    images = []
    for phase, name in phases:
        img = draw_phase_image(size, phase)
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
    size = 64      # Basisbildgröße in Pixel
    factor = 4     # Upscale-Faktor für den Pixel-Art-Look
    duration = 500 # Dauer pro Frame in Millisekunden
    folder = "moon_phases"
    
    images = save_phases_as_png(size=size, factor=factor, output_folder=folder)
    create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=duration)

if __name__ == "__main__":
    main()
