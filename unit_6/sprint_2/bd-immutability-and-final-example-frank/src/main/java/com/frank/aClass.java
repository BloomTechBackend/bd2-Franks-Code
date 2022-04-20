package com.frank;

import java.util.Arrays;

public class aClass {
        private int[] anArray;

        public aClass(int[] intArray) {

                // anArray = intArray;   // changed from asigning the reference to defensive copy
                anArray = Arrays.copyOf(intArray, intArray.length);  // Copy the reference to a new object
        }

        public void showClass() {
                System.out.println(("\nContents of array in aClass: "));
                for (int i = 0; i < anArray.length; i++) {
                        System.out.println("Element " + i + ": " + anArray[i]);
                }
        }
}
