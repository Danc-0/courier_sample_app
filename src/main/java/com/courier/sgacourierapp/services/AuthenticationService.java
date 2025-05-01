package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.entities.UserEntity;
import com.courier.sgacourierapp.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AuthenticationRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .or(() -> userRepository.findByPhoneNumber(username))
                .or(() -> userRepository.findByIdNumber(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with input: " + username));

        if (userEntity == null) {
            return null;
        }
        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole())
                .build();
    }
}
