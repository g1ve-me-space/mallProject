package repository;

import model.StaffAssignment;
import java.util.*;
import java.util.stream.Collectors;

public class StaffAssignmentRepository extends AbstractRepository<StaffAssignment> {

    // Find assignments by floor ID
    public List<StaffAssignment> findByFloorId(String floorId) {
        return store.values().stream()
                .filter(assignment -> assignment.getFloorId() != null && assignment.getFloorId().equals(floorId))
                .collect(Collectors.toList());
    }

    // Find assignments by staff ID
    public List<StaffAssignment> findByStaffId(String staffId) {
        return store.values().stream()
                .filter(assignment -> assignment.getStaffId() != null && assignment.getStaffId().equals(staffId))
                .collect(Collectors.toList());
    }

    // Find assignments by shift
    public List<StaffAssignment> findByShift(String shift) {
        return store.values().stream()
                .filter(assignment -> assignment.getShift() != null && assignment.getShift().equalsIgnoreCase(shift))
                .collect(Collectors.toList());
    }

    // Find morning shift assignments
    public List<StaffAssignment> findMorningAssignments() {
        return findByShift("Morning");
    }

    // Find evening shift assignments
    public List<StaffAssignment> findEveningAssignments() {
        return findByShift("Evening");
    }

    // Find night shift assignments
    public List<StaffAssignment> findNightAssignments() {
        return findByShift("Night");
    }

    // Find assignments by floor and shift
    public List<StaffAssignment> findByFloorAndShift(String floorId, String shift) {
        return store.values().stream()
                .filter(assignment -> assignment.getFloorId() != null && assignment.getFloorId().equals(floorId) &&
                        assignment.getShift() != null && assignment.getShift().equalsIgnoreCase(shift))
                .collect(Collectors.toList());
    }

    // Update assignment shift
    public boolean updateShift(String assignmentId, String newShift) {
        Optional<StaffAssignment> assignmentOpt = findById(assignmentId);
        if (assignmentOpt.isPresent()) {
            StaffAssignment assignment = assignmentOpt.get();
            assignment.setShift(newShift);
            save(assignment.getId(), assignment);
            return true;
        }
        return false;
    }

    // Update assignment floor
    public boolean updateFloor(String assignmentId, String newFloorId) {
        Optional<StaffAssignment> assignmentOpt = findById(assignmentId);
        if (assignmentOpt.isPresent()) {
            StaffAssignment assignment = assignmentOpt.get();
            assignment.setFloorId(newFloorId);
            save(assignment.getId(), assignment);
            return true;
        }
        return false;
    }

    // Get staff assignments count by floor
    public Map<String, Long> getAssignmentCountByFloor() {
        return store.values().stream()
                .filter(assignment -> assignment.getFloorId() != null)
                .collect(Collectors.groupingBy(
                        StaffAssignment::getFloorId,
                        Collectors.counting()
                ));
    }

    // Get staff assignments count by shift
    public Map<String, Long> getAssignmentCountByShift() {
        return store.values().stream()
                .filter(assignment -> assignment.getShift() != null)
                .collect(Collectors.groupingBy(
                        StaffAssignment::getShift,
                        Collectors.counting()
                ));
    }

    // Find staff with multiple assignments
    public Map<String, Long> findStaffWithMultipleAssignments() {
        return store.values().stream()
                .filter(assignment -> assignment.getStaffId() != null)
                .collect(Collectors.groupingBy(
                        StaffAssignment::getStaffId,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    // Check if staff is assigned to a floor during a shift
    public boolean isStaffAssignedToFloor(String staffId, String floorId, String shift) {
        return store.values().stream()
                .anyMatch(assignment -> assignment.getStaffId() != null && assignment.getStaffId().equals(staffId) &&
                        assignment.getFloorId() != null && assignment.getFloorId().equals(floorId) &&
                        assignment.getShift() != null && assignment.getShift().equalsIgnoreCase(shift));
    }

    // Get all staff IDs with assignments
    public Set<String> getAllStaffIdsWithAssignments() {
        return store.values().stream()
                .filter(assignment -> assignment.getStaffId() != null)
                .map(StaffAssignment::getStaffId)
                .collect(Collectors.toSet());
    }

    // Get all floor IDs with assignments
    public Set<String> getAllFloorIdsWithAssignments() {
        return store.values().stream()
                .filter(assignment -> assignment.getFloorId() != null)
                .map(StaffAssignment::getFloorId)
                .collect(Collectors.toSet());
    }

    // Override save method to ensure ID consistency
    @Override
    public void save(String id, StaffAssignment entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    @Override
    public void delete(String id) {

    }

    // Alternative save method that uses the entity's own ID
    public void save(StaffAssignment entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("StaffAssignment ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}