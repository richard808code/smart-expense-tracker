package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import cz.smartexpensetracker.smart_expense_tracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable UUID id) {
        Budget budget = budgetService.getBudgetById(id);
        return budget != null ? ResponseEntity.ok(budget) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Budget> addBudget(@Valid @RequestBody Budget budget) {
        Budget createdBudget = budgetService.createBudget(budget);
        return ResponseEntity.status(201).body(createdBudget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudgetById(@PathVariable UUID id, @Valid @RequestBody Budget budget) {
        Budget updatedBudget = budgetService.updateBudgetById(id, budget);
        return updatedBudget != null ? ResponseEntity.ok(updatedBudget) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetById(@PathVariable UUID id) {
        budgetService.deleteBudgetById(id);
        return ResponseEntity.noContent().build();
    }
}
