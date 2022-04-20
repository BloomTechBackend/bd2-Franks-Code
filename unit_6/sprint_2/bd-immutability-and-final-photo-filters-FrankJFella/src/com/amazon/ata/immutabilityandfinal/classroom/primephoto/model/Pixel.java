package com.amazon.ata.immutabilityandfinal.classroom.primephoto.model;

import java.util.Objects;

/**
 * Represents a single point in an image. Each pixel has a location in the image, and an associated RGB color.
 */
// Made this immutable by adding final to instance variables
//      and using defensive copy in the ctor
public class Pixel {
    private final int x;
    private final int y;
    private final RGB rgb;

    // constructor receives a reference to an RGB object
    // use defensive copying to add the RGB object to this object
    public Pixel(int x, int y, RGB rgb) {
        this.x = x;
        this.y = y;
        // this.rgb = rgb;  // replaced with defensive copy
        this.rgb = new RGB(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), rgb.getTransparency());
    }

    public RGB getRGB() {
        return rgb;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, rgb);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Pixel pixel = (Pixel) obj;

        return pixel.x == this.x && pixel.y == this.y &&
           Objects.equals(pixel.rgb, this.rgb);
    }
}
