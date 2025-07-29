package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping
    public String getAllCategories() {
        return "All categories loaded.";
    }

    @PostMapping
    public String addCategory(@RequestBody Category category) {
        return "Category added: " + category.getName();
    }
}
