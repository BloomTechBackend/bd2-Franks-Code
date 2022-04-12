package com.bloomtech.catsanddogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Spring Boot is a framework to make a server easier to develop
// A framework is a set of classes to make common processes easier

// This code is automatically generated when you create a Spring Boot project
// DON'T CHANGE THIS CODE!

// This will start a Tomcat server
// Tomcat is an open source server provided by Apache
// Tomcat is the most common server used by Java apps

// The Tomcat server is called: localhost on port 8080
// "localhost" is the server name for your computer
// the server is at localhost street, Apt 8080

@SpringBootApplication  // Indicates this is a Spring Boot application

public class Application
{
    public static void main(String[] args) {  // this is main() for the Server
        //                 (class-containing-main(), any-args-to-pass-to-main)
        SpringApplication.run(Application.class, args);  // Starts the server running
    }

}
