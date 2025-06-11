package com.courier.sgacourierapp.controllers;

import com.courier.sgacourierapp.services.CustomerService;
import com.courier.sgacourierapp.services.OrdersService;
import com.courier.sgacourierapp.services.UsersService;
import com.courier.sgacourierapp.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/internal")
public class HomeController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private CustomerService customerService;

    @GetMapping({"", "/"})
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(final Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("users", usersService.getAllUsers());
        model.addAttribute("orders", ordersService.getAllOrders());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        return "dashboard";
    }
}
