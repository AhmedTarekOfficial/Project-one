package com.saftyhub.project1.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import  com.saftyhub.project1.services.*;



@RestController
@RequestMapping("/api")
public class DatabaseRequestes {

    public UserAutho ff = new UserAutho() ;

    @GetMapping("/searching/{email}/{password}")
    public ResponseEntity<String> searching(@PathVariable("email") String employeeEmail,
                                            @PathVariable("password") String employeePassword) {

       

        if (ff != null) {
            System.out.println("Employee information found!");
            return ResponseEntity.ok("Employee information found!");
        } else {
            System.out.println("Can't find the employee information data, please recheck and try again.");
            return ResponseEntity.status(404).body("Employee not found");
        }
    }

    @GetMapping("/test")
public String test() {
    return "API is working";
}
}
