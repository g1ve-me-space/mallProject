package controller;

import model.SecurityStaff;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.SecurityStaffRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/security-staff")
public class SecurityStaffController {

    private final SecurityStaffRepository securityRepository;

    public SecurityStaffController(SecurityStaffRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @GetMapping
    public ResponseEntity<List<SecurityStaff>> listAll() {
        return ResponseEntity.ok(securityRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityStaff> getById(@PathVariable String id) {
        Optional<SecurityStaff> s = securityRepository.findById(id);
        return s.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SecurityStaff> create(@RequestBody SecurityStaff staff) {
        securityRepository.save(staff);
        return ResponseEntity.status(201).body(staff);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        securityRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}