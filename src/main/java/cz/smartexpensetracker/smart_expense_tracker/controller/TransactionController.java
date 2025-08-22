package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;
import cz.smartexpensetracker.smart_expense_tracker.model.User;
import cz.smartexpensetracker.smart_expense_tracker.repository.BudgetRepository;
import cz.smartexpensetracker.smart_expense_tracker.repository.UserRepository;
import cz.smartexpensetracker.smart_expense_tracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*") // Allow all origins for CORS
@RestController // Marks this class as a REST controller
@RequestMapping("/api/transactions") // Base URL path for this controller
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;

    // Constructor injection of dependencies
    public TransactionController(final TransactionService transactionService, final UserRepository userRepository, final BudgetRepository budgetRepository) {
        this.transactionService = transactionService;
        this.userRepository = userRepository;
        this.budgetRepository = budgetRepository;
    }

    // GET endpoint to return all transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // GET endpoint to get a transaction by UUID, returns 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable final UUID id) {
        final Transaction transaction = transactionService.getTransactionById(id);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    // POST endpoint to create a new transaction associated with a user
    @PostMapping(value = "/user/{userId}", consumes = "application/json")
    public ResponseEntity<Transaction> addTransaction(@PathVariable final UUID userId, @Valid @RequestBody final Transaction transaction) {
        // Fetch user or throw exception if not found
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        transaction.setUser(user);

        // Validate budget presence
        if (transaction.getBudget() == null || transaction.getBudget().getId() == null) {
            throw new IllegalArgumentException("Budget or Budget ID must not be null");
        }

        // Fetch budget or throw exception if not found
        final UUID budgetId = transaction.getBudget().getId();
        final Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        transaction.setBudget(budget);

        // Create the transaction and return 201 Created
        final Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(201).body(createdTransaction);
    }

    // PUT endpoint to update an existing transaction by UUID
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransactionById(@PathVariable final UUID id, @Valid @RequestBody final Transaction transaction) {
        // Validate budget presence in update payload
        if (transaction.getBudget() == null || transaction.getBudget().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Fetch budget or throw exception
        final UUID budgetId = transaction.getBudget().getId();
        final Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        transaction.setBudget(budget);

        // Perform update and respond accordingly
        final Transaction updatedTransaction = transactionService.updateTransactionById(id, transaction);
        return updatedTransaction != null ? ResponseEntity.ok(updatedTransaction) : ResponseEntity.notFound().build();
    }

    // DELETE endpoint to remove a transaction by UUID, returns 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransactionById(@PathVariable final UUID id) {
        transactionService.deleteTransactionById(id);
        return ResponseEntity.noContent().build();
    }

    // GET endpoint to retrieve all transactions for a specific user by userId
    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUserId(@PathVariable final UUID userId) {
        return transactionService.getTransactionsByUserId(userId);
    }
}