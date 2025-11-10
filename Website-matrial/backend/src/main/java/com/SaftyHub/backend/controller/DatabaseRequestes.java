package main.java.com.SaftyHub.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import main.java.com.SaftyHub.backend.Service.*;

@RestController
@RequestMapping("/api")
public class DatabaseRequestes {

    private UserAutho ff;

    @GetMapping("/searching/{email}/{password}")
    public ResponseEntity<String> searching(@PathVariable("email") String employeeEmail,
                                            @PathVariable("password") String employeePassword) {

        ff = new Register(
            "accounts_information",
            new String[]{"email", "Password"},
            new String[]{"email", "Password"},
            new String[]{employeeEmail, employeePassword}
        );

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
