package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    // Creates a new transaction and saves it to the database
    Transaction createTransaction(Transaction transaction);

    // Retrieves all transactions from the database
    List<Transaction> getAllTransactions();

    // Retrieves a transaction by its unique ID
    Transaction getTransactionById(UUID id);

    // Deletes a transaction identified by its ID
    void deleteTransactionById(UUID id);

    // Updates an existing transaction identified by its ID with new data
    Transaction updateTransactionById(UUID id, Transaction updatedTransaction);

    // Retrieves all transactions for a specific user by user ID
    List<Transaction> getTransactionsByUserId(UUID userId);
}