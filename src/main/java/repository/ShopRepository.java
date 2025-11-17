package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShopRepository extends InFileRepository<Shop> {

    public ShopRepository() {
        super("shop.json", new TypeReference<List<Shop>>() {});
    }

    /**
     * Finds a shop by its exact name, case-insensitively.
     * @param name The name of the shop to find.
     * @return an Optional containing the shop if found.
     */
    public Optional<Shop> findByName(String name) {
        return findAll().stream()
                .filter(shop -> shop.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}