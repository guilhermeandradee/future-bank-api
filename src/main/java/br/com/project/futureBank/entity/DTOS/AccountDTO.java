package br.com.project.futureBank.entity.DTOS;

import br.com.project.futureBank.entity.Account;

import java.math.BigDecimal;

public record AccountDTO(String cpf, BigDecimal balance, String adress, String password) {

}
