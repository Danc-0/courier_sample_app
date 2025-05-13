package com.courier.sgacourierapp.entities;

import com.courier.sgacourierapp.common.CourierEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sga_individual_customers")
public class IndividualCustomerEntity {
    @Id
    @Column(name = "customer_id")
    private Long customerId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String idNumber;
    private String city;
    private CourierEnums.CountryCode countryCode;
    private LocalDate dateOfBirth;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
