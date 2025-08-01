package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.User;
import cz.smartexpensetracker.smart_expense_tracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private User createSampleUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("hashed-password");
        return user;
    }

    @Test
    void testCreateUser() {
        User sample = createSampleUser();

        UserRepository mockRepo = Mockito.mock(UserRepository.class);
        when(mockRepo.save(sample)).thenReturn(sample);

        UserServiceImpl service = new UserServiceImpl(mockRepo);

        User result = service.createUser(sample);
        assertEquals(sample, result);

        verify(mockRepo, times(1)).save(sample);
    }

    @Test
    void testGetUserById_found() {
        UUID id = UUID.randomUUID();
        User sample = createSampleUser();
        sample.setId(id);

        UserRepository mockRepo = Mockito.mock(UserRepository.class);
        when(mockRepo.findById(id)).thenReturn(Optional.of(sample));

        UserServiceImpl service = new UserServiceImpl(mockRepo);

        User result = service.getUserById(id);
        assertEquals(sample, result);

        verify(mockRepo).findById(id);
    }

    @Test
    void testGetUserById_notFound() {
        UUID id = UUID.randomUUID();

        UserRepository mockRepo = Mockito.mock(UserRepository.class);
        when(mockRepo.findById(id)).thenReturn(Optional.empty());

        UserServiceImpl service = new UserServiceImpl(mockRepo);

        User result = service.getUserById(id);
        assertNull(result);

        verify(mockRepo).findById(id);
    }

    @Test
    void testDeleteUserById() {
        UUID id = UUID.randomUUID();

        UserRepository mockRepo = Mockito.mock(UserRepository.class);
        doNothing().when(mockRepo).deleteById(id);

        UserServiceImpl service = new UserServiceImpl(mockRepo);

        service.deleteUserById(id);

        verify(mockRepo, times(1)).deleteById(id);
    }

    @Test
    void testGetAllUsers() {
        User u1 = createSampleUser();
        User u2 = createSampleUser();
        u2.setUsername("anotheruser");

        List<User> users = List.of(u1, u2);

        UserRepository mockRepo = Mockito.mock(UserRepository.class);
        when(mockRepo.findAll()).thenReturn(users);

        UserServiceImpl service = new UserServiceImpl(mockRepo);

        List<User> result = service.getAllUsers();
        assertEquals(users, result);

        verify(mockRepo).findAll();
    }

    @Test
    void testUpdateUserById_found() {
        UUID id = UUID.randomUUID();
        User existing = createSampleUser();
        existing.setId(id);

        User updated = new User();
        updated.setUsername("newname");
        updated.setEmail("new@example.com");
        updated.setPassword("newpassword");

        UserRepository mockRepo = Mockito.mock(UserRepository.class);
        when(mockRepo.findById(id)).thenReturn(Optional.of(existing));
        when(mockRepo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserServiceImpl service = new UserServiceImpl(mockRepo);

        User result = service.updateUserById(id, updated);

        assertEquals("newname", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("newpassword", result.getPassword());

        verify(mockRepo).findById(id);
        verify(mockRepo).save(existing);
    }

    @Test
    void testUpdateUserById_notFound() {
        UUID id = UUID.randomUUID();
        User updated = createSampleUser();

        UserRepository mockRepo = Mockito.mock(UserRepository.class);
        when(mockRepo.findById(id)).thenReturn(Optional.empty());

        UserServiceImpl service = new UserServiceImpl(mockRepo);

        User result = service.updateUserById(id, updated);

        assertNull(result);

        verify(mockRepo).findById(id);
        verify(mockRepo, never()).save(any());
    }
}