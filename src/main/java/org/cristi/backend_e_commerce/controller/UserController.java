package org.cristi.backend_e_commerce.controller;

import org.cristi.backend_e_commerce.model.User;
import org.cristi.backend_e_commerce.model.UserException;
import org.cristi.backend_e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = service.register(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (UserException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        String token = service.verify(user);
        if (token != null && !token.equals("fail")) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @GetMapping("/user/myaccount")
    public ResponseEntity<?> getCurrentUser(){
        try{
            User currentUser = service.getCurrentUser();
            System.out.println(currentUser);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }catch (UserException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}