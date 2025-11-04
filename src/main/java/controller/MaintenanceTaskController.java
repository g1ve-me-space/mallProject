package controller;

import model.MaintenanceTask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.MaintenanceTaskRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/maintenance-tasks")
public class MaintenanceTaskController {

    private final MaintenanceTaskRepository taskRepository;

    public MaintenanceTaskController(MaintenanceTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceTask>> listAll() {
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceTask> getById(@PathVariable String id) {
        Optional<MaintenanceTask> t = taskRepository.findById(id);
        return t.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<MaintenanceTask>> byStatus(@RequestParam String status) {
        return ResponseEntity.ok(taskRepository.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<MaintenanceTask> create(@RequestBody MaintenanceTask task) {
        taskRepository.save(task);
        return ResponseEntity.status(201).body(task);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestParam String status) {
        boolean ok = taskRepository.updateStatus(id, status);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignTask(@PathVariable String id, @RequestParam String assignmentId) {
        boolean ok = taskRepository.assignTask(id, assignmentId);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/unassign")
    public ResponseEntity<?> unassignTask(@PathVariable String id) {
        boolean ok = taskRepository.unassignTask(id);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> statistics() {
        return ResponseEntity.ok(taskRepository.getTaskCountsByStatus());
    }
}