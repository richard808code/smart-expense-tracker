package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(UUID id);

    void deleteUserById(UUID id);

    User updateUserById(UUID id, User updatedUser);
}
