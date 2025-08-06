package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import cz.smartexpensetracker.smart_expense_tracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @Override
    public Budget getBudgetById(UUID id) {
        return budgetRepository.findById(id).orElse(null);
    }

    @Override
    public List<Budget> getBudgetsByUserId(UUID userId) {
        return budgetRepository.findByUserId(userId);
    }

    @Override
    public void deleteBudgetById(UUID id) {
        budgetRepository.deleteById(id);
    }

    @Override
    public Budget updateBudgetById(UUID id, Budget updatedBudget) {
        return budgetRepository.findById(id)
                .map(existing -> {
                    existing.setLimitAmount(updatedBudget.getLimitAmount());
                    existing.setCategory(updatedBudget.getCategory());
                    return budgetRepository.save(existing);
                })
                .orElse(null);
    }
}