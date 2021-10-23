package com.seucontrolefinanceiro.controller;

import com.seucontrolefinanceiro.domain.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface ScfController<T, K> {

    ResponseEntity<List<T>> find(String query);

    ResponseEntity<T> findById(@PathVariable String id);

    ResponseEntity<T> insert(@RequestBody @Validated K form, UriComponentsBuilder uriBuilder);

    ResponseEntity<Void> delete(@PathVariable String id);

    ResponseEntity<UserResponse> update(@RequestBody @Validated K form, UriComponentsBuilder uriBuilder);
}
