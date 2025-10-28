package service;

import model.Customer;
import repository.CustomerRepository;
import java.util.List;
import java.util.Optional;

public class CustomerService extends AbstractService<Customer> {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository repository) {
        super(repository);
        this.customerRepository = repository;
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    public List<Customer> findByCurrency(String currency) {
        return customerRepository.findByCurrency(currency);
    }

    public Optional<Customer> findCustomerWithMostPurchases() {
        return customerRepository.findCustomerWithMostPurchases();
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}