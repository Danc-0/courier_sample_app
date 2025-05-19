package com.courier.sgacourierapp.events;

import com.courier.sgacourierapp.entities.OrderEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderCreatedEvent extends ApplicationEvent {
    private final OrderEntity order;

    public OrderCreatedEvent(Object source, OrderEntity order) {
        super(source);
        this.order = order;
    }

}
