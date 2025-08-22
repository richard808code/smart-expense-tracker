package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.User;
import cz.smartexpensetracker.smart_expense_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*") // Allow all origins for CORS
@RestController // Marks this class as a REST controller
@RequestMapping("/api/users") // Base URL path for this controller
public class UserController {

    private final UserService userService;

    // Constructor injection of UserService
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    // GET endpoint to return all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET endpoint to get a user by UUID, returns 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable final UUID id) {
        final User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // POST endpoint to create a new user
    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody final User user) {
        final User createdUser = userService.createUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    // PUT endpoint to update an existing user by UUID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable final UUID id, @Valid @RequestBody final User user) {
        final User updatedUser = userService.updateUserById(id, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    // DELETE endpoint to delete a user by UUID
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable final UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}