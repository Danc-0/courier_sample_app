package com.courier.sgacourierapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;
    private Long courierId;
    private String orderStatus;
    private Date orderCreatedDate;
    private Date orderUpdatedDate;
    private String orderType;
    private String orderCategory;
    private Date createdDate;
    private Date updatedDate;
}
