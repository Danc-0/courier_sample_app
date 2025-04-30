package com.courier.sgacourierapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sga_pattern_customer")
public class CustomerEntity {
    @Id @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String idNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String status;
    private Date createdDate;
    private Date updatedDate;
}
