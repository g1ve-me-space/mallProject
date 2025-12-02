package controller;

import jakarta.validation.Valid;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import repository.*;
import enums.Shift;
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
    public String listAll(Model model,
                          @RequestParam(value = "floorId", required = false) String floorId,
                          @RequestParam(value = "staffId", required = false) String staffId) {

        populateModel(model); // Pentru filtre

        List<StaffAssignment> results;
        if (floorId != null && !floorId.isEmpty() && staffId != null && !staffId.isEmpty()) {
            results = assignmentRepository.findByFloor_IdAndStaff_Id(floorId, staffId);
        } else if (floorId != null && !floorId.isEmpty()) {
            results = assignmentRepository.findByFloor_Id(floorId);
        } else if (staffId != null && !staffId.isEmpty()) {
            results = assignmentRepository.findByStaff_Id(staffId);
        } else {
            results = assignmentRepository.findAll();
        }

        model.addAttribute("assignments", results);
        model.addAttribute("selFloor", floorId);
        model.addAttribute("selStaff", staffId);
        return "assignments/index";
    }

    private void populateModel(Model model) {
        List<Staff> allStaff = new ArrayList<>();
        allStaff.addAll(maintenanceStaffRepository.findAll());
        allStaff.addAll(securityStaffRepository.findAll());
        allStaff.sort(Comparator.comparing(Staff::getName));
        model.addAttribute("allStaff", allStaff);

        List<Floor> allFloors = floorRepository.findAll();
        allFloors.sort(Comparator.comparing(Floor::getNumber));
        model.addAttribute("allFloors", allFloors);

        model.addAttribute("allShifts", Shift.values());
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new StaffAssignment());
        populateModel(model);
        return "assignments/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        StaffAssignment assignment = assignmentRepository.findById(id).orElse(null);
        if (assignment != null) {
            model.addAttribute("assignment", assignment);
            populateModel(model);
            return "assignments/form";
        }
        return "redirect:/assignments";
    }

    @PostMapping
    public String createOrUpdate(@Valid @ModelAttribute("assignment") StaffAssignment assignment,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "staffId", required = false) String staffId,
                                 @RequestParam(value = "floorId", required = false) String floorId,
                                 Model model) {

        if (staffId == null || staffId.isEmpty()) bindingResult.rejectValue("staff", "error.staff", "Required!");
        if (floorId == null || floorId.isEmpty()) bindingResult.rejectValue("floor", "error.floor", "Required!");

        if (bindingResult.hasErrors()) {
            populateModel(model);
            return "assignments/form";
        }

        if (assignment.getId() == null || assignment.getId().isEmpty()) {
            assignment.setId(UUID.randomUUID().toString());
        }

        assignment.setFloor(floorRepository.findById(floorId).orElse(null));
        Staff s = maintenanceStaffRepository.findById(staffId)
                .map(Staff.class::cast)
                .orElse(securityStaffRepository.findById(staffId).orElse(null));
        assignment.setStaff(s);

        assignmentRepository.save(assignment);
        return "redirect:/assignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        assignmentRepository.deleteById(id);
        return "redirect:/assignments";
    }
}