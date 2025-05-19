package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.common.CourierEnums;
import com.courier.sgacourierapp.entities.InvoiceEntity;
import com.courier.sgacourierapp.entities.OrderEntity;
import com.courier.sgacourierapp.repository.InvoiceRepository;
import com.courier.sgacourierapp.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrdersRepository ordersRepository;

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

    public InvoiceEntity createInvoice(final Long orderId) {
        try {
            OrderEntity orderEntity = ordersRepository.findById(orderId).get();
            InvoiceEntity invoiceEntity = new InvoiceEntity();
            invoiceEntity.setCustomerId(orderEntity.getCustomer().getId());
            invoiceEntity.setOrderId(orderId);
            invoiceEntity.setInvoiceNumber(generateInvoiceNumber());
            invoiceEntity.setCreatedDate(LocalDateTime.now());
            invoiceEntity.setDiscountAmount(0.0);
            invoiceEntity.setPaymentMethod(orderEntity.getPaymentMethods().name());
            invoiceEntity.setBillingStatus(CourierEnums.BillingStatuses.UNBILLED.name());
            invoiceEntity.setTotalAmount(calculateOrderPrice(orderEntity));
            invoiceEntity.setBasePrice(0.0);
            invoiceEntity.setPaymentStatus(CourierEnums.BillingPaymentStatuses.PENDING.name());
            return invoiceRepository.save(invoiceEntity);
        } catch (Exception e){
            throw new RuntimeException("Error while creating invoice");
        }
    }

    public String generateInvoiceNumber() {
        String prefix = "INV-";
        int year = LocalDateTime.now().getYear();

        int nextSequence = getNextInvoiceSequence();
        String paddedSequence = String.format("%03d", nextSequence);

        return prefix + year + paddedSequence;
    }

    public int getNextInvoiceSequence() {
        Long count = invoiceRepository.countByYear(LocalDate.now().getYear());
        return count.intValue() + 1;
    }

    public Double calculateOrderPrice(OrderEntity order) {
        double distance = Double.parseDouble(order.getDistanceKm());
        double weight = order.getWeight();
        double volume = order.getVolume();
        boolean express = CourierEnums.OrderTypes.EXPRESS.equals(order.getOrderType());

        BigDecimal distanceCost = BigDecimal.valueOf(distance * 150);
        BigDecimal weightCost = BigDecimal.valueOf(weight * 50);
        BigDecimal volumeCost = BigDecimal.valueOf(volume * 25);

        BigDecimal baseTotal = distanceCost.add(weightCost).add(volumeCost);

        BigDecimal modifier = BigDecimal.ZERO;

        if (weight > 20) {
            modifier = modifier.add(baseTotal.multiply(BigDecimal.valueOf(0.10)));
        }

        if (distance > 100) {
            modifier = modifier.add(baseTotal.multiply(BigDecimal.valueOf(0.15)));
        }

        if (volume > 100) {
            modifier = modifier.add(baseTotal.multiply(BigDecimal.valueOf(0.08)));
        }

        if (express) {
            double densityFactor = weight + volume;
            if (densityFactor < 20) {
                modifier = modifier.add(baseTotal.multiply(BigDecimal.valueOf(0.20))); // premium small item express
            } else if (densityFactor > 50) {
                modifier = modifier.add(BigDecimal.valueOf(500)); // flat express fee
                modifier = modifier.add(baseTotal.multiply(BigDecimal.valueOf(0.10))); // risk fee
            } else {
                modifier = modifier.add(BigDecimal.valueOf(500)); // standard express fee
            }
        }

        BigDecimal total = baseTotal.add(modifier);
        return Double.valueOf(String.valueOf(total.setScale(2, RoundingMode.HALF_UP)));
    }

}
