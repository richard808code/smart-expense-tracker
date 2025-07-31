package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import cz.smartexpensetracker.smart_expense_tracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(UUID id){
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCategoryById(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategoryById(UUID id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedCategory.getName());
                    return categoryRepository.save(existing);
                })
                .orElse(null);
    }
}
