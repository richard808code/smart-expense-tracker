package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;
import cz.smartexpensetracker.smart_expense_tracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(final BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    // Saves a new budget to the repository
    @Override
    public Budget createBudget(final Budget budget) {
        return budgetRepository.save(budget);
    }

    // Retrieves all budgets from the repository
    @Override
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    // Retrieves a budget by its UUID, or returns null if not found
    @Override
    public Budget getBudgetById(final UUID id) {
        return budgetRepository.findById(id).orElse(null);
    }

    // Finds budgets by user ID including their categories in the query
    @Override
    public List<Budget> getBudgetsByUserIdWithCategory(final UUID userId) {
        return budgetRepository.findByUserIdWithCategory(userId);
    }

    // Deletes a budget by its UUID
    @Override
    public void deleteBudgetById(final UUID id) {
        budgetRepository.deleteById(id);
    }

    // Updates an existing budget by ID with new values, returns updated budget or null if not found
    @Override
    public Budget updateBudgetById(final UUID id, final Budget updatedBudget) {
        return budgetRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedBudget.getName());
                    existing.setLimitAmount(updatedBudget.getLimitAmount());
                    existing.setCategory(updatedBudget.getCategory());
                    return budgetRepository.save(existing);
                })
                .orElse(null);
    }

    // Retrieves budgets for a user including their transactions and calculates
    // the remaining amount per budget (limit - spent)
    // @param userId UUID of the user
    // @return List of budgets with the remaining amount set
    @Override
    public List<Budget> getBudgetsWithRemainingForUser(final UUID userId) {
        final List<Budget> budgets = budgetRepository.findByUserIdWithTransactions(userId);

        for (final Budget budget : budgets) {
            final BigDecimal totalSpent = budget.getTransactions().stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            final BigDecimal remaining = budget.getLimitAmount().subtract(totalSpent);
            budget.setRemainingAmount(remaining);
        }

        return budgets;
    }
}