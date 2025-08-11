package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.User;
import cz.smartexpensetracker.smart_expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Saves a new user to the database
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Retrieves a list of all users from the database
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Finds a user by their unique ID, returns null if not found
    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    // Deletes a user by their ID
    @Override
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    // Updates an existing user's username, returns null if user not found
    @Override
    public User updateUserById(UUID id, User updatedUser) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(updatedUser.getUsername());
                    return userRepository.save(existing);
                })
                .orElse(null);
    }
}