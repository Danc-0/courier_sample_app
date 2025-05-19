package com.courier.sgacourierapp.events;

import com.courier.sgacourierapp.entities.OrderEntity;
import com.courier.sgacourierapp.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    @Autowired
    private InvoiceService invoiceService;

    @EventListener
    public void handleOrderCreated(final OrderCreatedEvent event) {
        final OrderEntity order = event.getOrder();
        invoiceService.createInvoice(order.getId());
    }
}