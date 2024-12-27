package com.shadowfax.vacationbookingsystem.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    // getId metodunu ekliyoruz
    public Long getId() {
        return user.getId();  // User modelindeki id'yi döndürüyoruz
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Kullanıcı rollerini buradan döndürebiliriz. Örnek olarak "ROLE_USER" döndürüyoruz.
        return List.of(() -> "ROLE_USER");
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
