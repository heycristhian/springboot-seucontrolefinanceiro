package com.seucontrolefinanceiro.controller;

import com.seucontrolefinanceiro.domain.dto.request.UserRequest;
import com.seucontrolefinanceiro.domain.dto.response.UserResponse;
import com.seucontrolefinanceiro.domain.model.Bill;
import com.seucontrolefinanceiro.domain.model.User;
import com.seucontrolefinanceiro.exception.ObjectNotFoundException;
import com.seucontrolefinanceiro.service.BillScfService;
import com.seucontrolefinanceiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("scf-service/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private BillScfService billService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> find(String user) {
        System.out.println(user);
        List<User> users = service.findAll();
        return ResponseEntity.ok().body(UserResponse.converter((users)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable String id) {
        User user = service.findById(id);
        return ResponseEntity.ok().body(new UserResponse(user));
    }

    @PostMapping
    public ResponseEntity<UserResponse> insert(@RequestBody @Validated UserRequest form, UriComponentsBuilder uriBuilder) {
        User user = form.converter();
        user = service.save(user);
        URI uri = uriBuilder.path("scf-service/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserResponse(user));
    }

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

    @DeleteMapping("/resetAccount/{id}")
    public ResponseEntity<String> resetAccount(@PathVariable String id) {
        try {
            User user = service.findById(id);
            billService.deleteAll(user.getBills());
            return ResponseEntity.ok("Account reset successfully!");
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody @Validated UserRequest form, UriComponentsBuilder uriBuilder) {
        User user = form.converter();
        user = service.update(user);
        URI uri = uriBuilder.path("scf-service/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{user}/bills")
    public ResponseEntity<List<Bill>> findBills(@PathVariable String user) {
        User userFound = service.findByEmail(user).get();
        return ResponseEntity.ok().body(userFound.getBills());
    }
}
