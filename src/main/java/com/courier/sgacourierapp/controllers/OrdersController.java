package com.courier.sgacourierapp.controllers;

import com.courier.sgacourierapp.entities.OrderEntity;
import com.courier.sgacourierapp.entities.OrderFormData;
import com.courier.sgacourierapp.entities.UserEntity;
import com.courier.sgacourierapp.services.OrdersService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/internal")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("orders")
    public String showOrders(final Model model) {
        final OrderFormData formData = new OrderFormData();
        formData.setOrder(new OrderEntity());
        formData.setDispatcher(new UserEntity());
        model.addAttribute("formData", formData);
        return "orders";
    }

    @PostMapping("addNewOrder")
    public String addNewOrder(@Valid @ModelAttribute("formData") final OrderFormData orderFormData,
                              final RedirectAttributes redirectAttributes) {
        try {
            ordersService.addNewOrder(orderFormData);
            redirectAttributes.addFlashAttribute("successMessage", "Order added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add order: " + e.getMessage());
        }
        return "redirect:/internal/orders";
    }

    @GetMapping("/order/edit/{courierId}")
    public String showEditOrderForm(@PathVariable String courierId, Model model) {
        try {
            OrderFormData formData = new OrderFormData();
            OrderEntity order = ordersService.getOrderByCourierId(Long.parseLong(courierId));
            if (order.getAssignedTo() != null) {
                UserEntity user = ordersService.getUserById(order.getAssignedTo());
                formData.setDispatcher(user);
            }
            formData.setOrder(order);
            List<UserEntity> userEntityList = ordersService.getDispatchUsers();

            model.addAttribute("formData", formData);
            model.addAttribute("dispatchers", userEntityList);
            model.addAttribute("showOrderModal", true);
            return "orders";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to retrieve order: " + e.getMessage());
            return "redirect:/internal/orders";
        }
    }

    @PostMapping("/order/edit")
    public String updateOrder(
            @Valid
            @ModelAttribute("formData") OrderFormData formData,
            RedirectAttributes redirectAttributes) {
        System.out.println("Sample Value: " + formData.getDispatcher().getId());
        try {

            ordersService.updateOrderByCourierId(formData.getOrder(), formData.getDispatcher());
            redirectAttributes.addFlashAttribute("successMessage", "Order updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update order: " + e.getMessage());
        }
        return "redirect:/internal/orders";
    }

    @GetMapping("/order/delete/{courierId}")
    public String deleteOrder(@PathVariable String courierId, RedirectAttributes redirectAttributes) {
        try {
            ordersService.deleteOrderByCourierId(Long.parseLong(courierId));
            redirectAttributes.addFlashAttribute("successMessage", "Order deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete order: " + e.getMessage());
        }
        return "redirect:/internal/orders";
    }

    @GetMapping("getAllOrders")
    @ResponseBody
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        final List<OrderEntity> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("getSingleOrder")
    @ResponseBody
    public ResponseEntity<OrderEntity> getSingleOrder(@RequestParam("id") final Long id) {
        final OrderEntity order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("getOrdersByStatus")
    @ResponseBody
    public ResponseEntity<List<OrderEntity>> getOrdersByStatus(@RequestParam("orderStatus") final String status) {
        final List<OrderEntity> ordersByStatus = ordersService.getAllOrdersByStatus(status);
        return ResponseEntity.ok(ordersByStatus);
    }

    @PostMapping("updateOrder")
    public String updateOrderDetails(
            @Valid
            @ModelAttribute("formData") OrderFormData orderFormData,
            final RedirectAttributes redirectAttributes) {
        try {
            ordersService.updateOrderByCourierId(orderFormData.getOrder(), orderFormData.getDispatcher());
            redirectAttributes.addFlashAttribute("successMessage", "Order updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update order: " + e.getMessage());
        }
        return "redirect:/internal/orders";
    }
}