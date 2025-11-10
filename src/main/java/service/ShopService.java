package service;

import model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ShopRepository; // It now uses our new, concrete class

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    // --- Core service methods that call the repository ---

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public Optional<Shop> findById(String id) {
        return shopRepository.findById(id);
    }

    public void deleteById(String id) {
        // We now call deleteById to match the new Repository interface
        shopRepository.deleteById(id);
    }

    public void save(Shop shop) {
        // Adding business logic: Ensure a new shop gets an ID.
        if (shop.getId() == null || shop.getId().trim().isEmpty()) {
            shop.setId(UUID.randomUUID().toString());
        }
        shopRepository.save(shop);
    }

    // --- Custom service method that exposes the repository's custom method ---

    public Optional<Shop> findByName(String name) {
        return shopRepository.findByName(name);
    }
}