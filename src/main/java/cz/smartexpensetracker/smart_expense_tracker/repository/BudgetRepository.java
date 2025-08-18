package cz.smartexpensetracker.smart_expense_tracker.repository;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    // Finds budgets for a user and fetches associated category in the same query to avoid extra selects
    @Query("SELECT b FROM Budget b JOIN FETCH b.category WHERE b.user.id = :userId")
    List<Budget> findByUserIdWithCategory(@Param("userId") UUID userId);

    // Finds budgets for a user and fetches associated transactions; includes budgets without transactions
    @Query("SELECT b FROM Budget b LEFT JOIN FETCH b.transactions WHERE b.user.id = :userId")
    List<Budget> findByUserIdWithTransactions(@Param("userId") UUID userId);
}