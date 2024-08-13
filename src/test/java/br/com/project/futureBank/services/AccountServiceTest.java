package br.com.project.futureBank.services;

import br.com.project.futureBank.entity.Account;
import br.com.project.futureBank.entity.DTOS.AccountDTO;
import br.com.project.futureBank.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Autowired
    @InjectMocks
    private AccountService accountService;

    @Nested
    class findByCpf {

        @Test
        @DisplayName("Should find account by cpf.")
        void shouldFindAccount(){

            AccountDTO accountDTO = new AccountDTO("123", "123@gmail.com", "123");
            Account account = new Account(accountDTO);

            when(accountRepository.findByCpf(any())).thenReturn(account);

            Account result = accountRepository.findByCpf("123");
            assertNotNull(result);
        }

        @Test
        @DisplayName("Shouldnt find acount")
        void ShouldTrhowError() throws Exception {
            AccountDTO accountDTO = new AccountDTO("123", "123@gmail.com", "123");
            Account account = new Account(accountDTO);

            when(accountRepository.findByCpf(any())).thenReturn(null);

            Account result = accountRepository.findByCpf("123");
            assertThat(null == result);
        }
    }

    @Nested
    class transferValue {
        @Test
        @DisplayName("Should transfer amount")
        void shouldTransferValue(){
            Account account = new Account(new AccountDTO("123", "123", "123"));

            Account accountToReceive = new Account(new AccountDTO("111", "111", "111"));

            account.makeDeposit(new BigDecimal(20));

            account.transferValue(new BigDecimal(10));
            accountToReceive.makeDeposit(new BigDecimal(10));


            when(accountRepository.save(account)).thenReturn(account);
            when(accountRepository.save(accountToReceive)).thenReturn(accountToReceive);

            accountRepository.save(account);
            accountRepository.save(accountToReceive);

            assertEquals("R$10", account.getBalance());
            assertEquals("R$10", accountToReceive.getBalance());
        }

        @Test
        @DisplayName("Should trhow exception saying not credit")
        void shouldThrowExceptionNoCredit(){
            Account account = new Account(new AccountDTO("123", "123", "123"));

            Account accountToReceive = new Account(new AccountDTO("111", "111", "111"));

            assertThrows(RuntimeException.class, () -> account.transferValue(new BigDecimal(10)));

        }

        @Test
        @DisplayName("Should withdraw balance")
        void shouldWithdrawBalance(){
            Account account = new Account(new AccountDTO("123", "123", "123"));
            account.makeDeposit(new BigDecimal(15));

            account.withdrawValue(new BigDecimal(10));

            assertEquals(account.getBalance(), "R$5");

            accountRepository.save(account);
        }

        @Test
        @DisplayName("Should throw balance error")
        void shouldThrowNotBalanceError(){
            Account account = new Account(new AccountDTO("123", "123", "123"));

            assertThrows(RuntimeException.class, () -> account.withdrawValue(new BigDecimal(10)));
        }
    }
}
