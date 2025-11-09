package controller;

import model.MaintenanceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.MaintenanceTaskRepository;

import java.util.List;
import java.util.UUID;

@Controller // Changed from @RestController
@RequestMapping("/maintenance") // Changed from /api/maintenance-tasks
public class MaintenanceTaskController {

    private final MaintenanceTaskRepository taskRepository;

    @Autowired
    public MaintenanceTaskController(MaintenanceTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * GET /maintenance
     * Displays a list of all maintenance tasks.
     */
    @GetMapping
    public String listAllTasks(Model model) {
        List<MaintenanceTask> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "maintenance/index"; // Renders templates/maintenance/index.html
    }

    /**
     * GET /maintenance/new
     * Displays the form to create a new task.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        MaintenanceTask newTask = new MaintenanceTask();
        newTask.setStatus("Planned"); // Set a default status
        model.addAttribute("task", newTask);
        return "maintenance/form"; // Renders templates/maintenance/form.html
    }

    /**
     * POST /maintenance
     * Processes the form submission for creating a new task.
     */
    @PostMapping
    public String createTask(@ModelAttribute MaintenanceTask task) {
        task.setId(UUID.randomUUID().toString());
        taskRepository.save(task);
        return "redirect:/maintenance"; // Redirects to the list page
    }

    /**
     * POST /maintenance/{id}/delete
     * Deletes the specified task.
     */
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable String id) {
        taskRepository.delete(id);
        return "redirect:/maintenance";
    }
}