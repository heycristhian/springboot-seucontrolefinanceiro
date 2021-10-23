package com.seucontrolefinanceiro.service;

import com.seucontrolefinanceiro.domain.model.PaymentCategory;
import com.seucontrolefinanceiro.repository.PaymentCategoryRepository;
import com.seucontrolefinanceiro.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class PaymentCategoryScfService {

    @Autowired
    private PaymentCategoryRepository repository;

    public List<PaymentCategory> findAll() {
        return repository.findAll();
    }

    public PaymentCategory findById(String id) {
        Optional<PaymentCategory> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found!"));
    }

    public PaymentCategory save(PaymentCategory paymentCategory) {
        PaymentCategory insert = repository.insert(paymentCategory);
        return insert;
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public PaymentCategory update(PaymentCategory newObj) {
        PaymentCategory currentObj = findById(newObj.getId());
        currentObj = updateData(newObj, currentObj.getId());
        return repository.save(currentObj);
    }

    public PaymentCategory updateData(PaymentCategory newObj, String id) {
        return PaymentCategory.builder()
                .id(id)
                .description(newObj.getDescription())
                .billType(newObj.getBillType())
                .build();
    }

    public List<PaymentCategory> findByDescriptionContainingIgnoreCase(String description) {
        return repository.findByDescriptionContainingIgnoreCase(description);
    }
}
