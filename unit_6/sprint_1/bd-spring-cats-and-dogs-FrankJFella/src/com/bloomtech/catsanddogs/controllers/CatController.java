package com.bloomtech.catsanddogs.controllers;

import com.bloomtech.catsanddogs.repositories.CatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// REST is a protocal for communication between servers and apps
// a protocol a set rules to describe how two things communicate or interact

@RestController  // Tell server that are controllers in here to handle various paths
public class CatController
{
    // Hold teh data this class is using
    private CatRepository catRepo = new CatRepository();

    // @GetMapping tells the serve if you receive an HTTP GET request for the path given
    //                             run this function/method
    //         handle-this-path, return-this-type-of-date
    @GetMapping(value = "/cats", produces = {"application/json"})
    public ResponseEntity<?> methodNameDoesntMatter() // this method will run if a GET with /cats is received
    {
        System.out.println("---- method to handle /cats path is running");
        //            return-object(data             , status
        return new ResponseEntity<>(catRepo.getCats(), HttpStatus.OK);
    }
}
