package com.courier.sgacourierapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sga_invoices")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long customerId;
    private String invoiceNumber;
    private String invoiceType;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime paymentDate;
    private Double basePrice;
    private Double taxAmount;
    private Double discountAmount;
    private Double totalAmount;
    private String billingStatus;
}
