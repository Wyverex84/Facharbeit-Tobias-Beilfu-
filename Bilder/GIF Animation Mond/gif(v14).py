import os
from PIL import Image, ImageDraw, ImageChops

def get_circle_mask(size, center, R):
    mask = Image.new("L", (size, size), 0)
    draw = ImageDraw.Draw(mask)
    cx, cy = center
    draw.ellipse((cx - R, cy - R, cx + R, cy + R), fill=255)
    return mask

def get_rect_mask(size, rect):
    mask = Image.new("L", (size, size), 0)
    draw = ImageDraw.Draw(mask)
    draw.rectangle(rect, fill=255)
    return mask

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
    f = f % 16
    i = int(f)
    t = f - i
    j = (i + 1) % 16
    method_i, offset_i, rad_i = base_phases[i]
    method_j, offset_j, rad_j = base_phases[j]

    if method_i == "circle" and method_j == "circle":
        offset = offset_i + t * (offset_j - offset_i)
        radius_factor = rad_i + t * (rad_j - rad_i)
        return ("circle", offset, radius_factor)
    else:
        return base_phases[i] if t < 0.5 else base_phases[j]

def get_phase_mask_continuous(f, size):
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    base_mask = get_circle_mask(size, (cx, cy), R)

    method, offset, rad_factor = interpolate_parameters(f)
    if method == "none":
        return Image.new("L", (size, size), 0)
    elif method == "full":
        return base_mask
    elif method == "rect":
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
    img = Image.new("RGBA", (size, size), (0,0,0,0))
    cx, cy = size // 2, size // 2
    R = size // 2 - 2
    dark_color = (80,80,80,255)
    draw = ImageDraw.Draw(img)
    draw.ellipse((cx-R, cy-R, cx+R, cy+R), fill=dark_color, outline="black")

    if f < 0.01 or f > 15.99:
        return img

    white_layer = Image.new("RGBA", (size, size), "white")
    mask = get_phase_mask_continuous(f, size)
    img.paste(white_layer, (0, 0), mask)
    return img

def upscale_image(img, factor):
    return img.resize((img.width*factor, img.height*factor), resample=Image.NEAREST)

def save_phases_as_png(size=64, factor=4, output_folder="moon_phases", total_frames=240):
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    images = []
    for i in range(total_frames):
        f = (i / total_frames) * 16  # Kein doppelter Endframe
        img = draw_phase_image(size, f)
        filename = os.path.join(output_folder, f"moon_phase_{i:03d}.png")
        img.save(filename)
        print(f"{filename} gespeichert.")
        images.append(upscale_image(img, factor))
    return images

def create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=33):
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
    size = 64
    factor = 4
    duration = 33   # ~30 FPS
    total_frames = 240
    folder = "moon_phases"

    images = save_phases_as_png(size=size, factor=factor, output_folder=folder, total_frames=total_frames)
    create_gif_from_images(images, gif_filename="moon_cycle.gif", duration=duration)

if __name__ == "__main__":
    main()