package org.cristi.backend_e_commerce.service;

import org.cristi.backend_e_commerce.Repo.UserRepo;
import org.cristi.backend_e_commerce.model.User;
import org.cristi.backend_e_commerce.model.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User register(User user) throws UserException {
        validateUser(user);
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user);
        System.out.println("registered");
        return repo.save(user);
    }

    private void validateUser(User user) throws UserException {
        if (repo.findByUsername(user.getUsername()) != null) {
            throw new UserException("Username already exists");
        }
        if (repo.findByEmail(user.getEmail()) != null) {
            throw new UserException("Email already registered");
        }
        if (!checkEmail(user.getEmail())) {
            throw new UserException("Incorrect email format");
        }
        if (!checkPassword(user.getPassword())) {
            throw new UserException("Password must contain a minimum of eight characters, at least one lower case letter, one upper case letter, one digit, and one special character.");
        }
    }

    // between 8-24 chars, at least one lower case, upper case, one digit, one symbol
    private boolean checkPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$");
    }

    private boolean checkEmail(String email) {
        return email.matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$");
    }

    public String verify(User user) {
        try {
            // Auth the user
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            System.out.println("authenticated");
            // if authenticated, generate and return token
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user.getUsername());
                System.out.println(token);
                return token;
            }
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed");
        }
        return "fail";
    }
}
