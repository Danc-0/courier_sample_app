package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.common.CourierEnums;
import com.courier.sgacourierapp.entities.CustomerEntity;
import com.courier.sgacourierapp.entities.OrderEntity;
import com.courier.sgacourierapp.repository.CustomersRepository;
import com.courier.sgacourierapp.repository.OrdersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.courier.sgacourierapp.common.CourierEnums.*;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomersRepository customersRepository;

    public List<OrderEntity> getAllOrders() {
        return ordersRepository.findAll();
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
        ordersRepository.save(orderEntity);
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
