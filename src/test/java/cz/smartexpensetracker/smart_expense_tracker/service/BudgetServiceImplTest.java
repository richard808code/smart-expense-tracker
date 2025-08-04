package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Budget;
import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import cz.smartexpensetracker.smart_expense_tracker.model.User;
import cz.smartexpensetracker.smart_expense_tracker.repository.BudgetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BudgetServiceImplTest {

    private User createSampleUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
        return user;
    }

    private Category createSampleCategory() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Food");
        return category;
    }

    @Test
    void testCreateBudget() {
        Budget sample = new Budget();
        sample.setId(UUID.randomUUID());
        sample.setLimitAmount(new BigDecimal("500.00"));
        sample.setUser(createSampleUser());
        sample.setCategory(createSampleCategory());

        BudgetRepository mockRepo = Mockito.mock(BudgetRepository.class);
        when(mockRepo.save(sample)).thenReturn(sample);

        BudgetServiceImpl service = new BudgetServiceImpl(mockRepo);

        Budget result = service.createBudget(sample);
        assertEquals(sample, result);

        verify(mockRepo, times(1)).save(sample);
    }

    @Test
    void testGetBudgetById_found() {
        UUID id = UUID.randomUUID();
        Budget sample = new Budget();
        sample.setId(id);
        sample.setLimitAmount(new BigDecimal("500.00"));
        sample.setUser(createSampleUser());
        sample.setCategory(createSampleCategory());

        BudgetRepository mockRepo = Mockito.mock(BudgetRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.of(sample));

        BudgetServiceImpl service = new BudgetServiceImpl(mockRepo);

        Budget result = service.getBudgetById(id);
        assertEquals(sample, result);

        verify(mockRepo, times(1)).findById(id);
    }

    @Test
    void testGetBudgetById_notFound() {
        UUID id = UUID.randomUUID();

        BudgetRepository mockRepo = Mockito.mock(BudgetRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.empty());

        BudgetServiceImpl service = new BudgetServiceImpl(mockRepo);

        Budget result = service.getBudgetById(id);
        assertNull(result);

        verify(mockRepo, times(1)).findById(id);
    }

    @Test
    void testDeleteBudgetById_found() {
        UUID id = UUID.randomUUID();

        BudgetRepository mockRepo = Mockito.mock(BudgetRepository.class);
        doNothing().when(mockRepo).deleteById(id);

        BudgetServiceImpl service = new BudgetServiceImpl(mockRepo);

        service.deleteBudgetById(id);

        verify(mockRepo, times(1)).deleteById(id);
    }

    @Test
    void testGetAllBudgets() {
        Budget b1 = new Budget();
        b1.setId(UUID.randomUUID());
        b1.setLimitAmount(new BigDecimal("500.00"));
        b1.setUser(createSampleUser());
        b1.setCategory(createSampleCategory());

        Budget b2 = new Budget();
        b2.setId(UUID.randomUUID());
        b2.setLimitAmount(new BigDecimal("1000.00"));
        b2.setUser(createSampleUser());
        b2.setCategory(createSampleCategory());

        List<Budget> budgets = List.of(b1, b2);

        BudgetRepository mockRepo = Mockito.mock(BudgetRepository.class);
        when(mockRepo.findAll()).thenReturn(budgets);

        BudgetServiceImpl service = new BudgetServiceImpl(mockRepo);

        List<Budget> result = service.getAllBudgets();

        assertEquals(budgets, result);

        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void testUpdateBudgetById_found() {
        UUID id = UUID.randomUUID();
        Budget existing = new Budget();
        existing.setId(id);
        existing.setLimitAmount(new BigDecimal("400.00"));
        existing.setUser(createSampleUser());
        existing.setCategory(createSampleCategory());

        Budget updated = new Budget();
        updated.setId(id);
        updated.setLimitAmount(new BigDecimal("600.00"));
        updated.setUser(existing.getUser()); // použij stejného uživatele, aby se porovnání povedlo
        updated.setCategory(createSampleCategory());

        BudgetRepository mockRepo = Mockito.mock(BudgetRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.of(existing));
        when(mockRepo.save(any(Budget.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BudgetServiceImpl service = new BudgetServiceImpl(mockRepo);

        Budget result = service.updateBudgetById(id, updated);

        assertEquals(updated.getLimitAmount(), result.getLimitAmount());
        assertEquals(updated.getCategory(), result.getCategory());
        assertEquals(existing.getUser(), result.getUser());

        verify(mockRepo).findById(id);
        verify(mockRepo).save(existing);
    }

    @Test
    void testUpdateBudgetById_notFound() {
        UUID id = UUID.randomUUID();
        Budget updated = new Budget();

        BudgetRepository mockRepo = Mockito.mock(BudgetRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.empty());

        BudgetServiceImpl service = new BudgetServiceImpl(mockRepo);

        Budget result = service.updateBudgetById(id, updated);

        assertNull(result);
        verify(mockRepo).findById(id);
        verify(mockRepo, never()).save(any());
    }

    @Test
    void testDeleteBudgetById_notFound() {
        UUID id = UUID.randomUUID();

        BudgetRepository mockRepo = Mockito.mock(BudgetRepository.class);
        doThrow(new java.util.NoSuchElementException()).when(mockRepo).deleteById(id);

        BudgetServiceImpl service = new BudgetServiceImpl(mockRepo);

        try {
            service.deleteBudgetById(id);
        } catch (Exception ignored) {}

        verify(mockRepo).deleteById(id);
    }
}