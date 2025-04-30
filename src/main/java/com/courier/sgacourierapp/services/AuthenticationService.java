package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AuthenticationRepository userRepository;

    /**
     * Unique fields should be
     * Email
     * Phone Number
     * ID Number
     * Personal User ID
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .or(() -> userRepository.findByPhoneNumber(username))
                .or(() -> userRepository.findByIdNumber(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with input: " + username));
    }
}
