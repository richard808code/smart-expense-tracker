package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(UUID id);

    void deleteCategoryById(UUID id);

    Category updateCategoryById(UUID id, Category category);
}
