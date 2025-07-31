package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.model.Category;
import cz.smartexpensetracker.smart_expense_tracker.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @Test
    void testCreateCategory() {
        Category sample = new Category();
        sample.setName("Food");
        sample.setId(UUID.randomUUID());

       CategoryRepository mockRepo = Mockito.mock(CategoryRepository.class);
       when(mockRepo.save(sample)).thenReturn(sample);

       CategoryServiceImpl service = new CategoryServiceImpl(mockRepo);

       Category result = service.createCategory(sample);
       assertEquals(sample, result);

       verify(mockRepo, times(1)).save(sample);
    }

    @Test
    void testGetCategoryById_found() {
        UUID id = UUID.randomUUID();
        Category sample = new Category();
        sample.setId(id);
        sample.setName("Housing");

        CategoryRepository mockRepo = Mockito.mock(CategoryRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.of(sample));

        CategoryServiceImpl service = new CategoryServiceImpl(mockRepo);

        Category result = service.getCategoryById(id);
        assertEquals(sample, result);

        verify(mockRepo, times(1)).findById(id);
    }

    @Test
    void testGetCategoryById_notFound() {
        UUID id = UUID.randomUUID();

        CategoryRepository mockRepo = Mockito.mock(CategoryRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.empty());

        CategoryServiceImpl service = new CategoryServiceImpl(mockRepo);
        Category result = service.getCategoryById(id);
        assertNull(result);

        verify(mockRepo, times(1)).findById(id);
    }

    @Test
    void testDeleteCategoryById_found() {
        UUID id = UUID.randomUUID();

        CategoryRepository mockRepo = Mockito.mock(CategoryRepository.class);
        doNothing().when(mockRepo).deleteById(id);

        CategoryServiceImpl service = new CategoryServiceImpl(mockRepo);

        service.deleteCategoryById(id);
        verify(mockRepo, times(1)).deleteById(id);
    }

    @Test
    void testDeleteCategoryById_notFound() {
        UUID id = UUID.randomUUID();

        CategoryRepository mockRepo = Mockito.mock(CategoryRepository.class);
        doThrow(new EmptyResultDataAccessException(1)).when(mockRepo).deleteById(id);

        CategoryServiceImpl service = new CategoryServiceImpl(mockRepo);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            service.deleteCategoryById(id);
        });

        verify(mockRepo).deleteById(id);
    }

    @Test
    void testGetAllCategories() {
        Category cat1 = new Category();
        cat1.setId(UUID.randomUUID());
        cat1.setName("Food");

        Category cat2 = new Category();
        cat2.setId(UUID.randomUUID());
        cat2.setName("Transport");

        List<Category> categories = List.of(cat1, cat2);

        CategoryRepository mockRepo = Mockito.mock(CategoryRepository.class);
        when(mockRepo.findAll()).thenReturn(categories);

        CategoryServiceImpl service = new CategoryServiceImpl(mockRepo);
        List<Category> result = service.getAllCategories();
        assertEquals(categories, result);

        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void testUpdateCategoryById_found() {
        UUID id = UUID.randomUUID();
        Category existing = new Category();
        existing.setId(id);
        existing.setName("Old Name");

        Category updated = new Category();
        updated.setName("New Name");

        CategoryRepository mockRepo = Mockito.mock(CategoryRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.of(existing));
        when(mockRepo.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CategoryServiceImpl service = new CategoryServiceImpl(mockRepo);

        Category result = service.updateCategoryById(id, updated);

        assertEquals(updated.getName(), result.getName());

        verify(mockRepo).findById(id);
        verify(mockRepo).save(existing);
    }

    @Test
    void testUpdateCategoryById_notFound() {
        UUID id = UUID.randomUUID();
        Category updated = new Category();

        CategoryRepository mockRepo = Mockito.mock(CategoryRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.empty());

        CategoryServiceImpl service = new CategoryServiceImpl(mockRepo);

        Category result = service.updateCategoryById(id, updated);

        assertNull(result);

        verify(mockRepo).findById(id);
        verify(mockRepo, never()).save(any());
    }
}
