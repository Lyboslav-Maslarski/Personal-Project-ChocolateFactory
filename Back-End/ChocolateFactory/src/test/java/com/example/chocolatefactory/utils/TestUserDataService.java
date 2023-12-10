package com.example.chocolatefactory.utils;

import com.example.chocolatefactory.domain.AppUserDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("test")
public class TestUserDataService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserDetails appUserDetails = new AppUserDetails("user@gmail.com", "123456", Collections.emptyList());
        appUserDetails.setId(1L);

        return appUserDetails;
    }
}
