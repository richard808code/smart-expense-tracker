package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import cz.smartexpensetracker.smart_expense_tracker.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*") // Allow all origins for CORS
@RestController // Marks this class as a REST controller
@RequestMapping("/api/categories") // Base URL path for this controller
public class CategoryController {

    private final CategoryService categoryService;

    // Constructor injection of CategoryService dependency
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET endpoint to return list of all categories
    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    // GET endpoint to return a category by UUID, 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable final UUID id) {
        final Category category = categoryService.getCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    // GET endpoint to return categories belonging to a specific user by userId
    @GetMapping("/user/{userId}")
    public List<Category> getCategoriesByUserId(@PathVariable final UUID userId) {
        return categoryService.getCategoriesByUserId(userId);
    }

    // POST endpoint to create a new category
    @PostMapping
    public ResponseEntity<Category> addCategory(@Valid @RequestBody final Category category) {
        final Category createdCategory = categoryService.createCategory(category);
        return createdCategory != null ? ResponseEntity.status(201).body(createdCategory) : ResponseEntity.notFound().build();
    }

    // PUT endpoint to update an existing category by UUID, 404 if not found
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategoryById(@PathVariable final UUID id, @Valid @RequestBody final Category category) {
        final Category updatedCategory = categoryService.updateCategoryById(id, category);
        return updatedCategory != null ? ResponseEntity.ok(updatedCategory) : ResponseEntity.notFound().build();
    }

    // DELETE endpoint to remove a category by UUID, returns 204 No Content on success
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable final UUID id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}