package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.entities.CustomerEntity;
import com.courier.sgacourierapp.entities.IndividualCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualRepository extends JpaRepository<IndividualCustomerEntity, Long> {

    IndividualCustomerEntity findByEmail(String email);
}
