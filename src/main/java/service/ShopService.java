package service;

import model.Shop;
import model.Purchase;
import repository.ShopRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

public class ShopService extends AbstractService<Shop> {
    private ShopRepository shopRepository;

    public ShopService(ShopRepository repository) {
        super(repository);
        this.shopRepository = repository;
    }

    public Optional<Shop> findByName(String name) {
        return shopRepository.findByName(name);
    }

    public List<Shop> findByOwnerName(String ownerName) {
        return shopRepository.findByOwnerName(ownerName);
    }

    public List<Shop> findByNameContaining(String pattern) {
        return shopRepository.findByNameContaining(pattern);
    }

    public List<Shop> findByAreaRange(double minArea, double maxArea) {
        return shopRepository.findByAreaRange(minArea, maxArea);
    }

    public List<Shop> findShopsLargerThan(double area) {
        return shopRepository.findShopsLargerThan(area);
    }

    public List<Shop> findShopsSmallerThan(double area) {
        return shopRepository.findShopsSmallerThan(area);
    }

    public List<Shop> findShopsWithPurchases() {
        return shopRepository.findShopsWithPurchases();
    }

    public List<Shop> findShopsWithoutPurchases() {
        return shopRepository.findShopsWithoutPurchases();
    }

    public boolean addPurchase(String shopId, Purchase purchase) {
        return shopRepository.addPurchase(shopId, purchase);
    }

    public boolean removePurchase(String shopId, String purchaseId) {
        return shopRepository.removePurchase(shopId, purchaseId);
    }

    public double getTotalRevenue(String shopId) {
        return shopRepository.getTotalRevenue(shopId);
    }

    public OptionalDouble getAveragePurchaseAmount(String shopId) {
        return shopRepository.getAveragePurchaseAmount(shopId);
    }

    public long getPurchaseCount(String shopId) {
        return shopRepository.getPurchaseCount(shopId);
    }

    public Map<String, Double> getTopShopsByRevenue(int limit) {
        return shopRepository.getTopShopsByRevenue(limit);
    }

    public Map<String, Long> getTopShopsByPurchaseCount(int limit) {
        return shopRepository.getTopShopsByPurchaseCount(limit);
    }

    public List<Shop> findShopsByRevenueRange(double minRevenue, double maxRevenue) {
        return shopRepository.findShopsByRevenueRange(minRevenue, maxRevenue);
    }

    public Map<String, Object> getAreaStatistics() {
        return shopRepository.getAreaStatistics();
    }

    public Map<String, List<Shop>> getShopsByOwner() {
        return shopRepository.getShopsByOwner();
    }

    public boolean updateArea(String shopId, double newArea) {
        return shopRepository.updateArea(shopId, newArea);
    }

    public boolean updateOwner(String shopId, String newOwnerName) {
        return shopRepository.updateOwner(shopId, newOwnerName);
    }

    public void save(Shop entity) {
        shopRepository.save(entity);
    }
}