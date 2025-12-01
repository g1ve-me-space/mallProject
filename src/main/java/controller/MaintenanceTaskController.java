package controller;

import jakarta.validation.Valid; // Import
import model.Floor;
import model.MaintenanceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Import
import org.springframework.web.bind.annotation.*;
import repository.FloorRepository;
import repository.MaintenanceTaskRepository;

import java.util.UUID;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceTaskController {

    private final MaintenanceTaskRepository taskRepository;
    private final FloorRepository floorRepository;

    @Autowired
    public MaintenanceTaskController(MaintenanceTaskRepository taskRepository, FloorRepository floorRepository) {
        this.taskRepository = taskRepository;
        this.floorRepository = floorRepository;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "maintenance/index";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("task", new MaintenanceTask());
        model.addAttribute("floors", floorRepository.findAll());
        return "maintenance/form";
    }

    @PostMapping
    public String createTask(@Valid @ModelAttribute("task") MaintenanceTask task,
                             BindingResult bindingResult,
                             @RequestParam(value = "floorId", required = false) String floorId,
                             Model model) {

        // 1. Validare Manuală Dropdown
        if (floorId == null || floorId.isEmpty()) {
            bindingResult.rejectValue("floor", "error.floor", "Trebuie să selectezi un etaj!");
        }

        // 2. Verificare Erori
        if (bindingResult.hasErrors()) {
            // Retrimitem lista de etaje pentru a popula dropdown-ul
            model.addAttribute("floors", floorRepository.findAll());
            return "maintenance/form";
        }

        // 3. Salvare
        if (task.getId() == null || task.getId().isEmpty()) {
            task.setId(UUID.randomUUID().toString());
        }

        Floor floor = floorRepository.findById(floorId).orElse(null);
        task.setFloor(floor);

        taskRepository.save(task);
        return "redirect:/maintenance";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        taskRepository.deleteById(id);
        return "redirect:/maintenance";
    }
}