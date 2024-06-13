package org.example.authservice.Controllers;

import org.example.authservice.Data.LoginData;
import org.example.authservice.Data.RegistrationData;
import org.example.authservice.Entities.User;
import org.example.authservice.Exceptions.UserNotFoundException;
import org.example.authservice.Security.JwtSource;
import org.example.authservice.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    private final JwtSource jwtSource;

    @Autowired
    public AuthController(UserService userService, JwtSource jwtSource) {
        this.userService = userService;
        this.jwtSource = jwtSource;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationData registrationData) {
        if (userService.findByEmail(registrationData.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User with this email already exists");
        }
        if (!checkEmail(registrationData.getEmail())) {
            return ResponseEntity.badRequest().body("Incorrect email");
        }
        if (!checkPassword(registrationData.getPassword())) {
            return ResponseEntity.badRequest().body("Password must be >= 8 symbols. Use letters, numbers and special symbols");
        }

        userService.register(registrationData);
        return ResponseEntity.ok("Registration done");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginData loginData) {
        String accessToken = userService.login(loginData);

        return ResponseEntity.ok("Login done \n Token: " + accessToken);
    }


    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(@RequestHeader String token) {
        try {
            String email = jwtSource.getUsername(token);
            System.out.println("\033[31m" + email + "\033[0m");

            User user = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);

            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    public boolean checkEmail(String email) {
        return email.indexOf('@') > 0 && email.lastIndexOf('.') > email.indexOf('@');
    }


    public boolean checkPassword(String password) {
        return password.length() >= 8 && password.matches(".*[A-Za-z].*") && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&+=].*");
    }
}
