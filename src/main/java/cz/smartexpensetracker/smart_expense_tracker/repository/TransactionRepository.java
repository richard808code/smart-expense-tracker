package cz.smartexpensetracker.smart_expense_tracker.repository;

import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
