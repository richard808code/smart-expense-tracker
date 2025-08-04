package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import cz.smartexpensetracker.smart_expense_tracker.model.User;
import cz.smartexpensetracker.smart_expense_tracker.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BudgetController.class)
public class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService budgetService;

    private Budget sampleBudget;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        Category category = new Category();
        category.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));

        sampleBudget = new Budget();
        sampleBudget.setId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        sampleBudget.setLimitAmount(new BigDecimal("500.00"));
        sampleBudget.setUser(user);
        sampleBudget.setCategory(category);
    }

    @Test
    void shouldReturnListOfBudgets() throws Exception {
        Mockito.when(budgetService.getAllBudgets()).thenReturn(List.of(sampleBudget));

        mockMvc.perform(get("/api/budgets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleBudget.getId().toString()))
                .andExpect(jsonPath("$[0].limitAmount").value(500.00));
    }

    @Test
    void shouldReturnBudgetById() throws Exception {
        Mockito.when(budgetService.getBudgetById(sampleBudget.getId())).thenReturn(sampleBudget);

        mockMvc.perform(get("/api/budgets/{id}", sampleBudget.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleBudget.getId().toString()))
                .andExpect(jsonPath("$.limitAmount").value(500.00));
    }

    @Test
    void shouldReturnNotFoundForMissingBudget() throws Exception {
        UUID missingId = UUID.fromString("44444444-4444-4444-4444-444444444444");
        Mockito.when(budgetService.getBudgetById(missingId)).thenReturn(null);

        mockMvc.perform(get("/api/budgets/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateBudget() throws Exception {
        Mockito.when(budgetService.createBudget(any(Budget.class))).thenReturn(sampleBudget);

        String json = """
        {
            "limitAmount": 500.00,
            "user": {"id": "11111111-1111-1111-1111-111111111111"},
            "category": {"id": "22222222-2222-2222-2222-222222222222"}
        }
        """;

        mockMvc.perform(post("/api/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleBudget.getId().toString()))
                .andExpect(jsonPath("$.limitAmount").value(500.00));
    }

    @Test
    void shouldUpdateBudget() throws Exception {
        Mockito.when(budgetService.updateBudgetById(eq(sampleBudget.getId()), any(Budget.class)))
                .thenReturn(sampleBudget);

        String json = """
        {
            "limitAmount": 600.00,
            "user": {"id": "11111111-1111-1111-1111-111111111111"},
            "category": {"id": "22222222-2222-2222-2222-222222222222"}
        }
        """;

        mockMvc.perform(put("/api/budgets/{id}", sampleBudget.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleBudget.getId().toString()));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingBudget() throws Exception {
        UUID missingId = UUID.fromString("44444444-4444-4444-4444-444444444444");
        Mockito.when(budgetService.updateBudgetById(eq(missingId), any(Budget.class))).thenReturn(null);

        String json = """
        {
            "limitAmount": 600.00,
            "user": {"id": "11111111-1111-1111-1111-111111111111"},
            "category": {"id": "22222222-2222-2222-2222-222222222222"}
        }
        """;

        mockMvc.perform(put("/api/budgets/{id}", missingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteBudget() throws Exception {
        Mockito.doNothing().when(budgetService).deleteBudgetById(sampleBudget.getId());

        mockMvc.perform(delete("/api/budgets/{id}", sampleBudget.getId()))
                .andExpect(status().isNoContent());
    }
}