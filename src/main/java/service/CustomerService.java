package service;

import model.Customer;
import repository.CustomerRepository;

public class CustomerService extends AbstractService<Customer> {
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }
}
