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
@Table(name = "sga_internal_vehicle")
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vehicleId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
    private String vehicleName;
    private String vehicleType;
    private String vehicleModel;
    private int vehicleYear;
    private String vehicleNumberPlate;
    private String driverName;
    private Date createdDate;
    private Date updatedDate;
}
