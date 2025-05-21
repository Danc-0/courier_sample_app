package com.courier.sgacourierapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFormData {
    private OrderEntity order;
    private UserEntity dispatcher;
}
