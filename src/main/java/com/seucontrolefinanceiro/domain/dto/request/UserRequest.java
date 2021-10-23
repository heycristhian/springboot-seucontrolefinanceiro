package com.seucontrolefinanceiro.domain.dto.request;

import com.seucontrolefinanceiro.domain.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRequest {
    private final String id;
    @NonNull
    private final String fullName;
    @NonNull
    private final String email;
    @NonNull
    private final String password;
    @NonNull
    private final String cpf;

    public User converter() {
        return User.builder()
                .id(this.id)
                .fullName(this.fullName)
                .email(this.email)
                .password(this.password)
                .cpf(this.cpf)
                .build();
    }
}