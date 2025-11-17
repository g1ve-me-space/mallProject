package service;

import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    public void deleteById(String id) {
        customerRepository.deleteById(id);
    }

    public void save(Customer customer) {
        // The repository will handle ID and persistence
        customerRepository.save(customer);
    }
}