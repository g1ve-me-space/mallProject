package controller;

import model.Floor;
import model.Mall;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.MallRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/malls")
public class MallController {

    private final MallRepository mallRepository;

    public MallController(MallRepository mallRepository) {
        this.mallRepository = mallRepository;
    }

    @GetMapping
    public ResponseEntity<List<Mall>> listAll() {
        return ResponseEntity.ok(mallRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mall> getById(@PathVariable String id) {
        Optional<Mall> m = mallRepository.findById(id);
        return m.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mall> create(@RequestBody Mall mall) {
        mallRepository.save(mall);
        return ResponseEntity.status(201).body(mall);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String city) {
        if (name != null) {
            Optional<Mall> m = mallRepository.findByName(name);
            return m.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
        if (city != null) {
            return ResponseEntity.ok(mallRepository.findByCity(city));
        }
        return ResponseEntity.badRequest().body("Provide name or city query parameter");
    }

    @PostMapping("/{mallId}/floors")
    public ResponseEntity<?> addFloor(@PathVariable String mallId, @RequestBody Floor floor) {
        boolean ok = mallRepository.addFloor(mallId, floor);
        return ok ? ResponseEntity.status(201).build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{mallId}/floors/{floorId}")
    public ResponseEntity<?> removeFloor(@PathVariable String mallId, @PathVariable String floorId) {
        boolean ok = mallRepository.removeFloor(mallId, floorId);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/totalShops")
    public ResponseEntity<Integer> totalShops() {
        return ResponseEntity.ok(mallRepository.getTotalShopsCount());
    }

    @GetMapping("/mostFloors")
    public ResponseEntity<Mall> mallWithMostFloors() {
        Optional<Mall> m = mallRepository.findMallWithMostFloors();
        return m.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/cities")
    public ResponseEntity<Set<String>> cities() {
        return ResponseEntity.ok(mallRepository.getAllCities());
    }

    @PatchMapping("/{id}/city")
    public ResponseEntity<?> updateCity(@PathVariable String id, @RequestParam String value) {
        boolean ok = mallRepository.updateCity(id, value);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}