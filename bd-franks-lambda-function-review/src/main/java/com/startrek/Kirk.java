package com.startrek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

//  Make sure the data types in the RequestHandler<> match the handleRequest() method
public class Kirk implements RequestHandler<String, String> {
        /*
          Method to handle AWS Lambda requests
        */
        @Override
        //     return-type  method-name(object-with-request-data, aws-lambda-context-object)
        public String  handleRequest(String aString,  Context context) {
        // an aws-lambda-context-object supplies methods and properties that provide
        //    information about the invocation, function, and execution environment.

                System.out.println("Kaaaaaaaaahnnnnnnnn!!!!!");

                return "Kirk here. Three to beam up";
                }
        }

