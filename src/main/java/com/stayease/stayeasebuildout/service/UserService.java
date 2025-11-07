package com.stayease.stayeasebuildout.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stayease.stayeasebuildout.models.Users;
import com.stayease.stayeasebuildout.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Users registerUser(Users users) {
        return this.userRepository.save(users);
    }

    public boolean findUser(String email) {
        Optional<Users> isUser = this.userRepository.findByEmail(email);
        if(isUser.isPresent()) {
            return true;
        }
        else {
            return false;
        }
    }

}
