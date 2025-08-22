package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import cz.smartexpensetracker.smart_expense_tracker.model.User;
import cz.smartexpensetracker.smart_expense_tracker.repository.UserRepository;
import cz.smartexpensetracker.smart_expense_tracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*") // Allow all origins for CORS (Cross-Origin Resource Sharing)
@RestController // Marks this class as a REST controller
@RequestMapping("/api/budgets") // Base URL path for all methods in this controller
public class BudgetController {

    private final BudgetService budgetService;
    private final UserRepository userRepository;

    // Constructor injection of dependencies (service and repository)
    public BudgetController(final BudgetService budgetService, final UserRepository userRepository) {
        this.budgetService = budgetService;
        this.userRepository = userRepository;
    }

    // GET endpoint returning list of all budgets
    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    // GET endpoint returning budget by its UUID, returns 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable final UUID id) {
        final Budget budget = budgetService.getBudgetById(id);
        return budget != null ? ResponseEntity.ok(budget) : ResponseEntity.notFound().build();
    }

    // POST endpoint to create a new budget for a specific user identified by userId
    @PostMapping(value = "/user/{userId}", consumes = "application/json")
    public ResponseEntity<Budget> addBudget(@PathVariable final UUID userId, @Valid @RequestBody final Budget budget) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        budget.setUser(user);
        final Budget createdBudget = budgetService.createBudget(budget);
        return ResponseEntity.status(201).body(createdBudget);
    }

    // GET endpoint returning budgets for a specific user, including their categories
    @GetMapping("/user/{userId}")
    public List<Budget> getBudgetsByUserId(@PathVariable final UUID userId) {
        return budgetService.getBudgetsByUserIdWithCategory(userId);
    }

    // PUT endpoint to update an existing budget by its UUID, returns 404 if budget not found
    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudgetById(@PathVariable final UUID id, @Valid @RequestBody final Budget budget) {
        final Budget updatedBudget = budgetService.updateBudgetById(id, budget);
        return updatedBudget != null ? ResponseEntity.ok(updatedBudget) : ResponseEntity.notFound().build();
    }

    // DELETE endpoint to delete a budget by its UUID, returns 204 No Content on success
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetById(@PathVariable final UUID id) {
        budgetService.deleteBudgetById(id);
        return ResponseEntity.noContent().build();
    }

    // GET endpoint returning budgets for a user with their remaining amounts calculated
    @GetMapping("/remaining")
    public ResponseEntity<List<Budget>> getRemainingBudgetsForUser(@RequestParam final UUID userId) {
        final List<Budget> budgets = budgetService.getBudgetsWithRemainingForUser(userId);
        return ResponseEntity.ok(budgets);
    }
}