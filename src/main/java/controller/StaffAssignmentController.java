package controller;

import model.Floor;
import model.Staff;
import model.StaffAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.FloorRepository;
import repository.MaintenanceStaffRepository;
import repository.SecurityStaffRepository;
import repository.StaffAssignmentRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/assignments")
public class StaffAssignmentController {

    private final StaffAssignmentRepository assignmentRepository;
    private final MaintenanceStaffRepository maintenanceStaffRepository;
    private final SecurityStaffRepository securityStaffRepository;
    private final FloorRepository floorRepository;

    @Autowired
    public StaffAssignmentController(StaffAssignmentRepository assignmentRepository,
                                     MaintenanceStaffRepository maintenanceStaffRepository,
                                     SecurityStaffRepository securityStaffRepository,
                                     FloorRepository floorRepository) {
        this.assignmentRepository = assignmentRepository;
        this.maintenanceStaffRepository = maintenanceStaffRepository;
        this.securityStaffRepository = securityStaffRepository;
        this.floorRepository = floorRepository;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("assignments", assignmentRepository.findAll());
        return "assignments/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        List<Staff> allStaff = new ArrayList<>();
        allStaff.addAll(maintenanceStaffRepository.findAll());
        allStaff.addAll(securityStaffRepository.findAll());
        allStaff.sort(Comparator.comparing(Staff::getName));

        List<Floor> allFloors = floorRepository.findAll();
        allFloors.sort(Comparator.comparing(Floor::getNumber));

        model.addAttribute("assignment", new StaffAssignment());
        model.addAttribute("allStaff", allStaff);
        model.addAttribute("allFloors", allFloors);

        return "assignments/form";
    }

    @PostMapping
    public String create(@ModelAttribute StaffAssignment assignment) {
        assignment.setId(UUID.randomUUID().toString());
        assignmentRepository.save(assignment);
        return "redirect:/assignments";
    }

    /**
     * FIX: The method call is now .deleteById(id) to match the new repository.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        assignmentRepository.deleteById(id);
        return "redirect:/assignments";
    }
}