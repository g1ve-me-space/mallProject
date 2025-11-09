package controller;

import model.SecurityStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.SecurityStaffRepository;

import java.util.List;
import java.util.UUID;

@Controller // Changed from @RestController
@RequestMapping("/security-staff") // Changed from /api/security-staff
public class SecurityStaffController {

    private final SecurityStaffRepository securityRepository;

    @Autowired
    public SecurityStaffController(SecurityStaffRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    /**
     * GET /security-staff
     * Displays a list of all security staff.
     */
    @GetMapping
    public String listAll(Model model) {
        List<SecurityStaff> staffList = securityRepository.findAll();
        model.addAttribute("staffList", staffList);
        return "security-staff/index"; // Renders the index.html template
    }

    /**
     * GET /security-staff/new
     * Displays the form to create a new security staff member.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new SecurityStaff());
        return "security-staff/form"; // Renders the form.html template
    }

    /**
     * POST /security-staff
     * Processes the form submission for creating a new staff member.
     */
    @PostMapping
    public String create(@ModelAttribute SecurityStaff staff) {
        staff.setId(UUID.randomUUID().toString());
        securityRepository.save(staff);
        return "redirect:/security-staff"; // Redirects back to the list
    }

    /**
     * POST /security-staff/{id}/delete
     * Deletes the specified staff member.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        securityRepository.delete(id);
        return "redirect:/security-staff";
    }
}