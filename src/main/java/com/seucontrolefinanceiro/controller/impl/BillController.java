package com.seucontrolefinanceiro.controller.impl;

import com.seucontrolefinanceiro.controller.ScfController;
import com.seucontrolefinanceiro.domain.model.Bill;
import com.seucontrolefinanceiro.domain.dto.response.BillResponse;
import com.seucontrolefinanceiro.domain.dto.response.UserResponse;
import com.seucontrolefinanceiro.domain.dto.request.BillRequest;
import com.seucontrolefinanceiro.service.impl.BillService;
import com.seucontrolefinanceiro.service.impl.PaymentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("scf-service/bills")
public class BillController implements ScfController<BillResponse, BillRequest> {

    @Autowired
    private BillService service;

    @Autowired
    private PaymentCategoryService paymentCategoryService;

    @Override
    @GetMapping
    public ResponseEntity<List<BillResponse>> find(String query) {
        List<Bill> bills = service.findAll();
        return ResponseEntity.ok().body(BillResponse.converter(bills));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> findById(@PathVariable String id) {
        Bill bill = service.findById(id);
        return ResponseEntity.ok().body(new BillResponse(bill));
    }

    @Override
    @PostMapping
    public ResponseEntity<BillResponse> insert(@RequestBody @Validated BillRequest form, UriComponentsBuilder uriBuilder) {
        Bill bill = form.converter();
        service.save(bill);
        URI uri = uriBuilder.path("scf-service/bills/{id}").buildAndExpand(bill.getId()).toUri();
        return ResponseEntity.created(uri).body(new BillResponse(bill));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(String id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    public ResponseEntity<UserResponse> update(@RequestBody @Validated BillRequest form, UriComponentsBuilder uriBuilder) {
        Bill bill = form.converter();
        bill.setPaymentCategory(paymentCategoryService.findByDescriptionContainingIgnoreCase(bill.getPaymentCategory().getDescription()).get(0));
        service.update(bill);
        URI uri = uriBuilder.path("scf-service/bills/{id}").buildAndExpand(bill.getId()).toUri();
        return ResponseEntity.noContent().build();
    }
}