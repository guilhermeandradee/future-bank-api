package br.com.project.futureBank.entity.DTOS;

import br.com.project.futureBank.entity.Account;

import java.math.BigDecimal;

public record AccountResponseDTO(Long id, String cpf, String balance, String adress) {
    public AccountResponseDTO(Account account) {
        this(account.getId(), account.getCpf(), account.getBalance(), account.getAdress());
    }
}

