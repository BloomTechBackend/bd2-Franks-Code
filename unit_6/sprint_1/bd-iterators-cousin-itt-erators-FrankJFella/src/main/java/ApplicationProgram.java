import com.frank.addams.AddamsFamilyApplication;

import java.io.FileNotFoundException;
/********************************************************************************************
 * Application manager
 ********************************************************************************************/
public class ApplicationProgram {
        // If any method in the execuction path can throw a checked exception
        //    Each method in the execution path from where teh exception might be thrown and up
        //    must either catch the exception or have a throws on their method signature
        //    throws tells the compiler I know this exception might happen
        public static void main(String args[]) throws FileNotFoundException {
                // instantiate an instance of the application to be run
                AddamsFamilyApplication anAddamsFamilyApplication = new AddamsFamilyApplication();

                // invoke the application controller to start the application
                anAddamsFamilyApplication.run();
        }
}
