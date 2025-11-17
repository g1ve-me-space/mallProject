package controller;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;

import java.util.List;
import java.util.UUID;

@Controller // This should be @Controller, NOT @RestController
@RequestMapping("/customer") // This should be /customer for the UI
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * GET /customer
     * Displays a list of all customers.
     */
    @GetMapping
    public String listAllCustomers(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customer/index"; // This returns the name of the HTML template
    }

    /**
     * GET /customer/new
     * Displays the form to create a new customer.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/form"; // This returns the form template
    }

    /**
     * POST /customer
     * Processes the form submission for creating a new customer.
     */
    @PostMapping
    public String createCustomer(@ModelAttribute Customer customer) {
        // Generate a unique String ID before saving
        customerService.save(customer);
        return "redirect:/customer"; // Redirects back to the list page
    }

    /**
     * POST /customer/{id}/delete
     * Deletes the specified customer.
     */
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable String id) {
        customerService.deleteById(id);
        return "redirect:/customer";
    }
}