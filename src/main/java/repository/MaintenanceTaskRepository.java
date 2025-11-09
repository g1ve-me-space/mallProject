package repository;

import model.MaintenanceTask;
import model.TaskStatus; // Import the enum

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// No longer abstract
public class MaintenanceTaskRepository {

    protected final Map<String, MaintenanceTask> store = new ConcurrentHashMap<>();

    // --- Basic CRUD Methods ---

    public List<MaintenanceTask> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<MaintenanceTask> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public void save(MaintenanceTask item) {
        if (item.getId() == null || item.getId().isEmpty()) {
            throw new IllegalArgumentException("Entity ID cannot be null or empty.");
        }
        store.put(item.getId(), item);
    }

    public void delete(String id) {
        // This will be implemented in the AppConfig @Bean definition
        throw new UnsupportedOperationException();
    }

    // --- Your Custom Methods (Updated to use TaskStatus enum) ---

    // Find tasks by status
    public List<MaintenanceTask> findByStatus(TaskStatus status) {
        return store.values().stream()
                .filter(task -> task.getStatus() == status) // Direct enum comparison
                .collect(Collectors.toList());
    }

    // Find planned tasks
    public List<MaintenanceTask> findPlannedTasks() {
        return findByStatus(TaskStatus.PLANNED); // Use enum value
    }

    // Find active tasks
    public List<MaintenanceTask> findActiveTasks() {
        return findByStatus(TaskStatus.ACTIVE); // Use enum value
    }

    // Find completed tasks
    public List<MaintenanceTask> findCompletedTasks() {
        return findByStatus(TaskStatus.DONE); // Use enum value
    }

    // Find tasks by assignment ID (No changes needed here)
    public List<MaintenanceTask> findByAssignmentId(String assignmentId) {
        return store.values().stream()
                .filter(task -> task.getAssignmentId() != null && task.getAssignmentId().equals(assignmentId))
                .collect(Collectors.toList());
    }

    // Find unassigned tasks (No changes needed here)
    public List<MaintenanceTask> findUnassignedTasks() {
        return store.values().stream()
                .filter(task -> task.getAssignmentId() == null || task.getAssignmentId().trim().isEmpty())
                .collect(Collectors.toList());
    }

    // Update task status
    public boolean updateStatus(String taskId, TaskStatus newStatus) { // Parameter is now TaskStatus
        Optional<MaintenanceTask> taskOpt = findById(taskId);
        if (taskOpt.isPresent()) {
            MaintenanceTask task = taskOpt.get();
            task.setStatus(newStatus); // Set with enum
            save(task);
            return true;
        }
        return false;
    }

    // Assign task to assignment (No changes needed here)
    public boolean assignTask(String taskId, String assignmentId) {
        Optional<MaintenanceTask> taskOpt = findById(taskId);
        if (taskOpt.isPresent()) {
            MaintenanceTask task = taskOpt.get();
            task.setAssignmentId(assignmentId);
            save(task);
            return true;
        }
        return false;
    }

    // Unassign task
    public boolean unassignTask(String taskId) {
        return assignTask(taskId, null);
    }

    // Mark task as planned
    public boolean markAsPlanned(String taskId) {
        return updateStatus(taskId, TaskStatus.PLANNED); // Use enum value
    }

    // Mark task as active
    public boolean markAsActive(String taskId) {
        return updateStatus(taskId, TaskStatus.ACTIVE); // Use enum value
    }

    // Mark task as done
    public boolean markAsDone(String taskId) {
        return updateStatus(taskId, TaskStatus.DONE); // Use enum value
    }

    // Get task counts by status
    public Map<TaskStatus, Long> getTaskCountsByStatus() { // Return type is now Map<TaskStatus, Long>
        return store.values().stream()
                .filter(task -> task.getStatus() != null)
                .collect(Collectors.groupingBy(
                        MaintenanceTask::getStatus,
                        Collectors.counting()
                ));
    }

    // Find tasks with description containing keyword (No changes needed here)
    public List<MaintenanceTask> findByDescriptionContaining(String keyword) {
        return store.values().stream()
                .filter(task -> task.getDescription() != null &&
                        task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}