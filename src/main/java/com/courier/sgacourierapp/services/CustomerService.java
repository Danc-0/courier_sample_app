package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.common.CourierEnums;
import com.courier.sgacourierapp.common.StringValidator;
import com.courier.sgacourierapp.entities.BusinessCustomerEntity;
import com.courier.sgacourierapp.entities.CustomerEntity;
import com.courier.sgacourierapp.entities.IndividualCustomerEntity;
import com.courier.sgacourierapp.repository.BusinessRepository;
import com.courier.sgacourierapp.repository.CustomersRepository;
import com.courier.sgacourierapp.repository.IndividualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private IndividualRepository individualRepository;

    public List<CustomerEntity> getAllCustomers() {
        return customersRepository.findAll();
    }

    public List<CustomerEntity> getAllCustomers(final String email) {
        try {
            final Optional<List<CustomerEntity>> customer = customersRepository.findAllByEmail(email);
            return customer.orElseThrow(() -> new RuntimeException("Customer not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CustomerEntity getCustomerByEmail(final String email) {
        try {
            final Optional<CustomerEntity> customer = customersRepository.findByEmail(email);
            if (customer.isPresent()) {
                return customer.get();
            } else {
                throw new RuntimeException("Could not find customer with email: " + email);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomer(final CustomerEntity customer) {
        try {
            final CustomerEntity existingCustomerEntity = customersRepository.findById(customer.getId()).orElse(null);
            if (existingCustomerEntity != null) {
                existingCustomerEntity.setEmail(customer.getEmail());
                existingCustomerEntity.setPhoneNumber(customer.getPhoneNumber());
                existingCustomerEntity.setPaymentTerms(customer.getPaymentTerms());
                existingCustomerEntity.setStatus(customer.getStatus());
                existingCustomerEntity.setUpdatedDate(LocalDateTime.now());
                customersRepository.save(existingCustomerEntity);
            } else {
                throw new IllegalArgumentException("Customer does not exist");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewCustomer(final CustomerEntity customer,
                               final BusinessCustomerEntity businessCustomer,
                               final IndividualCustomerEntity individualCustomer) {
        try {
            final Optional<CustomerEntity> customerEntity = customersRepository.findByEmail(customer.getEmail());
            if (customerEntity.isEmpty()) {
                customer.setPhoneNumber(StringValidator.sanitizePhoneNumber(customer.getPhoneNumber()));
                customer.setCreatedDate(LocalDateTime.now());
                switch (customer.getCustomerType()) {
                    case INDIVIDUAL:
                        individualCustomer.setCustomer(customer);
                        individualCustomer.setCustomerId(customer.getId());
                        individualCustomer.setPhone(StringValidator.sanitizePhoneNumber(customer.getPhoneNumber()));
                        createIndividualCustomer(individualCustomer);
                        break;
                    case BUSINESS:
                        businessCustomer.setCustomer(customer);
                        businessCustomer.setCustomerId(customer.getId());
                        createBusinessCustomer(businessCustomer);
                        break;
                    default:
                        throw new RuntimeException("Invalid customer type");
                }
            } else {
                throw new IllegalArgumentException("Customer already exists");
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createBusinessCustomer(final BusinessCustomerEntity businessCustomer) {
        try {
            BusinessCustomerEntity businessCustomerEntity = businessRepository.findByRegistrationNumber(businessCustomer.getRegistrationNumber());
            if (businessCustomerEntity != null) {
                throw new IllegalArgumentException("Business customer already exists");
            }
            businessRepository.save(businessCustomer);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createIndividualCustomer(IndividualCustomerEntity individualCustomer) {
        try {
            final IndividualCustomerEntity individualCustomerEntity = individualRepository.findByEmail(individualCustomer.getEmail());
            if (individualCustomerEntity != null) {
                throw new IllegalArgumentException("Individual customer already exists");
            }
            individualCustomer.setCreatedDate(LocalDateTime.now());
            individualRepository.save(individualCustomer);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(final String email) {
        try {
            final Optional<CustomerEntity> customer = customersRepository.findByEmail(email);
            customer.ifPresent(customerEntity -> customersRepository.delete(customerEntity));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long countCustomersByStatus(List<CustomerEntity> customers, CourierEnums.Status status) {
        return customers.stream()
                .filter(customer -> status.equals(customer.getStatus()))
                .count();
    }

    public long countCustomersByType(List<CustomerEntity> customers, CourierEnums.CustomerTypes type) {
        return customers.stream()
                .filter(customer -> type.equals(customer.getCustomerType()))
                .count();
    }

    public long countCustomersCreatedOnDate(List<CustomerEntity> customers, LocalDate date) {
        return customers.stream()
                .filter(customer -> {
                    LocalDateTime createdDateTime = customer.getCreatedDate();
                    return createdDateTime != null && createdDateTime.toLocalDate().equals(date);
                })
                .count();
    }

    public long countCustomersByStatusAndCreatedDate(List<CustomerEntity> customers, CourierEnums.Status status, LocalDate date) {
        return customers.stream()
                .filter(customer -> status.equals(customer.getStatus()))
                .filter(customer -> {
                    LocalDateTime createdDateTime = customer.getCreatedDate();
                    return createdDateTime != null && createdDateTime.toLocalDate().equals(date);
                })
                .count();
    }

}
