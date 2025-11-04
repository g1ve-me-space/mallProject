package controller;

import model.StaffAssignment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.StaffAssignmentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/assignments")
public class StaffAssignmentController {

    private final StaffAssignmentRepository assignmentRepository;

    public StaffAssignmentController(StaffAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<StaffAssignment>> listAll() {
        return ResponseEntity.ok(assignmentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffAssignment> getById(@PathVariable String id) {
        Optional<StaffAssignment> a = assignmentRepository.findById(id);
        return a.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/byFloor/{floorId}")
    public ResponseEntity<List<StaffAssignment>> byFloor(@PathVariable String floorId) {
        return ResponseEntity.ok(assignmentRepository.findByFloorId(floorId));
    }

    @GetMapping("/byStaff/{staffId}")
    public ResponseEntity<List<StaffAssignment>> byStaff(@PathVariable String staffId) {
        return ResponseEntity.ok(assignmentRepository.findByStaffId(staffId));
    }

    @PatchMapping("/{id}/shift")
    public ResponseEntity<?> updateShift(@PathVariable String id, @RequestParam String shift) {
        boolean ok = assignmentRepository.updateShift(id, shift);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/floor")
    public ResponseEntity<?> updateFloor(@PathVariable String id, @RequestParam String floorId) {
        boolean ok = assignmentRepository.updateFloor(id, floorId);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/countsByFloor")
    public ResponseEntity<Map<String, Long>> countsByFloor() {
        return ResponseEntity.ok(assignmentRepository.getAssignmentCountByFloor());
    }

    @GetMapping("/countsByShift")
    public ResponseEntity<Map<String, Long>> countsByShift() {
        return ResponseEntity.ok(assignmentRepository.getAssignmentCountByShift());
    }

    @GetMapping("/staffWithMultiple")
    public ResponseEntity<Map<String, Long>> staffWithMultiple() {
        return ResponseEntity.ok(assignmentRepository.findStaffWithMultipleAssignments());
    }

    @GetMapping("/isAssigned")
    public ResponseEntity<Boolean> isAssigned(@RequestParam String staffId, @RequestParam String floorId, @RequestParam String shift) {
        boolean assigned = assignmentRepository.isStaffAssignedToFloor(staffId, floorId, shift);
        return ResponseEntity.ok(assigned);
    }

    @GetMapping("/allStaffIds")
    public ResponseEntity<Set<String>> allStaffIds() {
        return ResponseEntity.ok(assignmentRepository.getAllStaffIdsWithAssignments());
    }
}