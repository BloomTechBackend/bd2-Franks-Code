package com.springbootproject.myControllers;

// This class will hold controllers for our app

import com.springbootproject.theData.Answer;
import com.springbootproject.theData.Question;
import org.springframework.web.bind.annotation.*;

// Data may be sent with an HTTP request:
//
//   GET or DELETE the data is sent in a query paramter in the URL   - @RequestParam
//   POST or PUT the data is sent in the body of the request as JSON - @RequestBody
//
//  Java deals in objects, the server deals with JSON
//  objects need to be converted to JSON and JSON converted to objects
//  The server automatically converts between Java objects and JSON
//      IF the Java objects are POJOs
//      A POJO has a default ctor, standard getters/setters,
//             optionally .equals(), .toString() and .hashCode() override

@RestController // Tell the server there are controller methods in this class
public class theControllers {

    // Method to handle HTTP GET requests for the root path (/)
    @GetMapping (value="/")
    public String rootPathMethod() {
        System.out.println("Root path '/' method was called");
        return "Attendance coded is 4122";
    }

    @GetMapping (value="/springboot") // handle path "/springboot"
    public String georgesFunction() {
        System.out.println("Path /springboot method was called");
        return "No real choice";
    }

    @GetMapping (value="/springboot/dixon") // handle path "/springboot"
    public String anyNameYouWant() {  // this method name is not used anywhere
        System.out.println("Path /springboot/dixon method was called");
        return "The path with Dixon";
    }
    @PostMapping(value="/springboot/dixon") // handle path "/springboot"
    public String somename() {  // this method name is not used anywhere
        System.out.println("Post for Path /springboot/dixon method was called");
        return "Post for path with Dixon";
    }

    @PostMapping (value="/ask")
    // Data will be sent in the body of the POST request
    // to gain access to that data we use @RequestBody in the parameter list of the method
    // the server will automatically convert the JSON from the request body to an object of the class
    //                                    class    object
    public Answer magic8Ball(@RequestBody Question questionAsked) {

        Answer theAnswerToQuestion = new Answer();

        switch(questionAsked.getTheQuestion()) {
            case "Who is teaching Unit 6?":
                theAnswerToQuestion.setTheAnswer("Frank");
                break;
            case "Who is teaching Unit 4?":
                theAnswerToQuestion.setTheAnswer("Mauli");
                break;
            case "Who is teaching Unit 5?":
                theAnswerToQuestion.setTheAnswer("Tom");
                break;
            default:
                theAnswerToQuestion.setTheAnswer("I do not know");
        }
         return theAnswerToQuestion;  // return object, server converts to JSON
        }

    // Example of retrieving data from query parameter of a GET request
    @GetMapping (value="/ask")
    // Data will be sent in the query request of the URL:
    //    /ask?unit=6
    // to gain access to that data we use @RequestParam in the parameter list of the method
    // the server will automatically convert query request paramter to the type of the parameter
    //
    public Answer magic8Ball(@RequestParam int unit) { // convert query param to int and store in unit

        Answer theAnswerToQuestion = new Answer();

        switch(unit) {
            case 6:
                theAnswerToQuestion.setTheAnswer("Frank");
                break;
            case 4:
                theAnswerToQuestion.setTheAnswer("Mauli");
                break;
            case 5:
                theAnswerToQuestion.setTheAnswer("Tom");
                break;
            default:
                theAnswerToQuestion.setTheAnswer("I do not know");
        }
        return theAnswerToQuestion;  // return object, server converts to JSON
    }



    }

