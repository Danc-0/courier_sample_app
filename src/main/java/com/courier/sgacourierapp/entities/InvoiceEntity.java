package com.courier.sgacourierapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sga_invoices")
public class InvoiceEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int oderId;
    private String invoiceNumber;
    private String invoiceType;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private String createdDate;
    private String updatedDate;
}
