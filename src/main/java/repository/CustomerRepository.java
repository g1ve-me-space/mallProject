package repository;

import model.Customer;
import java.util.*;

public class CustomerRepository extends AbstractRepository<Customer> {

    // Additional customer-specific query methods can be added here

    public List<Customer> findByName(String name) {
        return store.values().stream()
                .filter(customer -> customer.getName().equalsIgnoreCase(name))
                .toList();
    }

    public List<Customer> findByCurrency(String currency) {
        return store.values().stream()
                .filter(customer -> customer.getCurrency().equalsIgnoreCase(currency))
                .toList();
    }

    public Optional<Customer> findCustomerWithMostPurchases() {
        return store.values().stream()
                .max(Comparator.comparingInt(customer ->
                        customer.getPurchases() != null ? customer.getPurchases().size() : 0));
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, Customer entity) {
        // Ensure the entity's ID matches the key
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    // Alternative save method that uses the entity's own ID
    public void save(Customer entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}