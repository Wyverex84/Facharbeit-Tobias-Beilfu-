import os
from PIL import Image, ImageDraw

def draw_fixed_phase(draw, size, phase_index):
    """
    Zeichnet eine der 8 Mondphasen (fest definierte Formen) auf das übergebene Draw-Objekt.
    
    phase_index:
      0 = Neumond (🌑)
      1 = Zunehmende Sichel (🌒)
      2 = Erstes Viertel (🌓)
      3 = Zunehmender Dreiviertelmond (🌔)
      4 = Vollmond (🌕)
      5 = Abnehmender Dreiviertelmond (🌖)
      6 = Letztes Viertel (🌗)
      7 = Abnehmende Sichel (🌘)
    """
    cx, cy = size // 2, size // 2
    R = size // 2 - 2  # Radius, mit kleinem Rand
    dark_color = (80, 80, 80, 255)
    
    # Zuerst den gesamten Mond als dunkle Scheibe zeichnen
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=dark_color, outline="black")
    
    if phase_index == 0:
        # Neumond: keine weiße Fläche
        return
    
    elif phase_index == 1:
        # Zunehmende Sichel (🌒): nur ein schmaler weißer Bogen rechts
        # Zeichne einen weißen Pieslice, der nur einen kleinen Bereich rechts zeigt.
        draw.pieslice((cx - R, cy - R, cx + R, cy + R),
                      start=-40, end=40, fill="white", outline="black")
    
    elif phase_index == 2:
        # Erstes Viertel (🌓): rechte Hälfte weiß
        draw.rectangle((cx, cy - R, cx + R, cy + R), fill="white", outline="black")
    
    elif phase_index == 3:
        # Zunehmender Dreiviertelmond (🌔): überwiegend weiß, linke Seite dunkel
        # Zuerst den gesamten Kreis weiß zeichnen...
        draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
        # ...dann die linke Hälfte wieder dunkel überlagern
        draw.rectangle((cx - R, cy - R, cx, cy + R), fill=dark_color, outline="black")
    
    elif phase_index == 4:
        # Vollmond (🌕): kompletter weißer Kreis
        draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
    
    elif phase_index == 5:
        # Abnehmender Dreiviertelmond (🌖): überwiegend weiß, rechte Seite dunkel
        draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill="white", outline="black")
        draw.rectangle((cx, cy - R, cx + R, cy + R), fill=dark_color, outline="black")
    
    elif phase_index == 6:
        # Letztes Viertel (🌗): linke Hälfte weiß
        draw.rectangle((cx - R, cy - R, cx, cy + R), fill="white", outline="black")
    
    elif phase_index == 7:
        # Abnehmende Sichel (🌘): schmaler weißer Bogen links
        draw.pieslice((cx - R, cy - R, cx + R, cy + R),
                      start=140, end=220, fill="white", outline="black")

def upscale_image(img, factor):
    """Skaliert das Bild per Nearest-Neighbor, um den Pixel-Art-Look zu erhalten."""
    return img.resize((img.width * factor, img.height * factor), resample=Image.NEAREST)

def save_phases_as_png(size=64, factor=4, output_folder="moon_phases"):
    """
    Erzeugt für jede Mondphase ein PNG und speichert diese im Ordner 'output_folder'.
    Liefert eine Liste der (upgegelten) Bilder zurück.
    """
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    # Definition der 8 Mondphasen:
    # phase_index, Dateiname-Zusatz
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
        img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
        draw = ImageDraw.Draw(img)
        draw_fixed_phase(draw, size, phase_index)
        
        # Speichern als PNG
        filename = os.path.join(output_folder, f"moon_{name}.png")
        img.save(filename)
        print(f"{filename} gespeichert.")
        
        # Upscaling für Pixel-Art-Look
        img_upscaled = upscale_image(img, factor)
        images.append(img_upscaled)
    
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
