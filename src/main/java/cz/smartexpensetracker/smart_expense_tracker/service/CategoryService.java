package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    // Creates a new category and returns it
    Category createCategory(Category category);

    // Returns all categories
    List<Category> getAllCategories();

    // Finds category by its ID, returns null if not found
    Category getCategoryById(UUID id);

    // Returns all categories belonging to a specific user
    List<Category> getCategoriesByUserId(UUID userId);

    // Deletes category by its ID
    void deleteCategoryById(UUID id);

    // Updates category by ID and returns the updated entity or null if not found
    Category updateCategoryById(UUID id, Category category);
}