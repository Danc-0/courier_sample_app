package com.courier.sgacourierapp.repository;

import com.courier.sgacourierapp.common.CourierEnums;
import com.courier.sgacourierapp.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.courier.sgacourierapp.common.CourierEnums.*;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByOrderStatus(OrderStatus orderStatus);

    OrderEntity findAllByCourierId(Long courierId);

    OrderEntity getOrderEntityById(Long id);
}
