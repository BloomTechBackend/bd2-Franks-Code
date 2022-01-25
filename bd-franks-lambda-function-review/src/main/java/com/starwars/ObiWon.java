package com.starwars;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ObiWon implements RequestHandler<String, String> {
        /*
          Method to handle AWS Lambda requests
        */
        @Override
        //     return-type  method-name(object-with-request-data, aws-lambda-context-object)
        public String  handleRequest(String aString,  Context context) {
                // an aws-lambda-context-object supplies methods and properties that provide
                //    information about the invocation, function, and execution environment.

                System.out.println("These aren’t the droids you’re looking for");

                return "May the force be with you";
        }
}
