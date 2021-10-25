package com.seucontrolefinanceiro.repository;

import com.seucontrolefinanceiro.domain.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {

    List<Bill> findByUserId(String id);
}
