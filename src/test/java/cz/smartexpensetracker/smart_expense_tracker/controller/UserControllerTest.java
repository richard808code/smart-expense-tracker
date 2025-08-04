package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.User;
import cz.smartexpensetracker.smart_expense_tracker.service.UserService;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setup() {
        sampleUser = new User();
        sampleUser.setId(UUID.randomUUID());
        sampleUser.setUsername("john_doe");
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(sampleUser));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleUser.getId().toString()))
                .andExpect(jsonPath("$[0].username").value("john_doe"));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        Mockito.when(userService.getUserById(sampleUser.getId())).thenReturn(sampleUser);

        mockMvc.perform(get("/api/users/{id}", sampleUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleUser.getId().toString()))
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    @Test
    void shouldReturnNotFoundForMissingUser() throws Exception {
        UUID missingId = UUID.randomUUID();
        Mockito.when(userService.getUserById(missingId)).thenReturn(null);

        mockMvc.perform(get("/api/users/{id}", missingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateUser() throws Exception {
        Mockito.when(userService.createUser(any(User.class))).thenReturn(sampleUser);

        String json = """
        {
            "username": "john_doe"
        }
        """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleUser.getId().toString()))
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    @Test
    void shouldReturnBadRequestForInvalidUser() throws Exception {
        String invalidJson = """
        {
            "username": ""
        }
        """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateUserById() throws Exception {
        UUID id = sampleUser.getId();
        Mockito.when(userService.updateUserById(eq(id), any(User.class))).thenReturn(sampleUser);

        String json = """
        {
            "username": "updated_user"
        }
        """;

        mockMvc.perform(put("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingMissingUser() throws Exception {
        UUID missingId = UUID.randomUUID();
        Mockito.when(userService.updateUserById(eq(missingId), any(User.class))).thenReturn(null);

        String json = """
        {
            "username": "updated_user"
        }
        """;

        mockMvc.perform(put("/api/users/{id}", missingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        UUID id = sampleUser.getId();
        Mockito.doNothing().when(userService).deleteUserById(id);

        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isOk());
    }
}