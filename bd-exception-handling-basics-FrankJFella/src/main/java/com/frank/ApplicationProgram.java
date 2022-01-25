package com.frank;

import com.frank.custom_exceptions.InvalidBowlerNumberException;
import com.frank.types.Bowler;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationProgram {

        private static List<Bowler> theBowlers = new ArrayList();

        public static void main(String[] args) throws FileNotFoundException {
                System.out.println("Start of application program");

                PrintStream errorLog = new PrintStream("myError.log");  // Define a file to be used for error messages
                System.setErr(errorLog);                                // Tell Java to use the PrintStream file as the
                //      standard error stream

                LoadBowlers();  // call method to instantiate some test data in ArrayList

                System.out.println("-".repeat(80) + "\n--- List of Bowlers ---");
                // I think an exception in this code, so I'm putting in a try block
                try {
                        for (Bowler aBowler : theBowlers) {
                                ShowBowler(aBowler);
                        }
                }
                //      exception-name      name-of-exception-object   - an object is passed to catch automatically
                catch (NullPointerException exceptionObject) {

                        // Note: We don't get here anymore since showBowler() handle NullPointerExcpetion
                        //       it no longer "Bubbles Up" to main()

                        System.out.println("\n" + getCurrentTimeStamp() + " Un-oh!!!!  Null pointer execption encountered");
                        System.out.println(getCurrentTimeStamp() + " The system says this is the problem: " + exceptionObject.getMessage());
                        System.err.println(getCurrentTimeStamp() + " How we got to the error: ");  // Write to the standard error stream
                        // printStackTrace display to the standard ERROR stream
                        // System.out displays to the standard OUTPUT stream
                        // BOTH are assigned to the screen - you may get intermixed/interleaved messaging
                        // You can assign one or the other or both to a PrintStream file instead of the screen
                        //       (see the top of this program for an example)

                        exceptionObject.printStackTrace();   // Ask the system to print the stack trace so we can see how we got to the error

                        System.out.println(getCurrentTimeStamp() + " Execution continuing - but no other Bowlers will be displayed");
                }
                System.out.println("-".repeat(80));
                String response = "";
                boolean shouldLoop = true;
                Scanner theKeyBoard = new Scanner(System.in);
                while (shouldLoop) {
                        System.out.println("\nEnter the number of the Bowler you would like displayed or 'e' to exit");
                        System.out.printf("Valid numbers are 1 thru %d\nYour choice: ", theBowlers.size());
                        response = theKeyBoard.nextLine();
                        if (response.toLowerCase().charAt(0) == 'e') {
                                shouldLoop = false;
                                continue;
                        }
                        try {
                                int bowlerNumber = Integer.parseInt(response);
                                ShowBowler(theBowlers.get(bowlerNumber - 1));
                        }
                        catch (NumberFormatException exceptionObj) {
                                try {
                                        throw new InvalidBowlerNumberException("input value " + response + "' was non-numeric");
                                }
                                catch (InvalidBowlerNumberException exceptionInfo) {
                                        exceptionInfo.printStackTrace();
                                        System.out.println(getCurrentTimeStamp() + " Please enter a numeric value in the range expected");
                                }
                        }
                        catch (IndexOutOfBoundsException exceptionObj) {
                                System.out.println("\nInvalid bowler number: " + response);
                                System.out.println("Please enter a value in the range indicated");
                                exceptionObj.printStackTrace();
                        }
                }
                System.out.println("-".repeat(80));

                System.out.println("End of application program");
                return;
        } // end of main()

        // After the end of main() - define some "helper methods"

        /**
         * Display data for a Bowler
         */
        // private method so only the main class can use it
        // the method must be static because main() that uses it is static
        private static void ShowBowler(Bowler aBowler) {
                System.out.print(aBowler);     // the Bowler class toString() is called to format the data as String
                try {
                        System.out.printf(" average: %.2f \n", aBowler.getAverage());
                } catch (NullPointerException e) {
                        System.out.println("\n" + getCurrentTimeStamp() + " Null Exception has occurred, check error log");
                }
        }

        /**
         * Add test data to test program data store
         */
        // private method so only the main class can use it
        // the method must be static because main() that uses it is static
        private static void LoadBowlers() {
                theBowlers.add(new Bowler("Fred Flintstone", new int[]{230, 260, 275}));
                theBowlers.add(new Bowler("Barney Rubble", new int[]{120, 140, 190}));
                theBowlers.add(new Bowler("The Dude", new int[]{260, 270, 290}));
                theBowlers.add(new Bowler());  // Use the default ctor - set name to '' and array to null
                theBowlers.add(new Bowler("Roy Munson", new int[]{225, 285, 252}));
        }

        /**
         * return current timestamp
         */
        private static Timestamp getCurrentTimeStamp() {
                return new Timestamp(System.currentTimeMillis());
        }
}

