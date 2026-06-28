package com.secure.notes_api.security;

import com.secure.notes_api.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();   // the BCrypt hash
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();   // no roles/permissions yet — add later if needed
    }

    // Account-status flags — all true = account is active and usable.
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    // Convenience: expose the wrapped User so other code can reach it later
    public User getUser() {
        return user;
    }
}