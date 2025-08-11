package cz.smartexpensetracker.smart_expense_tracker.repository;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // Finds all categories belonging to a specific user by user ID
    List<Category> findByUserId(UUID userId);
}