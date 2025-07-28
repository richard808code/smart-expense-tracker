package cz.smartexpensetracker.smart_expense_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "You have to enter an username!")
    private String username;

    @Email(message = "You have to enter a valid e-mail address!")
    @NotBlank(message = "You have to enter an e-mail address!")
    private String email;

    @NotBlank(message = "You have to enter a password!")
    @Size(min = 6, message = "Password must have at least 6 characters!")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets = new ArrayList<>();
}
