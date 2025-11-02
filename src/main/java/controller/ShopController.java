package controller;

import model.Shop;
import model.Purchase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ShopService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ResponseEntity<List<Shop>> listAll() {
        return ResponseEntity.ok(shopService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getById(@PathVariable String id) {
        Optional<Shop> s = shopService.findById(id);
        return s.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByName(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String owner) {
        if (name != null) {
            Optional<Shop> s = shopService.findByName(name);
            return s.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else if (owner != null) {
            return ResponseEntity.ok(shopService.findByOwnerName(owner));
        } else {
            return ResponseEntity.badRequest().body("Provide name or owner query parameter");
        }
    }

    @PostMapping
    public ResponseEntity<Shop> create(@RequestBody Shop shop) {
        shopService.save(shop);
        return ResponseEntity.status(201).body(shop);
    }

    @PostMapping("/{id}/purchases")
    public ResponseEntity<?> addPurchase(@PathVariable String id, @RequestBody Purchase purchase) {
        boolean ok = shopService.addPurchase(id, purchase);
        return ok ? ResponseEntity.status(201).build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/revenue")
    public ResponseEntity<Double> totalRevenue(@PathVariable String id) {
        double rev = shopService.getTotalRevenue(id);
        return ResponseEntity.ok(rev);
    }

    @GetMapping("/topByPurchases")
    public ResponseEntity<Map<String, Long>> topByPurchaseCount(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(shopService.getTopShopsByPurchaseCount(limit));
    }

    @PatchMapping("/{id}/area")
    public ResponseEntity<?> updateArea(@PathVariable String id, @RequestParam double area) {
        boolean ok = shopService.updateArea(id, area);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/owner")
    public ResponseEntity<?> updateOwner(@PathVariable String id, @RequestParam String owner) {
        boolean ok = shopService.updateOwner(id, owner);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}