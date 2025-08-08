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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;

    public TransactionController(TransactionService transactionService, UserRepository userRepository, BudgetRepository budgetRepository) {
        this.transactionService = transactionService;
        this.userRepository = userRepository;
        this.budgetRepository = budgetRepository;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable UUID id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/user/{userId}", consumes = "application/json")
    public ResponseEntity<Transaction> addTransaction(@PathVariable UUID userId, @Valid @RequestBody Transaction transaction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        transaction.setUser(user);

        if (transaction.getBudget() == null || transaction.getBudget().getId() == null) {
            throw new IllegalArgumentException("Budget or Budget ID must not be null");
        }

        UUID budgetId = transaction.getBudget().getId();
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        transaction.setBudget(budget);

        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(201).body(createdTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransactionById(@PathVariable UUID id, @Valid @RequestBody Transaction transaction) {
        if (transaction.getBudget() == null || transaction.getBudget().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        UUID budgetId = transaction.getBudget().getId();
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        transaction.setBudget(budget);

        Transaction updatedTransaction = transactionService.updateTransactionById(id, transaction);
        return updatedTransaction != null ? ResponseEntity.ok(updatedTransaction) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransactionById(@PathVariable UUID id) {
        transactionService.deleteTransactionById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUserId(@PathVariable UUID userId) {
        return transactionService.getTransactionsByUserId(userId);
    }
}
