import os
from PIL import Image, ImageDraw, ImageChops

def get_phase_mask(phase_index, size):
    """
    Erzeugt für den gegebenen phase_index (0 bis 7) eine Maske (im Modus "L"),
    die den Bereich bestimmt, der weiß (beleuchtet) sein soll – und zwar
    ausschließlich innerhalb des kreisförmigen Mondes.
    
    Die Phasen bedeuten:
      0 = Neumond (keine Beleuchtung)
      1 = Zunehmende Sichel (schmaler weißer Bereich rechts)
      2 = Erstes Viertel (rechte Hälfte weiß)
      3 = Zunehmender Dreiviertelmond (ca. 75 % rechts weiß)
      4 = Vollmond (komplett weiß)
      5 = Abnehmender Dreiviertelmond (ca. 75 % links weiß)
      6 = Letztes Viertel (linke Hälfte weiß)
      7 = Abnehmende Sichel (schmaler weißer Bereich links)
    """
    cx, cy = size // 2, size // 2
    R = size // 2 - 2

    # Kreis-Maske: definiert den Mond (Kreis)
    circle_mask = Image.new("L", (size, size), 0)
    d_circle = ImageDraw.Draw(circle_mask)
    d_circle.ellipse((cx - R, cy - R, cx + R, cy + R), fill=255)

    # Rechteck-Maske: definiert den gewünschten weißen Bereich (unbegrenzt)
    rect_mask = Image.new("L", (size, size), 0)
    d_rect = ImageDraw.Draw(rect_mask)
    
    if phase_index == 0:
        # Neumond: kein weißer Bereich
        pass
    elif phase_index == 1:
        # Zunehmende Sichel (🌒): schmaler Streifen rechts
        # Wir nehmen hier ca. 1/3 der Breite des Kreises
        x0 = int(cx + R - R / 3)
        x1 = cx + R
        d_rect.rectangle((x0, cy - R, x1, cy + R), fill=255)
    elif phase_index == 2:
        # Erstes Viertel (🌓): rechte Hälfte
        d_rect.rectangle((cx, cy - R, cx + R, cy + R), fill=255)
    elif phase_index == 3:
        # Zunehmender Dreiviertelmond (🌔): ca. 75 % rechts
        x0 = int(cx - R / 4)  # etwas über die Mitte hinaus
        d_rect.rectangle((x0, cy - R, cx + R, cy + R), fill=255)
    elif phase_index == 4:
        # Vollmond (🌕): kompletter Kreis – einfach den Kreis nehmen
        rect_mask = Image.new("L", (size, size), 255)
    elif phase_index == 5:
        # Abnehmender Dreiviertelmond (🌖): ca. 75 % links
        x1 = int(cx + R / 4)
        d_rect.rectangle((cx - R, cy - R, x1, cy + R), fill=255)
    elif phase_index == 6:
        # Letztes Viertel (🌗): linke Hälfte
        d_rect.rectangle((cx - R, cy - R, cx, cy + R), fill=255)
    elif phase_index == 7:
        # Abnehmende Sichel (🌘): schmaler Streifen links
        x1 = int(cx - R + R / 3)
        d_rect.rectangle((cx - R, cy - R, x1, cy + R), fill=255)
    
    # Endgültige Maske = Schnittmenge von Kreis und Rechteck
    final_mask = ImageChops.multiply(circle_mask, rect_mask)
    return final_mask

def draw_fixed_phase(size, phase_index):
    """
    Erzeugt ein Bild der Mondphase mit fest definierten weißen Bereichen,
    die auf den kreisförmigen Mond (dunkler Hintergrund) beschränkt sind.
    """
    # Basisbild: Mond als dunkler Kreis mit schwarzem Rand
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    dark_color = (80, 80, 80, 255)
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=dark_color, outline="black")
    
    # Für Neumond (phase 0) gibt es nichts weiter
    if phase_index == 0:
        return img
    
    # Erstelle den weißen Bereich (als eigenes Layer)
    white_layer = Image.new("RGBA", (size, size), "white")
    # Hole die Maske für die aktuelle Phase
    mask = get_phase_mask(phase_index, size)
    # Füge den weißen Bereich nur dort ein, wo die Maske weiß (255) ist
    img.paste(white_layer, (0, 0), mask)
    return img

def upscale_image(img, factor):
    """Skaliert das Bild per Nearest-Neighbor für einen Pixel-Art-Look."""
    return img.resize((img.width * factor, img.height * factor), resample=Image.NEAREST)

def save_phases_as_png(size=64, factor=4, output_folder="moon_phases"):
    """
    Erzeugt für jede Mondphase ein PNG (fest definierte Darstellung) und
    speichert diese im Ordner 'output_folder'. Liefert eine Liste der
    (upgegelten) Bilder zurück.
    """
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    # Definition der 8 Mondphasen: (phase_index, Name)
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
    for phase_index, name in phases:
        img = draw_fixed_phase(size, phase_index)
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
    size = 64           # Basisbildgröße (in Pixel)
    factor = 4          # Upscale-Faktor für den Pixel-Art-Look
    duration = 500      # Dauer pro Frame in Millisekunden
    png_folder = "moon_phases"

    # Erstelle und speichere die PNGs für jede Mondphase
    images = save_phases_as_png(size=size, factor=factor, output_folder=png_folder)
    
    # Erstelle aus den PNG-Bildern ein animiertes GIF
    create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=duration)

if __name__ == "__main__":
    main()
