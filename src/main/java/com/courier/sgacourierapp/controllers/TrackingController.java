package com.courier.sgacourierapp.controllers;

import com.courier.sgacourierapp.entities.OrderEntity;
import com.courier.sgacourierapp.entities.OrderFormData;
import com.courier.sgacourierapp.entities.UserEntity;
import com.courier.sgacourierapp.services.OrdersService;
import com.courier.sgacourierapp.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/internal")
public class TrackingController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/order/tracking")
    public String showTracking(
            @RequestParam(name = "courierId", required = false) final String courierId,
            final Model model) {

        List<OrderFormData> orderFormData;

        if (courierId != null && !courierId.isEmpty()) {
            try {
                orderFormData = ordersService.getAllAssignedActiveOrders(Long.valueOf(courierId));
            } catch (NumberFormatException e) {
                orderFormData = Collections.emptyList();
                model.addAttribute("errorMessage", "Invalid courier ID format");
            }
        } else {
            orderFormData = ordersService.getAllAssignedActiveOrders();
        }

        model.addAttribute("orderFormData", orderFormData);
        model.addAttribute("searchQuery", courierId);

        return "tracking";
    }
}