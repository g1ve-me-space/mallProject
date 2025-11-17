package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository extends InFileRepository<Customer> {

    public CustomerRepository() {
        super("customer.json", new TypeReference<List<Customer>>() {});
    }
}