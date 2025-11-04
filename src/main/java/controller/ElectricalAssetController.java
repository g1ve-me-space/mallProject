package controller;

import model.ElectricalAsset;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ElectricalAssetRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/electricals")
public class ElectricalAssetController {

    private final ElectricalAssetRepository electricalRepository;

    public ElectricalAssetController(ElectricalAssetRepository electricalRepository) {
        this.electricalRepository = electricalRepository;
    }

    @GetMapping
    public ResponseEntity<List<ElectricalAsset>> listAll() {
        return ResponseEntity.ok(electricalRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectricalAsset> getById(@PathVariable String id) {
        Optional<ElectricalAsset> e = electricalRepository.findById(id);
        return e.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ElectricalAsset> create(@RequestBody ElectricalAsset ea) {
        electricalRepository.save(ea);
        return ResponseEntity.status(201).body(ea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        electricalRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}