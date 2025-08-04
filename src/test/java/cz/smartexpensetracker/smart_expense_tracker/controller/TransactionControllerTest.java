package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;
import cz.smartexpensetracker.smart_expense_tracker.model.User;
import cz.smartexpensetracker.smart_expense_tracker.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private Transaction sampleTransaction;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Category category = new Category();
        category.setId(UUID.randomUUID());

        sampleTransaction = new Transaction();
        sampleTransaction.setId(UUID.randomUUID());
        sampleTransaction.setAmount(new BigDecimal("100.00"));
        sampleTransaction.setDescription("Test transaction");
        sampleTransaction.setDate(LocalDateTime.of(2025, 8, 4, 12, 0));
        sampleTransaction.setUser(user);
        sampleTransaction.setCategory(category);
    }

    @Test
    void shouldReturnAllTransactions() throws Exception {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(List.of(sampleTransaction));

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleTransaction.getId().toString()))
                .andExpect(jsonPath("$[0].amount").value(100.00));
    }

    @Test
    void shouldReturnTransactionById() throws Exception {
        Mockito.when(transactionService.getTransactionById(sampleTransaction.getId())).thenReturn(sampleTransaction);

        mockMvc.perform(get("/api/transactions/{id}", sampleTransaction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleTransaction.getId().toString()))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    void shouldReturnNotFoundForMissingTransaction() throws Exception {
        UUID missingId = UUID.randomUUID();
        Mockito.when(transactionService.getTransactionById(missingId)).thenReturn(null);

        mockMvc.perform(get("/api/transactions/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateTransaction() throws Exception {
        Mockito.when(transactionService.createTransaction(any(Transaction.class))).thenReturn(sampleTransaction);

        String json = String.format("""
        {
            "amount": 100.00,
            "description": "Test transaction",
            "date": "2025-08-04T12:00:00",
            "user": {"id": "%s"},
            "category": {"id": "%s"}
        }
        """, sampleTransaction.getUser().getId(), sampleTransaction.getCategory().getId());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleTransaction.getId().toString()))
                .andExpect(jsonPath("$.description").value("Test transaction"));
    }

    @Test
    void shouldReturnBadRequestForInvalidTransaction() throws Exception {
        String invalidJson = """
        {
            "amount": 0,
            "description": "",
            "date": null,
            "user": null,
            "category": null
        }
        """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateTransactionById() throws Exception {
        UUID id = sampleTransaction.getId();
        Mockito.when(transactionService.updateTransactionById(eq(id), any(Transaction.class))).thenReturn(sampleTransaction);

        String json = String.format("""
        {
            "amount": 200.00,
            "description": "Updated transaction",
            "date": "2025-08-04T12:00:00",
            "user": {"id": "%s"},
            "category": {"id": "%s"}
        }
        """, sampleTransaction.getUser().getId(), sampleTransaction.getCategory().getId());

        mockMvc.perform(put("/api/transactions/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingTransaction() throws Exception {
        UUID missingId = UUID.randomUUID();
        Mockito.when(transactionService.updateTransactionById(eq(missingId), any(Transaction.class))).thenReturn(null);

        String json = """
        {
            "amount": 200.00,
            "description": "Updated transaction",
            "date": "2025-08-04T12:00:00",
            "user": {"id": "11111111-1111-1111-1111-111111111111"},
            "category": {"id": "22222222-2222-2222-2222-222222222222"}
        }
        """;

        mockMvc.perform(put("/api/transactions/{id}", missingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteTransactionById() throws Exception {
        UUID id = sampleTransaction.getId();
        Mockito.doNothing().when(transactionService).deleteTransactionById(id);

        mockMvc.perform(delete("/api/transactions/{id}", id))
                .andExpect(status().isNoContent());
    }
}