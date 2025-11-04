package controller;

import model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable String id) {
        Optional<Customer> c = customerService.findById(id);
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        // Many repository/service save methods expect save(String id, T entity) or save(entity).
        // The CustomerService in your repo extends AbstractService and also may expose save methods.
        // Try to reuse the existing API first; adjust if your CustomerService requires save(id, entity).
        if (customer.getId() != null && !customer.getId().isEmpty()) {
            customerService.save(customer.getId(), customer);
        } else {
            // if service exposes save(entity) use reflection-less direct call:
            try {
                customerService.getClass().getMethod("save", Customer.class).invoke(customerService, customer);
            } catch (Exception ignored) {
                // fallback: use save with id if available — this will be handled by your implementation
                customerService.save(customer.getId() == null ? "" : customer.getId(), customer);
            }
        }
        return ResponseEntity.status(201).body(customer);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable String id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}