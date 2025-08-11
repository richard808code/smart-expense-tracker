package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.User;

import java.util.List;
import java.util.UUID;

// Service interface for managing users
// Defines basic CRUD operations for the User entity
public interface UserService {

    // Creates a new user
    User createUser(User user);

    // Retrieves a list of all users
    List<User> getAllUsers();

    // Finds a user by their ID
    User getUserById(UUID id);

    // Deletes a user by their ID
    void deleteUserById(UUID id);

    // Updates an existing user by their ID
    User updateUserById(UUID id, User updatedUser);
}