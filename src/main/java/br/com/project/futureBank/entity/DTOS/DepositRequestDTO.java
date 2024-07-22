package br.com.project.futureBank.entity.DTOS;

import java.math.BigDecimal;

public record DepositRequestDTO(String cpf, String password, BigDecimal value) {
}
