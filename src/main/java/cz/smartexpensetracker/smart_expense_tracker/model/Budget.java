package cz.smartexpensetracker.smart_expense_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @DecimalMin(value = "0.01", message = "The limit amount must be greater than 0!")
    private BigDecimal limitAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
