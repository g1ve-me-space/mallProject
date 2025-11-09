package controller;

import model.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.FloorService;

import java.util.List;
import java.util.UUID;

@Controller // Changed from @RestController
@RequestMapping("/floor") // Changed from /api/floors
public class FloorController {

    private final FloorService floorService;

    @Autowired
    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    /**
     * GET /floor
     * Displays a list of all floors.
     */
    @GetMapping
    public String listAllFloors(Model model) {
        List<Floor> floors = floorService.findAll();
        model.addAttribute("floors", floors);
        return "floor/index"; // Renders templates/floor/index.html
    }

    /**
     * GET /floor/new
     * Displays the form to create a new floor.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("floor", new Floor());
        return "floor/form"; // Renders templates/floor/form.html
    }

    /**
     * POST /floor
     * Processes the form submission for creating a new floor.
     */
    @PostMapping
    public String createFloor(@ModelAttribute Floor floor) {
        floor.setId(UUID.randomUUID().toString());
        floorService.save(floor);
        return "redirect:/floor"; // Redirects to the list page
    }

    /**
     * POST /floor/{id}/delete
     * Deletes the specified floor.
     */
    @PostMapping("/{id}/delete")
    public String deleteFloor(@PathVariable String id) {
        // You will need to implement deleteById in your FloorService/Repository
        floorService.deleteById(id);
        return "redirect:/floor";
    }
}