package service;

import model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ShopRepository;

import java.util.List;
import java.util.Optional;

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
        shopRepository.deleteById(id);
    }

    public void save(Shop shop) {
        // Do NOT set the ID here.
        // The repository will assign an ID if missing (new shop).
        shopRepository.save(shop);
    }

    // --- Custom service method that exposes the repository's custom method ---

    public Optional<Shop> findByName(String name) {
        return shopRepository.findByName(name);
    }
}