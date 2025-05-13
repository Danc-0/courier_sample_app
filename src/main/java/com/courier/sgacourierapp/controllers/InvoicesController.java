package com.courier.sgacourierapp.controllers;

import com.courier.sgacourierapp.entities.InvoiceEntity;
import com.courier.sgacourierapp.services.InvoiceService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/internal")
public class InvoicesController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/billing")
    public String showBillingDashboard(Model model) {
        List<InvoiceEntity> invoices = invoiceService.getAllInvoices();
        Double totalRevenue = invoiceService.getTotalInvoices();
        Double totalPaid = invoiceService.getTotalInvoiceAmountPerStatus("paid");
        Double totalUnpaid = invoiceService.getTotalInvoiceAmountPerStatus("pending");

        model.addAttribute("invoices", invoices);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalPaid", totalPaid);
        model.addAttribute("totalUnpaid", totalUnpaid);

        return "billing";
    }

    @GetMapping("/getAllBills")
    public ResponseEntity<List<InvoiceEntity>> getAllBills() {
        final List<InvoiceEntity> invoiceServices = invoiceService.getAllInvoices();
        return ResponseEntity.ok().body(invoiceServices);
    }

    @GetMapping("/getAllBillsByStatus")
    public ResponseEntity<List<InvoiceEntity>> getAllBillsByStatus(final Model model, @RequestParam("status") String status) {
        final List<InvoiceEntity> invoiceServices = invoiceService.getAllInvoicesByStatus(status);
        model.addAttribute("invoices", invoiceServices);
        return ResponseEntity.ok().body(invoiceServices);
    }

    @GetMapping("/getBillingTotals")
    public ResponseEntity<Double> getBillingTotal() {
        final Double totalInvoices = invoiceService.getTotalInvoices();
        return ResponseEntity.ok(totalInvoices);
    }

    @GetMapping("/getBillById")
    public ResponseEntity<InvoiceEntity> getBill(@RequestParam Long id) {
        final InvoiceEntity invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok().body(invoice);
    }

    @PostMapping("/generateBillForOrder")
    public ResponseEntity<InvoiceEntity> generateBill(@RequestParam Long orderId) {
        return ResponseEntity.ok().body(new InvoiceEntity());
    }

    @PostMapping("/markAsPaid")
    public ResponseEntity<InvoiceEntity> markBillAsPaid(@RequestParam Long billId,
                                                        @RequestParam String paymentMethod) {
        return ResponseEntity.ok().body(new InvoiceEntity());
    }

    @GetMapping("/exportBillingReport")
    public void exportReport(@RequestParam String format, HttpServletResponse response) {
    }


}
