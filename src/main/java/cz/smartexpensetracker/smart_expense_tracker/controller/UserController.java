package cz.smartexpensetracker.smart_expense_tracker.controller;

import cz.smartexpensetracker.smart_expense_tracker.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public String getAllUsers() {
        return "All users loaded.";
    }

    @PostMapping
    public String addUser(@RequestBody User user) {
        return "User added:" + user.getUsername();
    }
}
