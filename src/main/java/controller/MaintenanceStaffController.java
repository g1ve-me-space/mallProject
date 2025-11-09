package controller;

import model.MaintenanceStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.MaintenanceStaffRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/maintenance-staff")
public class MaintenanceStaffController {

    private final MaintenanceStaffRepository staffRepository;

    @Autowired
    public MaintenanceStaffController(MaintenanceStaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    /**
     * GET /maintenance-staff
     * Displays a list of all maintenance staff.
     */
    @GetMapping
    public String listAll(Model model) {
        List<MaintenanceStaff> staffList = staffRepository.findAll();
        model.addAttribute("staffList", staffList);
        return "maintenance-staff/index";
    }

    /**
     * GET /maintenance-staff/new
     * Displays the form to create a new maintenance staff member.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new MaintenanceStaff());
        return "maintenance-staff/form";
    }

    /**
     * POST /maintenance-staff
     * Processes the form submission for creating a new staff member.
     */
    @PostMapping
    public String create(@ModelAttribute MaintenanceStaff staff) {
        staff.setId(UUID.randomUUID().toString());
        staffRepository.save(staff);
        return "redirect:/maintenance-staff";
    }

    /**
     * POST /maintenance-staff/{id}/delete
     * Deletes the specified staff member.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        staffRepository.delete(id);
        return "redirect:/maintenance-staff";
    }
}