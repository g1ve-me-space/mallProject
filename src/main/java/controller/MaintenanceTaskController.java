package controller;

import enums.TaskStatus;
import model.MaintenanceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.MaintenanceTaskRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * FIX: The entire controller is updated to match your HTML templates.
 * The base path is now "/maintenance".
 */
@Controller
@RequestMapping("/maintenance")
public class MaintenanceTaskController {

    private final MaintenanceTaskRepository maintenanceTaskRepository;

    @Autowired
    public MaintenanceTaskController(MaintenanceTaskRepository maintenanceTaskRepository) {
        this.maintenanceTaskRepository = maintenanceTaskRepository;
    }

    /**
     * GET /maintenance
     * Displays the list of all tasks. This now points to your list template.
     * What is your list file named? If it's 'maintenance-list.html', change the return value to "maintenance-list".
     */
    @GetMapping
    public String listAllTasks(Model model) {
        List<MaintenanceTask> tasks = maintenanceTaskRepository.findAll();
        model.addAttribute("tasks", tasks);
        // Assuming your list HTML file is named 'maintenance/index.html' or similar
        // Please tell me the exact name of your list file.
        return "maintenance/index";
    }

    /**
     * GET /maintenance/new
     * Displays the form to create a new task.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new MaintenanceTask());
        // This makes the dropdown work by sending all possible statuses to the form.
        model.addAttribute("allStatuses", Arrays.asList(TaskStatus.values()));
        // Assuming your form HTML file is named 'maintenance/form.html' or similar
        return "maintenance/form";
    }

    /**
     * POST /maintenance
     * Processes the form submission for creating a new task.
     */
    @PostMapping
    public String createTask(@ModelAttribute MaintenanceTask task) {
        task.setId(UUID.randomUUID().toString());
        maintenanceTaskRepository.save(task);
        return "redirect:/maintenance"; // Redirects back to the list page
    }

    /**
     * POST /maintenance/{id}/delete
     * Deletes the specified task. The path and redirect are now correct.
     */
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable String id) {
        maintenanceTaskRepository.deleteById(id);
        return "redirect:/maintenance";
    }
}