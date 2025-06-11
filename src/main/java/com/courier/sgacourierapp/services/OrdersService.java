package com.courier.sgacourierapp.services;

import com.courier.sgacourierapp.common.CourierEnums;
import com.courier.sgacourierapp.entities.*;
import com.courier.sgacourierapp.events.OrderCreatedEvent;
import com.courier.sgacourierapp.repository.CustomersRepository;
import com.courier.sgacourierapp.repository.OrdersRepository;
import com.courier.sgacourierapp.repository.UsersRepository;
import com.courier.sgacourierapp.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.courier.sgacourierapp.common.CourierEnums.*;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

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

    public List<OrderFormData> getAllAssignedActiveOrders(final Long courierId) {
        List<OrderEntity> orderEntities = ordersRepository.getAllByCourierId(courierId);
        List<OrderFormData> ordersFormData = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            OrderFormData orderFormData = new OrderFormData();
            UserEntity userEntity = usersRepository.getUserEntityById(orderEntity.getAssignedTo());
            VehicleEntity vehicle = vehicleRepository.getById(userEntity.getId());
            orderFormData.setVehicle(vehicle);
            orderFormData.setDispatcher(userEntity);
            orderFormData.setOrder(orderEntity);
            ordersFormData.add(orderFormData);
        }
        return ordersFormData;
    }


    public List<OrderFormData> getAllAssignedActiveOrders() {
        List<OrderEntity> orderEntities = ordersRepository.findAllByAssignedToIsNotNullAndOrderStatus(OrderStatus.ACTIVE)
                .stream()
                .sorted(Comparator.comparing(OrderEntity::getCreatedDate).reversed())
                .toList();

        List<OrderFormData> ordersFormData = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            OrderFormData orderFormData = new OrderFormData();
            UserEntity userEntity = usersRepository.getUserEntityById(orderEntity.getAssignedTo());
            VehicleEntity vehicle = vehicleRepository.getById(userEntity.getId());
            orderFormData.setDispatcher(userEntity);
            orderFormData.setOrder(orderEntity);
            orderFormData.setVehicle(vehicle);
            ordersFormData.add(orderFormData);
        }

        return ordersFormData;
    }

    public void addNewOrder(final OrderFormData orderFormData) {
        orderFormData.getOrder().setCreatedDate(LocalDateTime.now());
        orderFormData.getOrder().setOrderCreatedDate(LocalDateTime.now());
        orderFormData.getOrder().setOrderStatus(OrderStatus.valueOf(orderFormData.getOrder().getOrderStatus().name().toUpperCase()));
        final CustomerEntity customerEntity = customersRepository.findByEmail(String.valueOf(orderFormData.getOrder().getCustomerRequestId()))
                .or(() -> customersRepository.findByPhoneNumber(String.valueOf(orderFormData.getOrder().getCustomerRequestId())))
                .orElseThrow(EntityNotFoundException::new);
        final OrderEntity existingOrderEntity = ordersRepository.findAllByCourierId(orderFormData.getOrder().getCourierId());
        if (customerEntity == null) {
            throw new EntityNotFoundException("Customer with ID " + orderFormData.getOrder().getCustomerRequestId() + " not found.");
        } else if (existingOrderEntity != null) {
            throw new EntityNotFoundException("Order with ID " + orderFormData.getOrder().getCourierId() + " already exists.");
        } else if (orderFormData.getDispatcher().getId() != null) {
            orderFormData.getOrder().setAssignedTo(orderFormData.getDispatcher().getId());
        }
        orderFormData.getOrder().setCustomer(customerEntity);
        orderFormData.getOrder().setPrice(orderFormData.getOrder().getPrice());
        OrderEntity savedOrder = ordersRepository.save(orderFormData.getOrder());
        eventPublisher.publishEvent(new OrderCreatedEvent(this, savedOrder));
    }

    public List<OrderEntity> getAllOrdersByStatus(final String status) {
        return ordersRepository.findAllByOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
    }

    public void updateOrderByCourierId(final OrderEntity orderEntity, final UserEntity userEntity) {
        try {
            final OrderEntity existingOrder = ordersRepository.findAllByCourierId(orderEntity.getCourierId());
            UserEntity existingUserEntity = new UserEntity();
            if (existingOrder == null || (existingOrder.getAssignedTo() != null && !existingOrder.getAssignedTo().equals(userEntity.getId()))) {
                throw new EntityNotFoundException("Order with ID " + orderEntity.getCourierId() + " could not be updated.");
            } else if (userEntity != null) {
                existingUserEntity = usersRepository.findById(userEntity.getId()).orElseThrow(EntityNotFoundException::new);
                existingUserEntity.setDispatchStatus(DispatchStatus.ASSIGNED);
                existingOrder.setAssignedTo(existingUserEntity.getId());
            } else if (orderEntity.getProgressPercent() == 0.0 && (orderEntity.getOrderStatus().equals(OrderStatus.ACTIVE) && orderEntity.getAssignedTo() != null)) {
                existingOrder.setProgressPercent(75);
            } else {
                throw new EntityNotFoundException("Order not assigned yet.");
            }

            if (existingOrder.getOrderStatus().equals(OrderStatus.PENDING) || existingOrder.getOrderStatus().equals(OrderStatus.ACTIVE)) {
                existingOrder.setWeight(orderEntity.getWeight());
                existingOrder.setVolume(orderEntity.getVolume());
                existingOrder.setPickupLongitude(orderEntity.getPickupLongitude());
                existingOrder.setPickupLatitude(orderEntity.getPickupLatitude());
                existingOrder.setDropoffLongitude(orderEntity.getDropoffLongitude());
                existingOrder.setDropoffLatitude(orderEntity.getDropoffLatitude());
                existingOrder.setOrderName(orderEntity.getOrderName());
                existingOrder.setCourierId(orderEntity.getCourierId());
                existingOrder.setOrderStatus(orderEntity.getOrderStatus());
                existingOrder.setOrderCreatedDate(orderEntity.getOrderCreatedDate());
                existingOrder.setOrderUpdatedDate(orderEntity.getOrderUpdatedDate());
                existingOrder.setOrderType(orderEntity.getOrderType());
                existingOrder.setOrderCategory(orderEntity.getOrderCategory());
                existingOrder.setUpdatedDate(LocalDateTime.now());
                existingOrder.setOrderUpdatedDate(LocalDateTime.now());

                usersRepository.save(existingUserEntity);
                ordersRepository.save(existingOrder);
            } else {
                throw new RuntimeException("Order cannot be updated.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public OrderEntity getOrderById(Long id) {
        return ordersRepository.findById(id).get();
    }

    public OrderEntity getOrderByCourierId(Long id) {
        return ordersRepository.findAllByCourierId(id);
    }

    @Transactional
    public void deleteOrderByCourierId(Long id) {
        ordersRepository.deleteByCourierId(id);
    }

    public UserEntity getUserById(Long id) {
        return usersRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<UserEntity> getDispatchUsers() {
        List<UserEntity> activeDispatchers = usersRepository.findAllByRoleAndDispatchStatus(
                CourierEnums.UserRoles.DISPATCHER, DispatchStatus.ACTIVE);

        List<UserEntity> assignedDispatchers = usersRepository.findAllByRoleAndDispatchStatus(
                CourierEnums.UserRoles.DISPATCHER, DispatchStatus.ASSIGNED);

        Set<UserEntity> allDispatchers = new HashSet<>();
        allDispatchers.addAll(activeDispatchers);
        allDispatchers.addAll(assignedDispatchers);

        return new ArrayList<>(allDispatchers);
    }

}
