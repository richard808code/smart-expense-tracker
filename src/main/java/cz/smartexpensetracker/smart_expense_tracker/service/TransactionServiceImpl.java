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

    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, BudgetRepository budgetRepository) {
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteTransactionById(UUID id) {
        transactionRepository.deleteById(id);
    }

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

    @Override
    public List<Transaction> getTransactionsByUserId(UUID userId) {
        return transactionRepository.findAllByUserIdWithBudget(userId);
    }
}