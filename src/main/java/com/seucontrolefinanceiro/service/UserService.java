package com.seucontrolefinanceiro.service;

import com.seucontrolefinanceiro.domain.model.User;
import com.seucontrolefinanceiro.exception.ObjectNotFoundException;
import com.seucontrolefinanceiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found!"));
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public User update(User newUser) {
        User currentUser = findById(newUser.getId());
        currentUser = updateData(newUser, currentUser.getId());
        return repository.save(currentUser);
    }

    public User updateData(User newUser, String id) {
        return User.builder()
                .id(id)
                .fullName(newUser.getFullName())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .cpf(newUser.getCpf())
                .build();
    }
}
