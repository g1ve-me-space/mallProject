package repository;

import enums.Shift;
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

    // Find assignments by shift (FIXED)
    public List<StaffAssignment> findByShift(Shift shift) { // Parameter is Shift enum
        return store.values().stream()
                .filter(assignment -> assignment.getShift() == shift) // Direct enum comparison
                .collect(Collectors.toList());
    }

    // Find morning shift assignments (FIXED)
    public List<StaffAssignment> findMorningAssignments() {
        return findByShift(Shift.MORNING);
    }

    // Find evening shift assignments (FIXED)
    public List<StaffAssignment> findEveningAssignments() {
        return findByShift(Shift.EVENING);
    }

    // Find night shift assignments (FIXED)
    public List<StaffAssignment> findNightAssignments() {
        return findByShift(Shift.NIGHT);
    }

    // Find assignments by floor and shift (FIXED)
    public List<StaffAssignment> findByFloorAndShift(String floorId, Shift shift) { // Parameter is Shift enum
        return store.values().stream()
                .filter(assignment -> assignment.getFloorId() != null && assignment.getFloorId().equals(floorId) &&
                        assignment.getShift() == shift) // Direct enum comparison
                .collect(Collectors.toList());
    }

    // Update assignment shift (FIXED)
    public boolean updateShift(String assignmentId, Shift newShift) { // Parameter is Shift enum
        Optional<StaffAssignment> assignmentOpt = findById(assignmentId);
        if (assignmentOpt.isPresent()) {
            StaffAssignment assignment = assignmentOpt.get();
            assignment.setShift(newShift); // Setter uses enum
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

    // Get staff assignments count by shift (FIXED)
    public Map<Shift, Long> getAssignmentCountByShift() { // Returns Map<Shift, Long>
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

    // Check if staff is assigned to a floor during a shift (FIXED)
    public boolean isStaffAssignedToFloor(String staffId, String floorId, Shift shift) { // Parameter is Shift enum
        return store.values().stream()
                .anyMatch(assignment -> assignment.getStaffId() != null && assignment.getStaffId().equals(staffId) &&
                        assignment.getFloorId() != null && assignment.getFloorId().equals(floorId) &&
                        assignment.getShift() == shift); // Direct enum comparison
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

    @Override
    public void save(String id, StaffAssignment entity) {
        if (!id.equals(entity.getId())) {
            entity.setId(id);
        }
        super.save(id, entity);
    }

    @Override
    public void delete(String id) {
        // Implemented in AppConfig
    }

    public void save(StaffAssignment entity) {
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("StaffAssignment ID cannot be null or empty");
        }
        super.save(entity.getId(), entity);
    }
}