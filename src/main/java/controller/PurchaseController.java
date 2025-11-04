package controller;

import model.Purchase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.PurchaseRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;

    public PurchaseController(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> listAll() {
        return ResponseEntity.ok(purchaseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getById(@PathVariable String id) {
        Optional<Purchase> p = purchaseRepository.findById(id);
        return p.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/byCustomer/{customerId}")
    public ResponseEntity<List<Purchase>> byCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(purchaseRepository.findByCustomerId(customerId));
    }

    @GetMapping("/byShop/{shopId}")
    public ResponseEntity<List<Purchase>> byShop(@PathVariable String shopId) {
        return ResponseEntity.ok(purchaseRepository.findByShopId(shopId));
    }

    @GetMapping("/range")
    public ResponseEntity<List<Purchase>> byAmountRange(@RequestParam double min, @RequestParam double max) {
        return ResponseEntity.ok(purchaseRepository.findByAmountRange(min, max));
    }

    @PostMapping
    public ResponseEntity<Purchase> create(@RequestBody Purchase purchase) {
        purchaseRepository.save(purchase);
        return ResponseEntity.status(201).body(purchase);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> stats() {
        return ResponseEntity.ok(purchaseRepository.getPurchaseStatistics());
    }

    @PatchMapping("/{id}/amount")
    public ResponseEntity<?> updateAmount(@PathVariable String id, @RequestParam double amount) {
        boolean ok = purchaseRepository.updatePurchaseAmount(id, amount);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}