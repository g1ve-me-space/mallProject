package repository;

import model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
// FIX: The class now extends our new InMemoryRepository
public class CustomerRepository extends InMemoryRepository<Customer, String> {

    // --- YOUR CUSTOM FINDER METHODS ARE PRESERVED ---
    // They continue to work because the underlying 'store' map still exists.

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
        // This assumes the Purchase list is managed elsewhere and set on the customer
        return store.values().stream()
                .max(Comparator.comparingInt(customer ->
                        customer.getPurchases() != null ? customer.getPurchases().size() : 0));
    }

    /**
     * FIX: Overriding the new 'save' method from InMemoryRepository.
     * This preserves your important null-check logic.
     */
    @Override
    public void save(Customer entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        // Calls the save method in the parent InMemoryRepository
        super.save(entity);
    }
}