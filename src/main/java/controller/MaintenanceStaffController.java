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

    @GetMapping
    public String listAll(Model model) {
        List<MaintenanceStaff> staffList = staffRepository.findAll();
        model.addAttribute("staffList", staffList);
        return "maintenance-staff/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new MaintenanceStaff());
        return "maintenance-staff/form";
    }

    @PostMapping
    public String create(@ModelAttribute MaintenanceStaff staff) {
        staff.setId(UUID.randomUUID().toString());
        staffRepository.save(staff);
        return "redirect:/maintenance-staff";
    }

    /**
     * FIX: The method call is changed from .delete(id) to .deleteById(id)
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        staffRepository.deleteById(id);
        return "redirect:/maintenance-staff";
    }
}