package com.frank.custom_exceptions;

import java.sql.Timestamp;

// Custom exceptions usually are subclasses of RunTimeException
public class InvalidBowlerNumberException extends RuntimeException {
        // Need a default ctor
        public InvalidBowlerNumberException() {}

        // Need a 1-arg String ctor to receive a message and pass it to the super class
        public InvalidBowlerNumberException(String theMessage) {
                super(getCurrentTimeStamp() + theMessage);
        }

        /**
         * return current timestamp
         */
        private static Timestamp getCurrentTimeStamp() {
                return new Timestamp(System.currentTimeMillis());
        }
}
