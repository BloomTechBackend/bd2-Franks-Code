package com.amazon.ata.hashingset.partsmanager;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class PartManager {
    // instantiate a HashSet of DeviceParts to keep track of what parts we are currently using
    //       (more about HashSet tomorrow)
    // Since HashSet implements the Set interface, we can define a reference to the interface
    //       and assign it a HashSet object (allow use of Polymorphism is we need it)
    //       define  a reference to the super class/interface and assign subclass objects to it
    //
    // HashSet - Store elements using HashCode (store in HashCode order)

    private Set<DevicePart> deviceParts = new HashSet<>();            // Elements in a HashSet must have a unique Hash Code
//  private Set<DevicePart> deviceParts = new HashSet<DevicePart>();  // datatype of the Set may be specified when instantiating

// We are keeping the HashSet of DeviceParts so we have of both ignoring collisions (HashSet)
//        and handling collisions using an array of ArrayList
//        in the "real world" we would not do both

// In order to keep all objects even if HashCode collision occurs,
//          store the objects in an array of ArrayList of the objects
//          use the HashCode of the object to determine the array element to use to store the object
//          typically the modulus (%) of the HashCode and the number of elements in the array
//                        is used to compute the index of the element for array

    // Define the size of the array to hold our objects as a constant
    private final int NUMPARTS = 10;  // Using 10 so we will have HashCode collisions with teh small amount of data we have

    // Instantiate the array of ArrayList of DevicePart objects to hold our DevicePart objects
    // To define an array:  datatype[] name = new datatype[size];
    //              datatype[] name  = new  datatype[size];
    //                   int[] nums  = new       int[10];
    //       CollectionClass[] name  = new CollectionClass[size];
    private List<DevicePart>[] parts = new ArrayList[NUMPARTS];  // array of ArrayList - elements are null (no ArrayList, yet)

    public void addDevicePart(DevicePart devicePart) {
        // Use the HashSet .add() to add the part passed to use to the deviceParts hashSet
        //     .add automagically calls the hashCode() method to determine the Hash Code
        //     .add sometimes also calls the equals() method when there is a Hash Code collision
        //                    to determine if the new, colliding hashcode is the same object
        //                           if there is a collision, the new object is not stored
        boolean isAdded = deviceParts.add(devicePart);  // .add() returns true if object stored, false if not

        // Also add the devicePart passed to us to the array so we can store colliding objects

        // To add an element to the array:
        //   1. Find the HashCode for the object to be added
        //   2. Use the HashCode to determine the index in the array in which we should store the object
        //      Use the Math.abs() to ensure we have non-negative index values
        int partIndex = Math.abs(devicePart.hashCode() % NUMPARTS);  // array index based on the HashCode

        // 3. Store the new devicePart in the array using the calculated index


        if (parts[partIndex] != null) {       //    Do we already have an ArrayList for the element at the index?
            parts[partIndex].add(devicePart); //       yes - add the new devicePart to the existing ArrayList for the element
        }
        else {                                //       no ...
            parts[partIndex] = new ArrayList<DevicePart>();   // instantiate an Arraylist for the element
            parts[partIndex].add((devicePart));               // add the new devicePart to the ArrayList
        }

        return;  // added for academic/training purposes so we can stop method here in the debugger
    }

    /**
     * Find and return an object in our array of objects or null if not found
     */
    public DevicePart findPart(DevicePart requestedDevicePart){
        // Instantiate a object to be returned initialized to null
        DevicePart returnedObject = null;

        // Use the Hash Code for the requested object to determine it's index in the array
        int partIndex = Math.abs(requestedDevicePart.hashCode() % NUMPARTS);

        // Check to see if the element in the array for the index has an ArrayList() (if not, not objects)
        if(parts[partIndex] != null ) {                          // If there is an ArrayList in the element, search it for the requested object
            if(parts[partIndex].contains(requestedDevicePart)) { //    If we find the requested object
                // .get() of ArrayList to retrieve the object, .get() needs the index of object we want
                //        so we use indexOf() to find the index of the requested object
                returnedObject = parts[partIndex].get(parts[partIndex].indexOf(requestedDevicePart));  // set the returned object to it
            }
            else {
                returnedObject = null; // unnecessary since returnedObject was initialized to null & ony changes if we found an object
            }
        }
        // return the found object
        return returnedObject;
    }




    public void printDeviceParts() {
        for (DevicePart devicePart: deviceParts) {
            System.out.println(devicePart);
        }
    }
}
