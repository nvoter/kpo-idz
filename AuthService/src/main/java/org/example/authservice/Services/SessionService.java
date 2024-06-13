package org.example.authservice.Services;

import com.sun.istack.NotNull;
import org.example.authservice.Entities.Session;
import org.example.authservice.Entities.User;
import org.example.authservice.Repositories.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionService {
    private final SessionRepo sessionRepo;
    private final int sessionExpiryHours;

    @Autowired
    public SessionService(
            SessionRepo sessionRepo,
            @Value("${session.expiry.hours}") int sessionExpiryHours
    ) {
        this.sessionRepo = sessionRepo;
        this.sessionExpiryHours = sessionExpiryHours;
    }

    @NotNull
    public void createSession(@NotNull User user, @NotNull String token) {
        LocalDateTime expiryTime = LocalDateTime.now().plusHours(sessionExpiryHours);
        Session session = sessionRepo.findFirstByUserId(user.getId())
                .orElseGet(() -> {
                    Session newSession = new Session();
                    newSession.setUser(user);
                    return newSession;
                });

        session.setToken(token);
        session.setExpires(expiryTime);
        sessionRepo.save(session);
    }
}