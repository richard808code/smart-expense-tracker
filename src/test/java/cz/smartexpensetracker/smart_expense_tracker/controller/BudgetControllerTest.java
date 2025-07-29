package cz.smartexpensetracker.smart_expense_tracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BudgetController.class)
public class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTextWhenGetBudgets() throws Exception {
        mockMvc.perform(get("/api/budgets"))
                .andExpect(status().isOk())
                .andExpect(content().string("All budgets loaded."));
    }

    @Test
    void shouldReturnTextWhenAddBudget() throws Exception {
        String json = """
        {
            "limitAmount": 500.0,
            "user": {
                "id": "11111111-1111-1111-1111-111111111111"
            },
            "category": {
                "id": "22222222-2222-2222-2222-222222222222"
            }
        }
        """;

        mockMvc.perform(post("/api/budgets")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Budget added: 500.0"));
    }
}
