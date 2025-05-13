package com.courier.sgacourierapp.controllers;

import com.courier.sgacourierapp.entities.OrderEntity;
import com.courier.sgacourierapp.services.OrdersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/internal")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("orders")
    public String showOrders(final Model model) {
        final OrderEntity order = new OrderEntity();
        model.addAttribute("orders", order);
        return "orders";
    }

    @PostMapping("addNewOrder")
    public String addNewOrder(@Valid @ModelAttribute("orders") final OrderEntity orderEntity, final RedirectAttributes redirectAttributes) {
        try {
            ordersService.addNewOrder(orderEntity);
            redirectAttributes.addFlashAttribute("successMessage", "Order added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong. Please try again.");
        }
        return "redirect:/internal/orders";
    }

    @GetMapping("getAllOrders")
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        final List<OrderEntity> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("getSingleOrder")
    public ResponseEntity<OrderEntity> getSingleOrder(@RequestParam("id") final Long id) {
        final OrderEntity order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("getOrdersByStatus")
    public ResponseEntity<List<OrderEntity>> getOrdersByStatus(@RequestParam("orderStatus") final String status) {
        final List<OrderEntity> ordersByStatus = ordersService.getAllOrdersByStatus(status);
        return ResponseEntity.ok(ordersByStatus);
    }

    @PostMapping("updateOrder")
    public ResponseEntity<OrderEntity> updateOrderDetails(@Valid @ModelAttribute("orders") OrderEntity orderEntity) {
        final OrderEntity order = ordersService.updateOrderById(orderEntity);
        return ResponseEntity.ok(order);
    }

}
