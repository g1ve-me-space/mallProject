package repository;

import model.MaintenanceTask;
import java.util.*;
import java.util.stream.Collectors;

public abstract class MaintenanceTaskRepository extends AbstractRepository<MaintenanceTask> {

    // Find tasks by status
    public List<MaintenanceTask> findByStatus(String status) {
        return store.values().stream()
                .filter(task -> task.getStatus() != null && task.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    // Find planned tasks
    public List<MaintenanceTask> findPlannedTasks() {
        return findByStatus("Planned");
    }

    // Find active tasks
    public List<MaintenanceTask> findActiveTasks() {
        return findByStatus("Active");
    }

    // Find completed tasks
    public List<MaintenanceTask> findCompletedTasks() {
        return findByStatus("Done");
    }

    // Find tasks by assignment ID
    public List<MaintenanceTask> findByAssignmentId(String assignmentId) {
        return store.values().stream()
                .filter(task -> task.getAssignmentId() != null && task.getAssignmentId().equals(assignmentId))
                .collect(Collectors.toList());
    }

    // Find unassigned tasks (no assignment ID)
    public List<MaintenanceTask> findUnassignedTasks() {
        return store.values().stream()
                .filter(task -> task.getAssignmentId() == null || task.getAssignmentId().trim().isEmpty())
                .collect(Collectors.toList());
    }

    // Update task status
    public boolean updateStatus(String taskId, String newStatus) {
        Optional<MaintenanceTask> taskOpt = findById(taskId);
        if (taskOpt.isPresent()) {
            MaintenanceTask task = taskOpt.get();
            task.setStatus(newStatus);
            save(task.getId(), task);
            return true;
        }
        return false;
    }

    // Assign task to assignment
    public boolean assignTask(String taskId, String assignmentId) {
        Optional<MaintenanceTask> taskOpt = findById(taskId);
        if (taskOpt.isPresent()) {
            MaintenanceTask task = taskOpt.get();
            task.setAssignmentId(assignmentId);
            save(task.getId(), task);
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
        return updateStatus(taskId, "Planned");
    }

    // Mark task as active
    public boolean markAsActive(String taskId) {
        return updateStatus(taskId, "Active");
    }

    // Mark task as done
    public boolean markAsDone(String taskId) {
        return updateStatus(taskId, "Done");
    }

    // Get task counts by status
    public Map<String, Long> getTaskCountsByStatus() {
        return store.values().stream()
                .filter(task -> task.getStatus() != null)
                .collect(Collectors.groupingBy(
                        MaintenanceTask::getStatus,
                        Collectors.counting()
                ));
    }

    // Find tasks with description containing keyword
    public List<MaintenanceTask> findByDescriptionContaining(String keyword) {
        return store.values().stream()
                .filter(task -> task.getDescription() != null &&
                        task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, MaintenanceTask entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    // Alternative save method that uses the entity's own ID
    public void save(MaintenanceTask entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("MaintenanceTask ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}