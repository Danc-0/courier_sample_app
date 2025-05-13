package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.entities.UserEntity;
import com.courier.sgacourierapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public UserEntity addUser(UserEntity user) {
        try {
            return usersRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserEntity> getAllUsers() {
        try {
            return usersRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserEntity getUserByPhoneNumber(String phoneNumber) {
        try {
            return usersRepository.findByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
