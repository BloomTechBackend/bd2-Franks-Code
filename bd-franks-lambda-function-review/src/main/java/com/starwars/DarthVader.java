package com.starwars;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DarthVader implements RequestHandler<String, String> {
        /*
          Method to handle AWS Lambda requests
        */
        @Override
        //     return-type  method-name(object-with-request-data, aws-lambda-context-object)
        public String  handleRequest(String aSentence,  Context context) {
                // an aws-lambda-context-object supplies methods and properties that provide
                //    information about the invocation, function, and execution environment.

                System.out.println("Luke, I am your father");

                return "I sense a disturbance in the force";
        }
}
