package controller;

import model.SecurityStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.SecurityStaffRepository;

import java.util.List;
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
        List<SecurityStaff> staffList = securityRepository.findAll();
        model.addAttribute("staffList", staffList);
        return "security-staff/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new SecurityStaff());
        return "security-staff/form";
    }

    @PostMapping
    public String create(@ModelAttribute SecurityStaff staff) {
        staff.setId(UUID.randomUUID().toString());
        securityRepository.save(staff);
        return "redirect:/security-staff";
    }

    /**
     * FIX: The method call is changed from .delete(id) to .deleteById(id)
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        securityRepository.deleteById(id);
        return "redirect:/security-staff";
    }
}