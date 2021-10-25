package com.seucontrolefinanceiro.service;

import com.seucontrolefinanceiro.domain.model.Bill;
import com.seucontrolefinanceiro.domain.model.User;
import com.seucontrolefinanceiro.feature.BillFactory;
import com.seucontrolefinanceiro.feature.UserFactory;
import com.seucontrolefinanceiro.repository.BillRepository;
import com.seucontrolefinanceiro.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @InjectMocks
    private BillService service;

    @Mock
    private BillRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Test
    public void mustReturnAllBills_WhenCallFindAll() {
        List<Bill> bills = BillFactory.getBills();
        when(repository.findAll()).thenReturn(bills);
        assertFalse(service.findAll().isEmpty());
    }

    @Test
    public void mustReturnBill_WhenCallFindById() {
        Bill bill = BillFactory.getBill();
        when(repository.findById(anyString())).thenReturn(Optional.of(bill));
        assertNotNull(service.findById(anyString()));
    }

    @Test
    public void mustReturnBill_WhenCallSave() {
        Bill bill = BillFactory.getBillWithId();
        User user = UserFactory.getUserWithId();
        when(userService.findById(anyString())).thenReturn(user);
        when(repository.findByUserId(anyString())).thenReturn(List.of(bill));
        assertNotNull(service.save(bill));
    }

    @Test
    public void mustDeleteBill_WhenCallDelete() {
        Bill bill = BillFactory.getBillWithId();
        when(repository.findById(anyString())).thenReturn(Optional.of(bill));
        service.delete(bill.getId());
        verify(repository, times(1)).deleteById(anyString());
    }

    @Test
    public void mustUpdateBill_WhenCallUpdate() {
        var expectedDescription = "Descricao nova";
        Bill newBill = BillFactory.getBillWithId();
        Bill oldBill = BillFactory.getBillWithId();
        newBill.setBillDescription(expectedDescription);
        when(repository.findById(anyString())).thenReturn(Optional.of(oldBill));
        var resultDescription = service.update(newBill).getBillDescription();
        assertEquals(expectedDescription, resultDescription);
    }

    @Test
    public void mustDeleteAll_WhenCallDeleteAll() {
        Bill bill = BillFactory.getBillWithId();
        service.deleteAll(List.of(bill));
        verify(repository, times(1)).deleteAll(List.of(bill));

    }
}