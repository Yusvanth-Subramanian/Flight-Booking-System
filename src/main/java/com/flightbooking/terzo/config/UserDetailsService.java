package com.flightbooking.terzo.config;

import com.flightbooking.terzo.dto.UserDetailsDTO;
import com.flightbooking.terzo.entity.User;
import com.flightbooking.terzo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    UserRepo userRepo;

    @Autowired
    public UserDetailsService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user == null){
            return null;
        }
        return new UserDetailsDTO(user);
    }
}
