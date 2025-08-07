package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(UUID id);

    void deleteTransactionById(UUID id);

    Transaction updateTransactionById(UUID id, Transaction updatedTransaction);

    List<Transaction> getTransactionsByUserId(UUID userId);

}
