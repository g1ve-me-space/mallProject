package controller;

import jakarta.validation.Valid; // Import
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Import
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
    public String listAll(Model model) {
        model.addAttribute("assignments", assignmentRepository.findAll());
        return "assignments/index";
    }

    // Metodă ajutătoare pentru a repopula listele în caz de eroare
    private void populateModel(Model model) {
        List<Staff> allStaff = new ArrayList<>();
        allStaff.addAll(maintenanceStaffRepository.findAll());
        allStaff.addAll(securityStaffRepository.findAll());
        allStaff.sort(Comparator.comparing(Staff::getName));

        List<Floor> allFloors = floorRepository.findAll();
        allFloors.sort(Comparator.comparing(Floor::getNumber));

        model.addAttribute("allStaff", allStaff);
        model.addAttribute("allFloors", allFloors);
        model.addAttribute("allShifts", Shift.values());
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new StaffAssignment());
        populateModel(model);
        return "assignments/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("assignment") StaffAssignment assignment,
                         BindingResult bindingResult,
                         @RequestParam(value = "staffId", required = false) String staffId,
                         @RequestParam(value = "floorId", required = false) String floorId,
                         Model model) {

        // 1. Validare Manuală
        if (staffId == null || staffId.isEmpty()) {
            bindingResult.rejectValue("staff", "error.staff", "Trebuie să alegi un angajat!");
        }
        if (floorId == null || floorId.isEmpty()) {
            bindingResult.rejectValue("floor", "error.floor", "Trebuie să alegi un etaj!");
        }

        // 2. Verificare Erori
        if (bindingResult.hasErrors()) {
            populateModel(model); // Reîncărcăm listele
            return "assignments/form";
        }

        // 3. Salvare
        if (assignment.getId() == null || assignment.getId().isEmpty()) {
            assignment.setId(UUID.randomUUID().toString());
        }

        Floor f = floorRepository.findById(floorId).orElse(null);
        assignment.setFloor(f);

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

    // Add these two methods into your existing StaffAssignmentController class (paste inside the class body)

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        var opt = assignmentRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/assignments";
        }
        StaffAssignment assignment = opt.get();
        model.addAttribute("assignment", assignment);
        // populate the supporting lists (staff, floors, shifts)
        populateModel(model);
        return "assignments/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("assignment") StaffAssignment assignment,
                         BindingResult bindingResult,
                         @RequestParam(value = "staffId", required = false) String staffId,
                         @RequestParam(value = "floorId", required = false) String floorId,
                         Model model) {

        // Manual validation for selects (same as create)
        if (staffId == null || staffId.isEmpty()) {
            bindingResult.rejectValue("staff", "error.staff", "Trebuie să alegi un angajat!");
        }
        if (floorId == null || floorId.isEmpty()) {
            bindingResult.rejectValue("floor", "error.floor", "Trebuie să alegi un etaj!");
        }

        if (bindingResult.hasErrors()) {
            populateModel(model);
            return "assignments/form";
        }

        // ensure path id is the source of truth
        assignment.setId(id);

        // set floor
        Floor f = floorRepository.findById(floorId).orElse(null);
        assignment.setFloor(f);

        // resolve staff from maintenance or security repositories
        Staff s = maintenanceStaffRepository.findById(staffId)
                .map(Staff.class::cast)
                .orElse(securityStaffRepository.findById(staffId).orElse(null));
        assignment.setStaff(s);

        assignmentRepository.save(assignment);
        return "redirect:/assignments";
    }
}