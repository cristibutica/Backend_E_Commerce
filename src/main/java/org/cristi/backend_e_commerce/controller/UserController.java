package org.cristi.backend_e_commerce.controller;

import org.cristi.backend_e_commerce.model.User;
import org.cristi.backend_e_commerce.model.UserException;
import org.cristi.backend_e_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try {
            User registeredUser = service.register(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (UserException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}