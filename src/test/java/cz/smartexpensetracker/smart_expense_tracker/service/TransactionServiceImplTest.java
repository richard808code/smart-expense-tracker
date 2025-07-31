package cz.smartexpensetracker.smart_expense_tracker.service;

import cz.smartexpensetracker.smart_expense_tracker.repository.TransactionRepository;
import cz.smartexpensetracker.smart_expense_tracker.model.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {

    @Test
    void testCreateTransaction() {
        Transaction sample = new Transaction();
        sample.setId(UUID.randomUUID());
        sample.setAmount(new BigDecimal("100.00"));
        sample.setDescription("Test purchase");

        TransactionRepository mockRepo = Mockito.mock(TransactionRepository.class);
        when(mockRepo.save(sample)).thenReturn(sample);

        TransactionServiceImpl service = new TransactionServiceImpl(mockRepo);

        Transaction result = service.createTransaction(sample);
        assertEquals(sample, result);

        verify(mockRepo, times(1)).save(sample);
    }

    @Test
    void testGetTransactionById_found() {
        UUID id = UUID.randomUUID();
        Transaction sample = new Transaction();
        sample.setId(id);
        sample.setAmount(new BigDecimal("100.00"));
        sample.setDescription("Sample transaction");

        TransactionRepository mockRepo = Mockito.mock(TransactionRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.of(sample));

        TransactionServiceImpl service = new TransactionServiceImpl(mockRepo);

        Transaction result = service.getTransactionById(id);
        assertEquals(sample, result);

        verify(mockRepo, times(1)).findById(id);
    }

    @Test
    void testGetTransactionById_notFound() {
        UUID id = UUID.randomUUID();

        TransactionRepository mockRepo = Mockito.mock(TransactionRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.empty());

        TransactionServiceImpl service = new TransactionServiceImpl(mockRepo);

        Transaction result = service.getTransactionById(id);
        assertNull(result);

        verify(mockRepo, times(1)).findById(id);
    }

    @Test
    void testDeleteTransactionById_found() {
        UUID id = UUID.randomUUID();

        TransactionRepository mockRepo = Mockito.mock(TransactionRepository.class);
        doNothing().when(mockRepo).deleteById(id);

        TransactionServiceImpl service = new TransactionServiceImpl(mockRepo);

        service.deleteTransactionById(id);

        verify(mockRepo, times(1)).deleteById(id);
    }

    @Test
    void testGetAllTransactions() {
        Transaction t1 = new Transaction();
        t1.setId(UUID.randomUUID());
        t1.setAmount(new BigDecimal("100.00"));
        t1.setDescription("Groceries");

        Transaction t2 = new Transaction();
        t2.setId(UUID.randomUUID());
        t2.setAmount(new BigDecimal("50.00"));
        t2.setDescription("Transport");

        List<Transaction> transactions = List.of(t1, t2);

        TransactionRepository mockRepo = Mockito.mock(TransactionRepository.class);
        when(mockRepo.findAll()).thenReturn(transactions);

        TransactionServiceImpl service = new TransactionServiceImpl(mockRepo);

        List<Transaction> result = service.getAllTransactions();

        assertEquals(transactions, result);

        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void testUpdateTransactionById_found() {
        UUID id = UUID.randomUUID();
        Transaction existing = new Transaction();
        existing.setId(id);
        existing.setAmount(new BigDecimal("50.00"));
        existing.setDescription("Old");
        existing.setDate(java.time.LocalDateTime.now().minusDays(1));

        Transaction updated = new Transaction();
        updated.setId(id);
        updated.setAmount(new BigDecimal("100.00"));
        updated.setDescription("New");
        updated.setDate(java.time.LocalDateTime.now());

        TransactionRepository mockRepo = Mockito.mock(TransactionRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.of(existing));
        when(mockRepo.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionServiceImpl service = new TransactionServiceImpl(mockRepo);

        Transaction result = service.updateTransactionById(id, updated);

        assertEquals(updated, result);

        verify(mockRepo).findById(id);
        verify(mockRepo).save(existing);
    }

    @Test
    void testUpdateTransactionById_notFound() {
        UUID id = UUID.randomUUID();
        Transaction updated = new Transaction();

        TransactionRepository mockRepo = Mockito.mock(TransactionRepository.class);
        when(mockRepo.findById(id)).thenReturn(java.util.Optional.empty());

        TransactionServiceImpl service = new TransactionServiceImpl(mockRepo);

        Transaction result = service.updateTransactionById(id, updated);

        assertNull(result);
        verify(mockRepo).findById(id);
        verify(mockRepo, never()).save(any());
    }

    @Test
    void testDeleteTransactionById_notFound() {
        UUID id = UUID.randomUUID();

        TransactionRepository mockRepo = Mockito.mock(TransactionRepository.class);
        doThrow(new java.util.NoSuchElementException()).when(mockRepo).deleteById(id);

        TransactionServiceImpl service = new TransactionServiceImpl(mockRepo);

        try {
            service.deleteTransactionById(id);
        } catch (Exception ignored) {}

        verify(mockRepo).deleteById(id);
    }
}