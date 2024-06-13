package org.example.authservice.Data;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginData {
    private String email;
    private String password;
}
