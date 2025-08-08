package cz.smartexpensetracker.smart_expense_tracker.repository;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import cz.smartexpensetracker.smart_expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    List<Budget> findByUserId(UUID userId);

    @Query("SELECT b FROM Budget b JOIN FETCH b.category WHERE b.user.id = :userId")
    List<Budget> findByUserIdWithCategory(@Param("userId") UUID userId);

    @Query("SELECT b FROM Budget b LEFT JOIN FETCH b.transactions WHERE b.user.id = :userId")
    List<Budget> findByUserIdWithTransactions(@Param("userId") UUID userId);
}