package com.josuelima.task_management.service;

import com.josuelima.task_management.repositories.UserRepositoty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepositoty userRepositoty;


    public AuthorizationService(UserRepositoty userRepositoty) {
        this.userRepositoty = userRepositoty;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositoty.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario não econtrado: " + username));
    }

}
