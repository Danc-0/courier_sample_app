package com.courier.sgacourierapp.controllers;

import com.courier.sgacourierapp.entities.BusinessCustomerEntity;
import com.courier.sgacourierapp.entities.CustomerEntity;
import com.courier.sgacourierapp.entities.IndividualCustomerEntity;
import com.courier.sgacourierapp.services.AuthenticationService;
import com.courier.sgacourierapp.services.BusinessCustomerService;
import com.courier.sgacourierapp.services.CustomerService;
import com.courier.sgacourierapp.services.IndividualCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.courier.sgacourierapp.common.CourierEnums.*;

@Controller
@RequestMapping("/internal")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private BusinessCustomerService businessCustomerService;
    @Autowired
    private IndividualCustomerService individualCustomerService;
    @Autowired
    private AuthenticationService authenticationService;

    private final LocalDate today = LocalDate.now();

    @GetMapping("/admin/customers")
    public String showCustomersPage(final Model model) {
        final List<CustomerEntity> allCustomers = customerService.getAllCustomers();
        model.addAttribute("customerEntity", new CustomerEntity());
        model.addAttribute("businessEntity", new BusinessCustomerEntity());
        model.addAttribute("individualEntity", new IndividualCustomerEntity());
        model.addAttribute("customers", allCustomers);
        populateCustomerStats(model, allCustomers);

        return "customers";

    }

    @GetMapping("/customers/accounts")
    public String getCustomerByEmail(@RequestParam(value = "email", required = false) String email,
                                     Model model) {
        List<CustomerEntity> filteredCustomers;
        List<CustomerEntity> allCustomers = customerService.getAllCustomers();
        if (email != null && !email.isBlank()) {
            filteredCustomers = customerService.getAllCustomers(email.trim());
        } else {
            filteredCustomers = customerService.getAllCustomers();
        }
        populateCustomerStats(model, allCustomers);
        model.addAttribute("customers", filteredCustomers);
        return "customers";
    }

    @GetMapping("/all/customers")
    public ResponseEntity<List<CustomerEntity>> listAllCustomers() {
        List<CustomerEntity> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/customers/add")
    public String addCustomer(@ModelAttribute CustomerEntity customer,
                              @ModelAttribute BusinessCustomerEntity businessCustomer,
                              @ModelAttribute IndividualCustomerEntity individualCustomer,
                              final RedirectAttributes redirectAttributes) {
        try {
            customerService.addNewCustomer(customer, businessCustomer, individualCustomer);
            redirectAttributes.addFlashAttribute("successMessage", "Customer added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong. Please try again.");
        }
        return "redirect:/internal/setting/customers";
    }

    @GetMapping("/{email}")
    public String viewCustomer(@PathVariable String email, Model model) {
        final CustomerEntity customer = customerService.getCustomerByEmail(email);
        model.addAttribute("customer", customer);

        if (customer.getCustomerType().equals(CustomerTypes.BUSINESS)) {
            BusinessCustomerEntity businessDetails = businessCustomerService.getByBusinessCustomerId(customer.getId());
            model.addAttribute("businessDetails", businessDetails);
        } else {
            IndividualCustomerEntity individualDetails = individualCustomerService.getByIndividualCustomerId(customer.getId());
            model.addAttribute("individualDetails", individualDetails);
        }

        return "/customers/view";
    }

    @GetMapping("/business/add")
    public String showBusinessForm(final Model model) {
        model.addAttribute("customer", new CustomerEntity());
        model.addAttribute("businessCustomer", new BusinessCustomerEntity());
        model.addAttribute("accountManagers", authenticationService.getAllAccountManagers());
        return "customers/business_form";
    }

    @GetMapping("/individual/new")
    public String showIndividualForm(Model model) {
        model.addAttribute("customer", new CustomerEntity());
        model.addAttribute("individualCustomer", new IndividualCustomerEntity());
        return "customers/individual_form";
    }

    @GetMapping("/{email}/edit")
    public String editCustomer(@PathVariable String email, final Model model) {
        CustomerEntity customer = customerService.getCustomerByEmail(email);
        model.addAttribute("customer", customer);

        if (customer.getCustomerType() == CustomerTypes.BUSINESS) {
            final CustomerEntity existingCustomer = customerService.getCustomerByEmail(email);
            final BusinessCustomerEntity businessCustomer = businessCustomerService.getByBusinessCustomerId(existingCustomer.getId());
            model.addAttribute("businessCustomer", businessCustomer);
            return "customers/business_edit_form";
        } else {
            IndividualCustomerEntity individualCustomer = individualCustomerService.getByIndividualCustomerId(customer.getId());
            model.addAttribute("individualCustomer", individualCustomer);
            return "customers/individual_edit_form";
        }
    }

    @PostMapping("/customers/update")
    public String updatePartnerCustomer(
            @ModelAttribute CustomerEntity customer,
            @ModelAttribute BusinessCustomerEntity businessCustomer,
            @ModelAttribute IndividualCustomerEntity individualCustomer) {

        switch (customer.getCustomerType()) {
            case INDIVIDUAL -> {
                if (individualCustomer != null) {
                    individualCustomer.setCustomer(customer);
                    individualCustomerService.updateIndividualCustomer(individualCustomer);
                }
            }
            case BUSINESS -> {
                if (businessCustomer != null) {
                    businessCustomer.setCustomer(customer);
                    businessCustomerService.updateBusinessCustomer(businessCustomer);
                }
            }
        }
        customerService.updateCustomer(customer);
        return "redirect:/internal/setting/customers";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return "redirect:/internal/customers";
    }

    private void populateCustomerStats(Model model, List<CustomerEntity> customers) {
        final long totalCustomers = customers.size();
        final long totalActive = customerService.countCustomersByStatus(customers, Status.ACTIVE);
        final long inactive = customerService.countCustomersByStatus(customers, Status.INACTIVE);
        final long pendingApproval = customerService.countCustomersByStatus(customers, Status.PENDING);
        final long suspended = customerService.countCustomersByStatus(customers, Status.SUSPENDED);

        final long individualCount = customerService.countCustomersByType(customers, CustomerTypes.INDIVIDUAL);
        final long businessCount = customerService.countCustomersByType(customers, CustomerTypes.BUSINESS);

        final long newToday = customerService.countCustomersCreatedOnDate(customers, today);
        final long pendingToday = customerService.countCustomersByStatusAndCreatedDate(customers, Status.PENDING, today);
        final long updatedToday = customerService.countCustomersByStatusAndCreatedDate(customers, Status.ACTIVE, today);

        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("activeCustomers", totalActive);
        model.addAttribute("inactive", inactive);
        model.addAttribute("pendingCustomers", pendingApproval);
        model.addAttribute("suspendedCustomers", suspended);
        model.addAttribute("individualCount", individualCount);
        model.addAttribute("businessCount", businessCount);
        model.addAttribute("newToday", newToday);
        model.addAttribute("pendingToday", pendingToday);
        model.addAttribute("updatedToday", updatedToday);
    }
}

