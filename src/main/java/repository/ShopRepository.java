package repository;

import model.Shop;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// It inherits findAll, findById, save, deleteById, count, existsById from InMemoryRepository.
public class ShopRepository extends InMemoryRepository<Shop, String> {

    /**
     * Finds a shop by its exact name, case-insensitively.
     * This is a core business method for this repository.
     * @param name The name of the shop to find.
     * @return an Optional containing the shop if found.
     */
    public Optional<Shop> findByName(String name) {
        // The 'store' map is inherited from the parent InMemoryRepository.
        return store.values().stream()
                .filter(shop -> shop.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}