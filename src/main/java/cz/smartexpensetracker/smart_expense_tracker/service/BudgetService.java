package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;

import java.util.List;
import java.util.UUID;

public interface BudgetService {

    Budget createBudget(Budget budget);

    List<Budget> getAllBudgets();

    Budget getBudgetById(UUID id);

    List<Budget> getBudgetsByUserId(UUID userId);

    void deleteBudgetById(UUID id);

    Budget updateBudgetById(UUID id, Budget updatedBudget);
}
