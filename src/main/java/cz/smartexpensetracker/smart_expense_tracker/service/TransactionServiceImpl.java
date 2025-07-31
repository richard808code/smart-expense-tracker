package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.repository.TransactionRepository;
import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
                    existing.setCategory(updatedTransaction.getCategory());
                    return transactionRepository.save(existing);
                })
                .orElse(null);
    }
}