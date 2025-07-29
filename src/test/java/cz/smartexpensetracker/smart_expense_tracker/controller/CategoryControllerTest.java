package cz.smartexpensetracker.smart_expense_tracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTextWhenGetCategories() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().string("All categories loaded."));

    }

    @Test
    void shouldReturnTextWhenAddCategory() throws Exception {
        String json = "{\"name\":\"Test Category\"}";

        mockMvc.perform(post("/api/categories")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Category added: Test Category"));
    }
}
