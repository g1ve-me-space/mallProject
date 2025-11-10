package service;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * FIX: This service no longer extends the old AbstractService.
 * It is now a standalone service consistent with our new architecture.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    // --- Standard methods (previously inherited from AbstractService) ---
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    public void deleteById(String id) {
        customerRepository.deleteById(id);
    }

    // --- Custom pass-through methods (preserved) ---
    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    public List<Customer> findByCurrency(String currency) {
        return customerRepository.findByCurrency(currency);
    }

    public Optional<Customer> findCustomerWithMostPurchases() {
        return customerRepository.findCustomerWithMostPurchases();
    }

    /**
     * FIX: The save method is now part of this service directly.
     * It calls the overridden save method in the repository.
     */
    public void save(Customer customer) {
        // The repository's save method already contains the null-ID check,
        // so we just pass the object through.
        customerRepository.save(customer);
    }
}