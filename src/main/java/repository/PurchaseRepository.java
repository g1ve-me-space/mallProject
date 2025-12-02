package repository;

import model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, String> {
    // Filtrare după client
    List<Purchase> findByCustomer_Id(String customerId);
}