package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.entities.InvoiceEntity;
import com.courier.sgacourierapp.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<InvoiceEntity> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<InvoiceEntity> getAllInvoicesByStatus(final String status) {
        return invoiceRepository.findAllByPaymentStatus(status);
    }

    public Double getTotalInvoices() {
        return invoiceRepository.getTotalInvoiceAmount();
    }

    public Double getTotalInvoiceAmountPerStatus(final String status) {
        return invoiceRepository.getTotalInvoiceAmountByStatus(status);
    }

    public InvoiceEntity getInvoiceById(Long id) {
        return invoiceRepository.findById(id).get();
    }

}
