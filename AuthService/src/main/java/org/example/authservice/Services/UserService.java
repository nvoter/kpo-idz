package org.example.authservice.Services;

import com.sun.istack.NotNull;
import org.example.authservice.Exceptions.UserNotFoundException;
import org.example.authservice.Data.LoginData;
import org.example.authservice.Data.RegistrationData;
import org.example.authservice.Entities.User;
import org.example.authservice.Repositories.UserRepo;
import org.example.authservice.Security.JwtSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;
    private final JwtSource jwtSource;

    @Autowired
    public UserService(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            SessionService sessionService,
            AuthenticationManager authenticationManager,
            JwtSource jwtSource
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
        this.authenticationManager = authenticationManager;
        this.jwtSource = jwtSource;
    }

    @NotNull
    public User register(@NotNull RegistrationData registrationData) {
        User user = new User();
        user.setNickname(registrationData.getNickname());
        user.setEmail(registrationData.getEmail());
        user.setPassword(passwordEncoder.encode(registrationData.getPassword()));

        return userRepo.save(user);
    }

    @NotNull
    public String login(@NotNull LoginData loginData) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginData.getEmail(),
                        loginData.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String accessToken = jwtSource.createAccessToken(loginData.getEmail(), "ROLE_USER");

        User user = findByEmail(loginData.getEmail()).orElseThrow(UserNotFoundException::new);

        sessionService.createSession(user, accessToken);

        return accessToken;
    }
    
    public Optional<User> findByEmail(@NotNull String email) {
        return userRepo.findByEmail(email);
    }
}
