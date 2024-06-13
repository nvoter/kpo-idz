package org.example.authservice.Security;

import lombok.Getter;
import org.example.authservice.Entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean isActive;
    private final String nickname;

    public UserDetailsImpl(
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean isActive,
            String nickname
    ) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
        this.nickname = nickname;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(User user) {
        return new UserDetailsImpl(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                true,
                user.getNickname()
        );
    }
}