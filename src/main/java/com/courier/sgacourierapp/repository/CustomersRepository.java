package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.entities.CustomerEntity;
import com.courier.sgacourierapp.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);

    Optional<List<CustomerEntity>> findAllByEmail(String email);
}
