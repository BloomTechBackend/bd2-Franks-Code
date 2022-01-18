package com.amazon.ata.hashingset.partsmanager;

import java.util.List;
import java.util.Objects;

public class DevicePart {
    // immutable - value cannot be changes once it is assigned (no setter defined in the class)
    //   mutable - value may be changed (using setter)
    private String manufacturer;             // read-only, non-changeable value (immutable)
    private String manufacturersPartNumber;  // read-only, non-changeable value (immutable)
    private List<AmazonDevice> devicesUsedIn;

    public DevicePart(String manufacturer, String manufacturersPartNumber, List<AmazonDevice> devicesUsedIn) {
        this.manufacturer = manufacturer;
        this.manufacturersPartNumber = manufacturersPartNumber;
        this.devicesUsedIn = devicesUsedIn;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getManufacturersPartNumber() {
        return manufacturersPartNumber;
    }

    public List<AmazonDevice> getDevicesUsedIn() {
        return devicesUsedIn;
    }

    public void setDevicesUsedIn(List<AmazonDevice> devicesUsedIn) {
        this.devicesUsedIn = devicesUsedIn;
    }

    public void addDeviceUsedIn(AmazonDevice amazonDevice) {
        devicesUsedIn.add(amazonDevice);
    }

    public void removeDeviceUsedIn(AmazonDevice amazonDevice) {
        devicesUsedIn.remove(amazonDevice);
    }

    @Override
    public String toString() {
        return String.format("Device Part: {manufacturer: %s, manufacturersPartNumber: %s, devicesUsedIn: %s}",
                manufacturer, manufacturersPartNumber, devicesUsedIn);
    }
    @Override // Tells the compiler to verify this a proper override - same name, parameters and return type
    // Note: Object is used for the parameter type because the method we are overriding has Object class as parameter type
    public boolean equals(Object o) {
        // The next line is for academic/training purposes only
        System.out.println("DevicePart equals()...");

        // if the object being compared is the same as the object you're comparing to, they are equal
        if (this == o) return true;

        // if the object being compared is null or a different class than the object you're comparing to, they are not equal
        if (o == null || getClass() != o.getClass()) return false;

        // instantiate an object of the class to used in comparison (object passed is an Object class object)
        DevicePart that = (DevicePart) o;

        // Use the class data members to determine equality
        // Note use of String class .equals to check manufacturer and manufacturerPartNumber
        return getManufacturer().equals(that.getManufacturer()) && getManufacturersPartNumber().equals(that.getManufacturersPartNumber());
    }

    @Override
    public int hashCode() {
        // The next line is for academic/training purposes only
        System.out.println("DevicePart hashCode()...");

        // Use the Java hash() to generate a Hash Code using the immutable data members of the class
        return Objects.hash(getManufacturer(), getManufacturersPartNumber());
    }
}
