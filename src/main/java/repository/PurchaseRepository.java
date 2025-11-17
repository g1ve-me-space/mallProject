package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Purchase;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurchaseRepository extends InFileRepository<Purchase> {
    public PurchaseRepository() {
        super("purchase.json", new TypeReference<List<Purchase>>() {});
    }
}