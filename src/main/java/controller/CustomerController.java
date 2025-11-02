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

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> search(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String currency) {
        if (name != null) {
            return ResponseEntity.ok(customerService.findByName(name));
        } else if (currency != null) {
            return ResponseEntity.ok(customerService.findByCurrency(currency));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/mostPurchases")
    public ResponseEntity<Customer> mostPurchaser() {
        Optional<Customer> c = customerService.findCustomerWithMostPurchases();
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        customerService.save(customer);
        return ResponseEntity.status(201).body(customer);
    }
}