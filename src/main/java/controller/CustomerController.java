package controller;
import java.util.UUID;
import jakarta.validation.Valid; // Import necesar pentru validare
import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Import necesar pentru erori
import org.springframework.web.bind.annotation.*;
import service.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String listAllCustomers(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customer/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/form";
    }

    /**
     * POST /customer
     * Aici am modificat pentru a prinde erorile de validare.
     */
    @PostMapping
    public String createCustomer(@Valid @ModelAttribute Customer customer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "customer/form";
        }

        // ⚠️ FIX: Generăm ID-ul dacă lipsește!
        if (customer.getId() == null || customer.getId().isEmpty()) {
            customer.setId(UUID.randomUUID().toString());
        }

        customerService.save(customer);
        return "redirect:/customer";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable String id) {
        customerService.deleteById(id);
        return "redirect:/customer";
    }
}