package com.courier.sgacourierapp.entities;

import com.courier.sgacourierapp.common.CourierEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sga_customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CourierEnums.CustomerTypes customerType;

    @Enumerated(EnumType.STRING)
    private CourierEnums.PaymentTerms paymentTerms;

    @Enumerated(EnumType.STRING)
    private CourierEnums.Status status;

    private String email;
    private String phoneNumber;
    private String address;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
