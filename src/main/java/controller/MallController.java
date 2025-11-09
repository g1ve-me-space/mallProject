package controller;

import model.Mall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.MallRepository;

import java.util.List;
import java.util.UUID;

@Controller // Changed from @RestController
@RequestMapping("/mall") // Changed from /api/malls
public class MallController {

    private final MallRepository mallRepository;

    @Autowired
    public MallController(MallRepository mallRepository) {
        this.mallRepository = mallRepository;
    }

    /**
     * GET /mall
     * Displays a list of all malls.
     */
    @GetMapping
    public String listAllMalls(Model model) {
        List<Mall> malls = mallRepository.findAll();
        model.addAttribute("malls", malls);
        return "mall/index"; // Renders templates/mall/index.html
    }

    /**
     * GET /mall/new
     * Displays the form to create a new mall.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("mall", new Mall());
        return "mall/form"; // Renders templates/mall/form.html
    }

    /**
     * POST /mall
     * Processes the form submission for creating a new mall.
     */
    @PostMapping
    public String createMall(@ModelAttribute Mall mall) {
        mall.setId(UUID.randomUUID().toString());
        mallRepository.save(mall);
        return "redirect:/mall"; // Redirects to the list page
    }

    /**
     * POST /mall/{id}/delete
     * Deletes the specified mall.
     */
    @PostMapping("/{id}/delete")
    public String deleteMall(@PathVariable String id) {
        mallRepository.delete(id);
        return "redirect:/mall";
    }
}