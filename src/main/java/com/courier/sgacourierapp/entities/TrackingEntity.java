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
@Table(name = "sga_package_tracking")
public class TrackingEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String orderId;
    private String trackingNumber;
    private String currentLocation;
    private String status;
    private Date createdDate;
    private Date updatedDate;
}
