package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.entities.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    @Query("SELECT SUM(i.totalAmount) FROM InvoiceEntity i")
    Double getTotalInvoiceAmount();

    @Query("SELECT SUM(i.totalAmount) FROM InvoiceEntity i WHERE i.paymentStatus = :status")
    Double getTotalInvoiceAmountByStatus(@Param("status") String status);

    List<InvoiceEntity> findAllByPaymentStatus(String paymentStatus);
}
