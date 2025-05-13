package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.entities.BusinessCustomerEntity;
import com.courier.sgacourierapp.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessCustomerEntity, Long> {

    BusinessCustomerEntity findByRegistrationNumber(String registrationNumber);
}
