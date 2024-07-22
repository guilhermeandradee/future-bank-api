package br.com.project.futureBank.repository;

import br.com.project.futureBank.entity.Account;
import br.com.project.futureBank.entity.DTOS.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByCpf(String cpf);
}
