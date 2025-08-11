package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;
import cz.smartexpensetracker.smart_expense_tracker.repository.BudgetRepository;
import cz.smartexpensetracker.smart_expense_tracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    // Repository for transactions to perform CRUD operations
    private final TransactionRepository transactionRepository;

    // Repository for budgets, used to validate and fetch budgets linked to transactions
    private final BudgetRepository budgetRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
    }

    // Saves a new transaction to the database
    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Retrieves all transactions from the database
    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Finds a transaction by its ID, returns null if not found
    @Override
    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id).orElse(null);
    }

    // Deletes a transaction identified by its ID
    @Override
    public void deleteTransactionById(UUID id) {
        transactionRepository.deleteById(id);
    }

    // Updates an existing transaction with new data; also verifies and sets the associated budget
    @Override
    public Transaction updateTransactionById(UUID id, Transaction updatedTransaction) {
        return transactionRepository.findById(id)
                .map(existing -> {
                    existing.setAmount(updatedTransaction.getAmount());
                    existing.setDescription(updatedTransaction.getDescription());
                    existing.setDate(updatedTransaction.getDate());

                    UUID budgetId = updatedTransaction.getBudget().getId();
                    Budget budget = budgetRepository.findById(budgetId)
                            .orElseThrow(() -> new RuntimeException("Budget not found"));
                    existing.setBudget(budget);

                    return transactionRepository.save(existing);
                })
                .orElse(null);
    }

    // Retrieves all transactions for a specific user, including budget details for each transaction
    @Override
    public List<Transaction> getTransactionsByUserId(UUID userId) {
        return transactionRepository.findAllByUserIdWithBudget(userId);
    }
}