package com.seucontrolefinanceiro.service;

import com.seucontrolefinanceiro.domain.model.Bill;
import com.seucontrolefinanceiro.feature.BillFactory;
import com.seucontrolefinanceiro.repository.BillRepository;
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
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @InjectMocks
    private BillService service;

    @Mock
    private BillRepository repository;

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
}