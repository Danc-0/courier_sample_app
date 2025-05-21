package com.courier.sgacourierapp.entities;

import com.courier.sgacourierapp.common.CourierEnums;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.courier.sgacourierapp.common.CourierEnums.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CustomerEntity customer;

    @Column(name = "customer_request_id")
    private String customerRequestId;

    @Column(name = "courier_id")
    private Long courierId;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "pickup_latitude")
    private Double pickupLatitude;

    @Column(name = "pickup_longitude")
    private Double pickupLongitude;

    @Column(name = "dropoff_latitude")
    private Double dropoffLatitude;

    @Column(name = "dropoff_longitude")
    private Double dropoffLongitude;

    @Column(name = "pickup_address")
    private String pickupAddress;

    @Column(name = "dropoff_address")
    private String dropoffAddress;

    @Column(name = "distance_km")
    private String distanceKm;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(name = "order_created_date")
    private LocalDateTime orderCreatedDate;

    @Column(name = "order_updated_date")
    private LocalDateTime orderUpdatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderTypes orderType = OrderTypes.DEDICATED;

    @Column(name = "weight")
    private Long weight = 0L;

    @Column(name = "volume")
    private Long volume = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethods paymentMethods = PaymentMethods.MOBILE_MONEY;

    @Column(name = "order_category")
    private String orderCategory;

    @Column(name = "price")
    private Long price;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}

