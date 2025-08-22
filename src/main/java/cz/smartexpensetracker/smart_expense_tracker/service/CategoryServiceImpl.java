package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import cz.smartexpensetracker.smart_expense_tracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Saves a new category to the database
    @Override
    public Category createCategory(final Category category) {
        return categoryRepository.save(category);
    }

    // Returns all categories
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Finds a category by its ID, returns null if not found
    @Override
    public Category getCategoryById(final UUID id){
        return categoryRepository.findById(id).orElse(null);
    }

    // Returns all categories for a given user ID
    @Override
    public List<Category> getCategoriesByUserId(final UUID userId) {
        return categoryRepository.findByUserId(userId);
    }

    // Deletes a category by its ID
    @Override
    public void deleteCategoryById(final UUID id) {
        categoryRepository.deleteById(id);
    }

    // Updates the name of an existing category and saves it, or returns null if not found
    @Override
    public Category updateCategoryById(final UUID id, final Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedCategory.getName());
                    return categoryRepository.save(existing);
                })
                .orElse(null);
    }
}