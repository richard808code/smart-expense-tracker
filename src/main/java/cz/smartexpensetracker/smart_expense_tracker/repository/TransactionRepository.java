package cz.smartexpensetracker.smart_expense_tracker.repository;

import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    // Finds all transactions for a specific user and fetches their associated budget in a single query
    // Uses JOIN FETCH to avoid the N+1 select problem and load budget data eagerly
    @Query("SELECT t FROM Transaction t JOIN FETCH t.budget WHERE t.user.id = :userId")
    List<Transaction> findAllByUserIdWithBudget(@Param("userId") UUID userId);
}