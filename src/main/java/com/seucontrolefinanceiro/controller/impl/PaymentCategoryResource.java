package com.seucontrolefinanceiro.controller.impl;

import com.seucontrolefinanceiro.controller.ScfController;
import com.seucontrolefinanceiro.domain.model.PaymentCategory;
import com.seucontrolefinanceiro.domain.dto.response.PaymentCategoryResponse;
import com.seucontrolefinanceiro.domain.dto.response.UserResponse;
import com.seucontrolefinanceiro.domain.dto.request.PaymentCategoryRequest;
import com.seucontrolefinanceiro.service.impl.PaymentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("scf-service/payment-categories")
public class PaymentCategoryResource implements ScfController<PaymentCategoryResponse, PaymentCategoryRequest> {

    @Autowired
    private PaymentCategoryService service;

    @Override
    @GetMapping
    public ResponseEntity<List<PaymentCategoryResponse>> find(String query) {
        List<PaymentCategory> paymentCategories = service.findAll();
        return ResponseEntity.ok().body(PaymentCategoryResponse.converter((paymentCategories)));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PaymentCategoryResponse> findById(@PathVariable String id) {
        PaymentCategory paymentCategory = service.findById(id);
        return ResponseEntity.ok().body(new PaymentCategoryResponse(paymentCategory));
    }

    @Override
    @PostMapping
    public ResponseEntity<PaymentCategoryResponse> insert(@RequestBody @Validated PaymentCategoryRequest form, UriComponentsBuilder uriBuilder) {
        PaymentCategory paymentCategory = form.converter();
        paymentCategory = service.save(paymentCategory);
        URI uri = uriBuilder.path("scf-service/payment-categories/{id}").buildAndExpand(paymentCategory.getId()).toUri();
        return ResponseEntity.created(uri).body(new PaymentCategoryResponse(paymentCategory));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody @Validated PaymentCategoryRequest form, UriComponentsBuilder uriBuilder) {
        PaymentCategory paymentCategory = form.converter();
        paymentCategory = service.update(paymentCategory);
        URI uri = uriBuilder.path("scf-service/payment-categories/{id}").buildAndExpand(paymentCategory.getId()).toUri();
        return ResponseEntity.noContent().build();
    }
}
