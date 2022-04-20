package com.frank;

public class FinalImmutabilityApplicationProgram {

        private  static int nums[] = {1, 2, 3, 4};

        public static void main(String[] args) {
                System.out.println("Hello from main()");

                showArray();

                aClass anObject = new aClass(nums);

                anObject.showClass();

                nums[0] = 9999;  // Change the first element im the main() array to  new value
                                 // The value in the aClass array was also changed
                                 // violates Encapsulation - Array in aClass was changed outside the class

                showArray();     // Display the values in the main() array
                anObject.showClass();  // Display the values in the aClass Object array
        }

        public static void showArray() {
                System.out.println(("\nContents of array in main(): "));
                for (int i = 0; i < nums.length; i++) {
                        System.out.println("Element " + i + ": " + nums[i]);
                }
        }

}
