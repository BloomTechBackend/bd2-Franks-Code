package com.amazon.ata.immutabilityandfinal.classroom.primephoto.model;

import java.util.Objects;

/**
 * An object that represent colors. Each object represents the color as three integers that stand for primary color
 * values.
 */
// Made the class immutable by adding final to instance variable and adjust use
public class RGB {

    // Made the instance variables final for immutability
    private final int red;
    private final int green;
    private final int blue;
    private final int transparency;

    private void check(int red, int green, int blue, int transparency) {
        if (red < 0 || red > 255 ||
            green < 0 || green > 255 ||
            blue < 0 || blue > 255) {
            throw new IllegalArgumentException(String.format("Invalid values color values. Red, green, " +
                "and blue must be between 0 and 255: {red: %d , green: %d, blue: %d}", red, green, blue));
        }
        if (transparency < 0 || transparency > 255) {
            throw new IllegalArgumentException("Invalid transparency value: " + transparency);
        }
    }

    // constructor - has no references being passed, do defensive copy is not necessary for immutability
    public RGB(int red, int green, int blue, int transparency) {
        check(red, green, blue, transparency);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.transparency = transparency;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getTransparency() {
        return transparency;
    }

    /**
     * Averages the red, blue, and green components, producing a grey color.
     */
    public RGB toGreyScale( ){
        int avg = (red + green + blue) / 3;

       // red = avg;    // cannot assign new values as it is defined as final
       // green = avg;  // cannot assign new values as it is defined as final
       // blue = avg;   // cannot assign new values as it is defined as final

        return(new RGB(avg, avg, avg, this.transparency));  // this. is optional also may use getTransparency()
    }

    /**
     * Converts the color to a reddish-brown color.
     */
    public RGB toSepia( ) {
        int newRed = (int)(0.393 * red + 0.769 * green + 0.189 * blue);
        int newGreen = (int)(0.349 * red + 0.686 * green + 0.168 * blue);
        int newBlue = (int)(0.272 * red + 0.534 * green + 0.131 * blue);

        // red = Math.min(255, newRed);    // cannot assign new values as it is defined as final
        // green = Math.min(255, newGreen);// cannot assign new values as it is defined as final
        // blue = Math.min(255, newBlue);  // cannot assign new values as it is defined as final
        return(new RGB(Math.min(255, newRed),Math.min(255, newGreen), Math.min(255, newBlue), getTransparency()));
    }

    /**
     * "Dark mode" - Assigns red, green, and blue, their current value subtracted from their max value (255).
     * This will turn white to black.
     */
    public RGB invert() {
        // red = 255 - red;     // cannot assign new values as it is defined as final
        // green = 255 - green; // cannot assign new values as it is defined as final
        // blue = 255 - blue;   // cannot assign new values as it is defined as final
        return(new RGB(255-red, 255 - green, 255 - blue, transparency));
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue, transparency);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        RGB rgb = (RGB) obj;

        return rgb.red == this.red && rgb.green == this.green &&
            rgb.blue == this.blue && rgb.transparency == this.transparency;
    }
}
