package repository;

import model.Shop;
import model.Purchase;
import java.util.*;
import java.util.stream.Collectors;

public abstract class ShopRepository extends AbstractRepository<Shop> {

    // Find shop by name
    public Optional<Shop> findByName(String name) {
        return store.values().stream()
                .filter(shop -> shop.getName() != null && shop.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Find shops by owner name
    public List<Shop> findByOwnerName(String ownerName) {
        return store.values().stream()
                .filter(shop -> shop.getOwnerName() != null && shop.getOwnerName().equalsIgnoreCase(ownerName))
                .collect(Collectors.toList());
    }

    // Find shops with name containing pattern
    public List<Shop> findByNameContaining(String pattern) {
        return store.values().stream()
                .filter(shop -> shop.getName() != null && shop.getName().toLowerCase().contains(pattern.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Find shops by area range
    public List<Shop> findByAreaRange(double minArea, double maxArea) {
        return store.values().stream()
                .filter(shop -> shop.getAreaSqm() >= minArea && shop.getAreaSqm() <= maxArea)
                .collect(Collectors.toList());
    }

    // Find shops larger than specified area
    public List<Shop> findShopsLargerThan(double area) {
        return store.values().stream()
                .filter(shop -> shop.getAreaSqm() > area)
                .collect(Collectors.toList());
    }

    // Find shops smaller than specified area
    public List<Shop> findShopsSmallerThan(double area) {
        return store.values().stream()
                .filter(shop -> shop.getAreaSqm() < area)
                .collect(Collectors.toList());
    }

    // Find shops with purchases
    public List<Shop> findShopsWithPurchases() {
        return store.values().stream()
                .filter(shop -> shop.getPurchases() != null && !shop.getPurchases().isEmpty())
                .collect(Collectors.toList());
    }

    // Find shops without purchases
    public List<Shop> findShopsWithoutPurchases() {
        return store.values().stream()
                .filter(shop -> shop.getPurchases() == null || shop.getPurchases().isEmpty())
                .collect(Collectors.toList());
    }

    // Add purchase to shop
    public boolean addPurchase(String shopId, Purchase purchase) {
        Optional<Shop> shopOpt = findById(shopId);
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            if (shop.getPurchases() == null) {
                shop.setPurchases(new ArrayList<>());
            }
            shop.getPurchases().add(purchase);
            save(shop.getId(), shop);
            return true;
        }
        return false;
    }

    // Remove purchase from shop
    public boolean removePurchase(String shopId, String purchaseId) {
        Optional<Shop> shopOpt = findById(shopId);
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            if (shop.getPurchases() != null) {
                boolean removed = shop.getPurchases().removeIf(purchase -> purchase.getId().equals(purchaseId));
                if (removed) {
                    save(shop.getId(), shop);
                    return true;
                }
            }
        }
        return false;
    }

    // Get total revenue for a shop
    public double getTotalRevenue(String shopId) {
        Optional<Shop> shopOpt = findById(shopId);
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            if (shop.getPurchases() != null) {
                return shop.getPurchases().stream()
                        .mapToDouble(Purchase::getAmount)
                        .sum();
            }
        }
        return 0.0;
    }

    // Get average purchase amount for a shop
    public OptionalDouble getAveragePurchaseAmount(String shopId) {
        Optional<Shop> shopOpt = findById(shopId);
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            if (shop.getPurchases() != null && !shop.getPurchases().isEmpty()) {
                return shop.getPurchases().stream()
                        .mapToDouble(Purchase::getAmount)
                        .average();
            }
        }
        return OptionalDouble.empty();
    }

    // Get purchase count for a shop
    public long getPurchaseCount(String shopId) {
        Optional<Shop> shopOpt = findById(shopId);
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            if (shop.getPurchases() != null) {
                return shop.getPurchases().size();
            }
        }
        return 0;
    }

    // Get top shops by revenue
    public Map<String, Double> getTopShopsByRevenue(int limit) {
        return store.values().stream()
                .collect(Collectors.toMap(
                        Shop::getId,
                        shop -> getTotalRevenue(shop.getId())
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    // Get top shops by purchase count
    public Map<String, Long> getTopShopsByPurchaseCount(int limit) {
        return store.values().stream()
                .collect(Collectors.toMap(
                        Shop::getId,
                        shop -> getPurchaseCount(shop.getId())
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    // Get shops by revenue range
    public List<Shop> findShopsByRevenueRange(double minRevenue, double maxRevenue) {
        return store.values().stream()
                .filter(shop -> {
                    double revenue = getTotalRevenue(shop.getId());
                    return revenue >= minRevenue && revenue <= maxRevenue;
                })
                .collect(Collectors.toList());
    }

    // Get area statistics for all shops
    public Map<String, Object> getAreaStatistics() {
        DoubleSummaryStatistics stats = store.values().stream()
                .mapToDouble(Shop::getAreaSqm)
                .summaryStatistics();

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalShops", stats.getCount());
        statistics.put("totalArea", stats.getSum());
        statistics.put("averageArea", stats.getAverage());
        statistics.put("minArea", stats.getMin());
        statistics.put("maxArea", stats.getMax());

        return statistics;
    }

    // Get shops grouped by owner
    public Map<String, List<Shop>> getShopsByOwner() {
        return store.values().stream()
                .filter(shop -> shop.getOwnerName() != null)
                .collect(Collectors.groupingBy(Shop::getOwnerName));
    }

    // Update shop area
    public boolean updateArea(String shopId, double newArea) {
        Optional<Shop> shopOpt = findById(shopId);
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            shop.setAreaSqm(newArea);
            save(shop.getId(), shop);
            return true;
        }
        return false;
    }

    // Update owner name
    public boolean updateOwner(String shopId, String newOwnerName) {
        Optional<Shop> shopOpt = findById(shopId);
        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();
            shop.setOwnerName(newOwnerName);
            save(shop.getId(), shop);
            return true;
        }
        return false;
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, Shop entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    // Alternative save method that uses the entity's own ID
    public void save(Shop entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Shop ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}