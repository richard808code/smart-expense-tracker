package cz.smartexpensetracker.smart_expense_tracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTextWhenGetUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().string("All users loaded."));
    }

    @Test
    void shouldReturnTextWhenAddUser() throws Exception {
        String json = """
                {
                    "username": "richard",
                    "email": "richard@example.com",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("User added:richard"));
    }
}
