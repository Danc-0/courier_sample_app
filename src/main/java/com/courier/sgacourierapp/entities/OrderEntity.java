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
@Table(name = "customer_orders")
public class OrderEntity {
    @Id @GeneratedValue
    private long id;
    private long customerId;
    private long courierId;
    private String orderStatus;
    private Date orderCreatedDate;
    private Date orderUpdatedDate;
    private String orderType;
    private String orderCategory;
    private Date createdDate;
    private Date updatedDate;
}
