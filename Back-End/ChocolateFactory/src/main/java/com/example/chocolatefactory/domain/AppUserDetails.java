package com.example.chocolatefactory.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetails extends User {
    private Long id;
    private String fullName;
    public AppUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Long getId() {
        return id;
    }

    public AppUserDetails setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public AppUserDetails setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
