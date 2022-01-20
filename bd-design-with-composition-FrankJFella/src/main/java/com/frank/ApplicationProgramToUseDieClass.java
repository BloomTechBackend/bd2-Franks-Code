package com.frank;

public class ApplicationProgramToUseDieClass {

        public static void main(String[] args) {
                System.out.println("/n-------- Welcome to the Die class Test Program ---------");

                Die aDie = new Die(6,"Green");  // Instantiate one Green Die with 6 sides
                System.out.println("-".repeat(50) + "\n Here is one Green die with 6 sides\n"+"-".repeat(50));
                System.out.println(aDie);

                Die[] yahtzeeDice = new Die[5];  // Define an empty array of 5 Die

                // Initialize the yahtzeeDice array with 6 sided, purple sided die
                for (int i = 1; i <= yahtzeeDice.length; i++) {
                        yahtzeeDice[i-1] = new Die(6,"Purple"); // index=i-1 since value starts at 1 and index starts at 0
                }
                System.out.println("-".repeat(50) + "\n Here is are the 5 Purple yathzeeDice\n"+"-".repeat(50));

                for (int i = 1; i < yahtzeeDice.length+1; i++){
                        System.out.print("Die " + i +": ");
                        for (int j = 0; j < yahtzeeDice[i-1].getSides().size(); j++) {
                                System.out.print(" Side: " + (j+1) + ": "
                                                                + yahtzeeDice[i-1].getSides().get(j).getSideValue() + " ("
                                                                + yahtzeeDice[i-1].getSides().get(j).getSideColor()+")");
                        }
                        System.out.println();
                }



        }
}
