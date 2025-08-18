package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;

import java.util.List;
import java.util.UUID;

public interface BudgetService {

    // Creates a new budget entity
    Budget createBudget(Budget budget);

    // Retrieves all budgets from the system
    List<Budget> getAllBudgets();

    // Retrieves a budget by its unique ID
    Budget getBudgetById(UUID id);



    // Retrieves all budgets for a user including their associated category details
    List<Budget> getBudgetsByUserIdWithCategory(UUID userId);

    // Deletes a budget by its unique ID
    void deleteBudgetById(UUID id);

    // Updates an existing budget identified by ID with new data
    Budget updateBudgetById(UUID id, Budget updatedBudget);

    // Retrieves budgets for a user along with calculated remaining amounts
    List<Budget> getBudgetsWithRemainingForUser(UUID userId);
}