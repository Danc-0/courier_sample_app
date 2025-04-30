package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPhoneNumber(String username);
    Optional<UserEntity> findByEmail(String username);
    Optional<UserEntity> findByIdNumber(String username);
}
