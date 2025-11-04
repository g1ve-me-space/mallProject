package repository;

import model.Purchase;
import java.util.*;
import java.util.stream.Collectors;

public abstract class PurchaseRepository extends AbstractRepository<Purchase> {

    // Find purchases by customer ID
    public List<Purchase> findByCustomerId(String customerId) {
        return store.values().stream()
                .filter(purchase -> purchase.getCustomerId() != null && purchase.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    // Find purchases by shop ID
    public List<Purchase> findByShopId(String shopId) {
        return store.values().stream()
                .filter(purchase -> purchase.getShopId() != null && purchase.getShopId().equals(shopId))
                .collect(Collectors.toList());
    }

    // Find purchases within amount range
    public List<Purchase> findByAmountRange(double minAmount, double maxAmount) {
        return store.values().stream()
                .filter(purchase -> purchase.getAmount() >= minAmount && purchase.getAmount() <= maxAmount)
                .collect(Collectors.toList());
    }

    // Find purchases above amount
    public List<Purchase> findPurchasesAboveAmount(double amount) {
        return store.values().stream()
                .filter(purchase -> purchase.getAmount() > amount)
                .collect(Collectors.toList());
    }

    // Find purchases below amount
    public List<Purchase> findPurchasesBelowAmount(double amount) {
        return store.values().stream()
                .filter(purchase -> purchase.getAmount() < amount)
                .collect(Collectors.toList());
    }

    // Get total purchase amount by customer
    public double getTotalAmountByCustomer(String customerId) {
        return store.values().stream()
                .filter(purchase -> purchase.getCustomerId() != null && purchase.getCustomerId().equals(customerId))
                .mapToDouble(Purchase::getAmount)
                .sum();
    }

    // Get total purchase amount by shop
    public double getTotalAmountByShop(String shopId) {
        return store.values().stream()
                .filter(purchase -> purchase.getShopId() != null && purchase.getShopId().equals(shopId))
                .mapToDouble(Purchase::getAmount)
                .sum();
    }

    // Get average purchase amount by customer
    public OptionalDouble getAverageAmountByCustomer(String customerId) {
        return store.values().stream()
                .filter(purchase -> purchase.getCustomerId() != null && purchase.getCustomerId().equals(customerId))
                .mapToDouble(Purchase::getAmount)
                .average();
    }

    // Get average purchase amount by shop
    public OptionalDouble getAverageAmountByShop(String shopId) {
        return store.values().stream()
                .filter(purchase -> purchase.getShopId() != null && purchase.getShopId().equals(shopId))
                .mapToDouble(Purchase::getAmount)
                .average();
    }

    // Get purchase count by customer
    public long getPurchaseCountByCustomer(String customerId) {
        return store.values().stream()
                .filter(purchase -> purchase.getCustomerId() != null && purchase.getCustomerId().equals(customerId))
                .count();
    }

    // Get purchase count by shop
    public long getPurchaseCountByShop(String shopId) {
        return store.values().stream()
                .filter(purchase -> purchase.getShopId() != null && purchase.getShopId().equals(shopId))
                .count();
    }

    // Get top customers by total purchase amount
    public Map<String, Double> getTopCustomersByTotalAmount(int limit) {
        return store.values().stream()
                .filter(purchase -> purchase.getCustomerId() != null)
                .collect(Collectors.groupingBy(
                        Purchase::getCustomerId,
                        Collectors.summingDouble(Purchase::getAmount)
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

    // Get top shops by total purchase amount
    public Map<String, Double> getTopShopsByTotalAmount(int limit) {
        return store.values().stream()
                .filter(purchase -> purchase.getShopId() != null)
                .collect(Collectors.groupingBy(
                        Purchase::getShopId,
                        Collectors.summingDouble(Purchase::getAmount)
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
                .filter(purchase -> purchase.getShopId() != null)
                .collect(Collectors.groupingBy(
                        Purchase::getShopId,
                        Collectors.counting()
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

    // Get total revenue across all purchases
    public double getTotalRevenue() {
        return store.values().stream()
                .mapToDouble(Purchase::getAmount)
                .sum();
    }

    // Get average purchase amount
    public OptionalDouble getAveragePurchaseAmount() {
        return store.values().stream()
                .mapToDouble(Purchase::getAmount)
                .average();
    }

    // Get purchase statistics
    public Map<String, Object> getPurchaseStatistics() {
        DoubleSummaryStatistics stats = store.values().stream()
                .mapToDouble(Purchase::getAmount)
                .summaryStatistics();

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalPurchases", stats.getCount());
        statistics.put("totalRevenue", stats.getSum());
        statistics.put("averagePurchase", stats.getAverage());
        statistics.put("minPurchase", stats.getMin());
        statistics.put("maxPurchase", stats.getMax());

        return statistics;
    }

    // Update purchase amount
    public boolean updatePurchaseAmount(String purchaseId, double newAmount) {
        Optional<Purchase> purchaseOpt = findById(purchaseId);
        if (purchaseOpt.isPresent()) {
            Purchase purchase = purchaseOpt.get();
            purchase.setAmount(newAmount);
            save(purchase.getId(), purchase);
            return true;
        }
        return false;
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, Purchase entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    // Alternative save method that uses the entity's own ID
    public void save(Purchase entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Purchase ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}