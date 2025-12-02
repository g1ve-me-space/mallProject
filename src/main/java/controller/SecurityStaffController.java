package controller;

import jakarta.validation.Valid;
import model.SecurityStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import repository.SecurityStaffRepository;

import java.util.UUID;

@Controller
@RequestMapping("/security-staff")
public class SecurityStaffController {

    private final SecurityStaffRepository securityRepository;

    @Autowired
    public SecurityStaffController(SecurityStaffRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("staffList", securityRepository.findAll());
        return "security-staff/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new SecurityStaff());
        return "security-staff/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("staff") SecurityStaff staff, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "security-staff/form";
        }

        if (staff.getId() == null || staff.getId().isEmpty()) {
            staff.setId(UUID.randomUUID().toString());
        }
        securityRepository.save(staff);
        return "redirect:/security-staff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        securityRepository.deleteById(id);
        return "redirect:/security-staff";
    }
}