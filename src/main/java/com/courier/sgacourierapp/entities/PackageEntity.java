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
@Table(name = "sga_package")
public class PackageEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String orderId;
    private String orderType;
    private String name;
    private String description;
    private String packageType;
    private String weight;
    private String volume;
    private Date createdDate;
    private Date updatedDate;
}
