package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @GetMapping
    public String getAllBudgets() {
        return "All budgets loaded.";
    }

    @PostMapping
    public String addBudget(@RequestBody Budget budget) {
        return "Budget added: " + budget.getLimitAmount();
    }
}
