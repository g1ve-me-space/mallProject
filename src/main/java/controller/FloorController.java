package controller;

import model.Floor;
import model.Shop;
import model.MaintenanceTask;
import model.ElectricalAsset;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FloorService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/floors")
public class FloorController {

    private final FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping
    public ResponseEntity<List<Floor>> listAll() {
        return ResponseEntity.ok(floorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Floor> getById(@PathVariable String id) {
        Optional<Floor> f = floorService.findById(id);
        return f.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/numbers")
    public ResponseEntity<List<Integer>> numbers() {
        return ResponseEntity.ok(floorService.getAllFloorNumbers());
    }

    @GetMapping("/shopCounts")
    public ResponseEntity<Map<Integer, Integer>> shopCounts() {
        return ResponseEntity.ok(floorService.getFloorShopCounts());
    }

    @PostMapping("/{floorId}/shops")
    public ResponseEntity<?> addShopToFloor(@PathVariable String floorId, @RequestBody Shop shop) {
        boolean ok = floorService.addShopToFloor(floorId, shop);
        return ok ? ResponseEntity.status(201).build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{floorId}/tasks")
    public ResponseEntity<?> addTaskToFloor(@PathVariable String floorId, @RequestBody MaintenanceTask task) {
        boolean ok = floorService.addTaskToFloor(floorId, task);
        return ok ? ResponseEntity.status(201).build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{floorId}/electricals")
    public ResponseEntity<?> addElectricalToFloor(@PathVariable String floorId, @RequestBody ElectricalAsset ea) {
        boolean ok = floorService.addElectricalToFloor(floorId, ea);
        return ok ? ResponseEntity.status(201).build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{floorId}/shops/{shopId}")
    public ResponseEntity<?> removeShopFromFloor(@PathVariable String floorId, @PathVariable String shopId) {
        boolean ok = floorService.removeShopFromFloor(floorId, shopId);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Floor> create(@RequestBody Floor floor) {
        floorService.save(floor);
        return ResponseEntity.status(201).body(floor);
    }
}