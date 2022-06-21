package com.example.whenandwhattime.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.whenandwhattime.entity.User;
import com.example.whenandwhattime.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
        User entity = repository.findByUsername(username);

        return entity;
    }
    
}