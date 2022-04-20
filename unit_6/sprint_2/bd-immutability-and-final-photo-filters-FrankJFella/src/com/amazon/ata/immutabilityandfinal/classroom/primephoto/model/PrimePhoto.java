package com.amazon.ata.immutabilityandfinal.classroom.primephoto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class representing a PrimePhoto - contains dimensions, and a list of Pixels that make up the image.
 */
// Made the class immutable by adding final to instance variable and adjust
public class PrimePhoto {
    private final List<Pixel> pixels;
    private final int height;
    private final int width;
    // used when saving to a buffered image
    private final int type;

    // constructor is passed a reference to a List<Pixel>
    //      use defensive copy to get the data in to this obect
    public PrimePhoto(List<Pixel> pixelList, int height, int width, int type) {
        if (pixelList.size() != (height * width)) {
            throw new IllegalArgumentException("Not enough pixels for the dimensions of the image.");
        }
        // this.pixels = pixelList;  // replaced by defensive copy
        this.pixels = new ArrayList(pixelList);  // defensive copy parameter to class
        this.height = height;
        this.width = width;
        this.type = type;
    }
    // Since pixels is a object, return a copy of it rather than the reference to it
    // defensive return of a reference from the class
    // anyone with a reference to an object can change it any time
    public List<Pixel> getPixels() {
        // return pixels;    // replaced by defensive return
        return new ArrayList<>(pixels);  // return a copy of the object
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pixels, height, width, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        PrimePhoto photo = (PrimePhoto) obj;

        return photo.height == this.height && photo.width == this.width &&
            photo.type == photo.type && Objects.equals(photo.pixels, this.pixels);
    }

}
