package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.common.CourierEnums;
import com.courier.sgacourierapp.entities.CustomerEntity;
import com.courier.sgacourierapp.entities.OrderEntity;
import com.courier.sgacourierapp.events.OrderCreatedEvent;
import com.courier.sgacourierapp.repository.CustomersRepository;
import com.courier.sgacourierapp.repository.OrdersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.courier.sgacourierapp.common.CourierEnums.*;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public List<OrderEntity> getAllOrders() {
        return ordersRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(OrderEntity::getCreatedDate).reversed())
                .collect(Collectors.toList());
    }

    public void addNewOrder(final OrderEntity orderEntity) {
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setOrderCreatedDate(LocalDateTime.now());
        orderEntity.setOrderStatus(OrderStatus.valueOf(orderEntity.getOrderStatus().name().toUpperCase()));
        final CustomerEntity customerEntity = customersRepository.findByEmail(String.valueOf(orderEntity.getCustomerRequestId()))
                .or(() -> customersRepository.findByPhoneNumber(String.valueOf(orderEntity.getCustomerRequestId())))
                .orElseThrow(EntityNotFoundException::new);
        final OrderEntity existingOrderEntity = ordersRepository.findAllByCourierId(orderEntity.getCourierId());
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer with ID " + orderEntity.getCustomerRequestId() + " not found.");
        } else if (existingOrderEntity != null) {
            throw new EntityNotFoundException("Order with ID " + orderEntity.getCourierId() + " already exists.");
        }
        orderEntity.setCustomer(customerEntity);
        orderEntity.setPrice(orderEntity.getPrice());
        OrderEntity savedOrder = ordersRepository.save(orderEntity);
        eventPublisher.publishEvent(new OrderCreatedEvent(this, savedOrder));
    }

    public List<OrderEntity> getAllOrdersByStatus(final String status) {
        return ordersRepository.findAllByOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
    }

    public OrderEntity updateOrderById(final OrderEntity orderEntity) {
        final CustomerEntity customerEntity = customersRepository.findByEmail(String.valueOf(orderEntity.getCustomerRequestId()))
                .or(() -> customersRepository.findByPhoneNumber(String.valueOf(orderEntity.getCustomerRequestId())))
                .orElseThrow(EntityNotFoundException::new);
        final OrderEntity existingOrder = ordersRepository.findById(orderEntity.getCourierId()).orElse(null);

        if (existingOrder == null) {
            throw new EntityNotFoundException("Order with ID " + orderEntity.getCourierId() + " not found.");
        }

        existingOrder.setCourierId(orderEntity.getCourierId());
        existingOrder.setOrderStatus(orderEntity.getOrderStatus());
        existingOrder.setOrderCreatedDate(orderEntity.getOrderCreatedDate());
        existingOrder.setOrderUpdatedDate(orderEntity.getOrderUpdatedDate());
        existingOrder.setOrderType(orderEntity.getOrderType());
        existingOrder.setOrderCategory(orderEntity.getOrderCategory());
        existingOrder.setUpdatedDate(LocalDateTime.now());

        return ordersRepository.save(existingOrder);
    }

    public OrderEntity getOrderById(Long id) {
        return ordersRepository.findById(id).get();
    }
}
