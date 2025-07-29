package cz.smartexpensetracker.smart_expense_tracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTextWhenGetTransactions() throws Exception {
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().string("All transactions loaded."));
    }

    @Test
    void shouldReturnTextWhenAddTransaction() throws Exception {
        String json = """
        {
            "amount": 15.99,
            "description": "Groceries",
            "date": "2025-07-29T13:00:00",
            "user": {
                "id": "11111111-1111-1111-1111-111111111111"
            },
            "category": {
                "id": "22222222-2222-2222-2222-222222222222"
            }
        }
        """;

        mockMvc.perform(post("/api/transactions")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Transaction added: Groceries"));
    }
}
