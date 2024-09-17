package br.com.project.futureBank.entity.DTOS;

import java.math.BigDecimal;

public record TransferValueRequestDTO(String cpf, String password, String cpfToReceive, BigDecimal value, String token) {
}
