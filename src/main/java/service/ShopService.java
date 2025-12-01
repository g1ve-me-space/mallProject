package service;

import model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ShopRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID; // Import necesar pentru generarea ID-urilor

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    // --- Core service methods ---

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public Optional<Shop> findById(String id) {
        return shopRepository.findById(id);
    }

    public void deleteById(String id) {
        shopRepository.deleteById(id);
    }

    public void save(Shop shop) {
        // ⚠️ FIX: Generăm manual ID-ul dacă este nou (JPA nu face asta automat pentru String)
        if (shop.getId() == null || shop.getId().trim().isEmpty()) {
            shop.setId(UUID.randomUUID().toString());
        }
        shopRepository.save(shop);
    }

    // --- Custom method ---

    public Optional<Shop> findByName(String name) {
        // Folosim metoda nouă din JPA Repository (definită la pasul anterior)
        return shopRepository.findByNameIgnoreCase(name);
    }
}