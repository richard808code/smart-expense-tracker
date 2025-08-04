package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import cz.smartexpensetracker.smart_expense_tracker.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private Category sampleCategory;

    @BeforeEach
    void setup() {
        sampleCategory = new Category();
        sampleCategory.setId(UUID.randomUUID());
        sampleCategory.setName("Sample Category");
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        Mockito.when(categoryService.getAllCategories()).thenReturn(List.of(sampleCategory));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleCategory.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("Sample Category"));
    }

    @Test
    void shouldReturnCategoryById() throws Exception {
        Mockito.when(categoryService.getCategoryById(sampleCategory.getId())).thenReturn(sampleCategory);

        mockMvc.perform(get("/api/categories/{id}", sampleCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleCategory.getId().toString()))
                .andExpect(jsonPath("$.name").value("Sample Category"));
    }

    @Test
    void shouldReturnNotFoundForMissingCategory() throws Exception {
        UUID missingId = UUID.randomUUID();
        Mockito.when(categoryService.getCategoryById(missingId)).thenReturn(null);

        mockMvc.perform(get("/api/categories/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateCategory() throws Exception {
        Mockito.when(categoryService.createCategory(any(Category.class))).thenReturn(sampleCategory);

        String json = """
        {
            "name": "Sample Category"
        }
        """;

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleCategory.getId().toString()))
                .andExpect(jsonPath("$.name").value("Sample Category"));
    }

    @Test
    void shouldReturnBadRequestForEmptyName() throws Exception {
        String json = """
        {
            "name": ""
        }
        """;

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        UUID id = sampleCategory.getId();
        Category updatedCategory = new Category();
        updatedCategory.setId(id);
        updatedCategory.setName("Updated Category");

        Mockito.when(categoryService.updateCategoryById(eq(id), any(Category.class))).thenReturn(updatedCategory);

        String json = """
        {
            "name": "Updated Category"
        }
        """;

        mockMvc.perform(put("/api/categories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingCategory() throws Exception {
        UUID missingId = UUID.randomUUID();
        Mockito.when(categoryService.updateCategoryById(eq(missingId), any(Category.class))).thenReturn(null);

        String json = """
        {
            "name": "Updated Category"
        }
        """;

        mockMvc.perform(put("/api/categories/{id}", missingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        UUID id = sampleCategory.getId();
        Mockito.doNothing().when(categoryService).deleteCategoryById(id);

        mockMvc.perform(delete("/api/categories/{id}", id))
                .andExpect(status().isNoContent());
    }
}