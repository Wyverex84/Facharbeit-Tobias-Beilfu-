import os
from PIL import Image, ImageDraw, ImageChops

def get_circle_mask(size, center, R):
    """Erzeugt eine kreisförmige Maske (Modus "L") der Größe size x size,
       weiß (255) innerhalb eines Kreises mit Radius R um center, sonst schwarz."""
    mask = Image.new("L", (size, size), 0)
    draw = ImageDraw.Draw(mask)
    cx, cy = center
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=255)
    return mask

def get_rect_mask(size, rect):
    """Erzeugt eine rechteckige Maske (Modus "L") der Größe size x size.
       rect ist die Bounding-Box (x0, y0, x1, y1) des Rechtecks."""
    mask = Image.new("L", (size, size), 0)
    draw = ImageDraw.Draw(mask)
    draw.rectangle(rect, fill=255)
    return mask

# Definiere die 16 Basisphasen (Index 0 bis 15)
# Jedes Tupel: (method, offset, radius_factor)
#  - "none" und "full" haben keine weiteren Parameter
#  - "rect" wird als fester Fall genutzt ("right" bzw. "left")
#  - "circle" bekommt einen offset (in Vielfachen von R) und einen radius_factor
base_phases = {
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

def interpolate_parameters(f):
    """
    Für einen kontinuierlichen Phasenwert f (0 <= f < 16) wird zwischen den
    beiden nächsten Basisphasen interpoliert.
    Liefert (method, offset, radius_factor).
    Falls die Methoden unterschiedlich sind, wird bei t<0.5 der untere, sonst der obere Wert gewählt.
    """
    # runde f in den Bereich [0,16)
    f = f % 16
    i = int(f)
    t = f - i
    j = (i + 1) % 16
    method_i, offset_i, rad_i = base_phases[i]
    method_j, offset_j, rad_j = base_phases[j]
    
    # Falls beide Phasen den Typ "circle" haben, interpolieren:
    if method_i == "circle" and method_j == "circle":
        offset = offset_i + t * (offset_j - offset_i)
        radius_factor = rad_i + t * (rad_j - rad_i)
        return ("circle", offset, radius_factor)
    else:
        # Bei unterschiedlichen Methoden (z.B. "circle" zu "rect"), wählen wir diskret
        if t < 0.5:
            return base_phases[i]
        else:
            return base_phases[j]

def get_phase_mask_continuous(f, size):
    """
    Erzeugt eine Maske (Modus "L") für einen kontinuierlichen Phasenwert f (0<= f <16)
    basierend auf der Interpolation der Basisphasen.
    """
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    base_mask = get_circle_mask(size, (cx, cy), R)
    
    method, offset, rad_factor = interpolate_parameters(f)
    if method == "none":
        return Image.new("L", (size, size), 0)
    elif method == "full":
        return base_mask
    elif method == "rect":
        # "rect" erwartet einen Zusatzstring ("right" oder "left")
        # Für diskrete Phasen, nutze den Wert aus der diskreten Definition:
        # Wähle hier den Wert des nächstgelegenen Basisphasenindexes:
        phase_idx = int(round(f)) % 16
        method_d, side, _ = base_phases[phase_idx]
        if side == "right":
            rect = (cx, cy - R, cx + R, cy + R)
        else:
            rect = (cx - R, cy - R, cx, cy + R)
        rect_mask = get_rect_mask(size, rect)
        return ImageChops.multiply(base_mask, rect_mask)
    elif method == "circle":
        center_extra = (int(cx + offset * R), cy)
        extra = get_circle_mask(size, center_extra, int(rad_factor * R))
        return ImageChops.multiply(base_mask, extra)
    else:
        return Image.new("L", (size, size), 0)

def draw_phase_image(size, f):
    """
    Erzeugt das Mondphasen-Bild (RGBA) in der Größe size x size.
    f ist der kontinuierliche Phasenwert (0<= f < 16).
    Der Basis-Mondkreis (grau mit schwarzem Rand) wird gezeichnet,
    anschließend wird der weiße (beleuchtete) Teil über die Maske hinzugefügt.
    """
    img = Image.new("RGBA", (size, size), (0,0,0,0))
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    dark_color = (80,80,80,255)
    draw = ImageDraw.Draw(img)
    draw.ellipse((cx-R, cy-R, cx+R, cy+R), fill=dark_color, outline="black")
    
    # Bei Neumond (f nahe 0 oder 16) soll kein weißer Anteil erscheinen.
    # Wir setzen hier einen kleinen Schwellenwert.
    if f < 0.01 or f > 15.99:
        return img
    
    white_layer = Image.new("RGBA", (size, size), "white")
    mask = get_phase_mask_continuous(f, size)
    img.paste(white_layer, (0, 0), mask)
    return img

def upscale_image(img, factor):
    """Skaliert das Bild per Nearest-Neighbor für den Pixel-Art-Look."""
    return img.resize((img.width*factor, img.height*factor), resample=Image.NEAREST)

def save_phases_as_png(size=64, factor=4, output_folder="moon_phases"):
    """
    Erzeugt für 120 Zwischenphasen ein PNG und speichert diese im Ordner output_folder.
    Liefert eine Liste der (upgegelten) Bilder zurück.
    """
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    total_frames = 120
    images = []
    # Der kontinuierliche Phasenwert soll von 0 bis 16 gehen (16 entspricht wieder Neumond).
    for i in range(total_frames):
        f = (i / (total_frames - 1)) * 16  # f in [0,16]
        img = draw_phase_image(size, f)
        filename = os.path.join(output_folder, f"moon_phase_{i:03d}.png")
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
    size = 64       # Basisbildgröße in Pixel
    factor = 4      # Upscale-Faktor für Pixel-Art
    duration = 50   # Dauer pro Frame (in ms)
    folder = "moon_phases"
    
    images = save_phases_as_png(size=size, factor=factor, output_folder=folder)
    create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=duration)

if __name__ == "__main__":
    main()
