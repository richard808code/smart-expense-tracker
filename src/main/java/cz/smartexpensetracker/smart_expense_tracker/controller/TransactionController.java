package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @GetMapping
    public String getAllTransactions() {
        return "All transactions loaded.";
    }

    @PostMapping
    public String addTransaction(@RequestBody Transaction transaction) throws Exception {
        return "Transaction added: " + transaction.getDescription();
    }
}
