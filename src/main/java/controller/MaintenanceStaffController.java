package controller;

import jakarta.validation.Valid; // Import
import model.MaintenanceStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Import
import org.springframework.web.bind.annotation.*;
import repository.MaintenanceStaffRepository;
import enums.MaintenanceStaffType;

import java.util.List;
import java.util.Optional;
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
        model.addAttribute("staffList", staffRepository.findAll());
        return "maintenance-staff/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new MaintenanceStaff());
        model.addAttribute("allStaffTypes", MaintenanceStaffType.values());
        return "maintenance-staff/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("staff") MaintenanceStaff staff,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allStaffTypes", MaintenanceStaffType.values());
            return "maintenance-staff/form";
        }

        if (staff.getId() == null || staff.getId().isEmpty()) {
            staff.setId(UUID.randomUUID().toString());
        }
        staffRepository.save(staff);
        return "redirect:/maintenance-staff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        staffRepository.deleteById(id);
        return "redirect:/maintenance-staff";
    }

    // ---------- ADDED: show edit form ----------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<MaintenanceStaff> opt = staffRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/maintenance-staff";
        }
        model.addAttribute("staff", opt.get());
        model.addAttribute("allStaffTypes", MaintenanceStaffType.values());
        return "maintenance-staff/form";
    }

    // ---------- ADDED: handle edit submit ----------
    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("staff") MaintenanceStaff staff,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allStaffTypes", MaintenanceStaffType.values());
            return "maintenance-staff/form";
        }

        // ensure path id is the source of truth
        staff.setId(id);
        staffRepository.save(staff);
        return "redirect:/maintenance-staff";
    }
}