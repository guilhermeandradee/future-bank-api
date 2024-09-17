package br.com.project.futureBank.entity.DTOS;

import br.com.project.futureBank.util.CustomBigDecimalDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;

public record DepositRequestDTO(String cpf,
                                String password,
                                @JsonDeserialize(using = CustomBigDecimalDeserializer.class) BigDecimal value,
                                String token) {
}
