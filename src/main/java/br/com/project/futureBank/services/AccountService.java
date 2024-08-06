package br.com.project.futureBank.services;

import br.com.project.futureBank.UserProducer.UserProducer;
import br.com.project.futureBank.entity.Account;
import br.com.project.futureBank.entity.DTOS.AccountDTO;
import br.com.project.futureBank.entity.DTOS.AccountResponseDTO;
import br.com.project.futureBank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserProducer userProducer;

    public AccountResponseDTO saveAccount(AccountDTO accountDTO){

        if (accountDTO.password() == null || accountDTO.password().isEmpty() ||
                accountDTO.adress() == null || accountDTO.adress().isEmpty() ||
                accountDTO.cpf() == null || accountDTO.cpf().isEmpty()) {
            throw new RuntimeException("Os campos não podem ser nulos ou vazios!");
        }

        Account account = new Account(accountDTO);
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO(accountRepository.save(account));

        userProducer.publishMessageEmail(account);

        return accountResponseDTO;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public AccountResponseDTO depositValue(String cpf, String password, BigDecimal valueToDeposit){
        Account account = AuthenticateAccount(cpf, password);

        if(valueToDeposit == null){
            throw new RuntimeException("Nenhum valor informado para depósito!");
        }

        account.makeDeposit(valueToDeposit);
        accountRepository.save(account);

        return new AccountResponseDTO(account);
    }

    public AccountResponseDTO withdrawValue(String cpf, String password, BigDecimal valueToWithdraw){
        Account account = AuthenticateAccount(cpf, password);

        if(valueToWithdraw == null){
            throw new RuntimeException("Valor de saque não informado!");
        }

        account.withdrawValue(valueToWithdraw);

        accountRepository.save(account);


        return new AccountResponseDTO(account);
    }

    public List<AccountResponseDTO> transferValue(String cpf, String password, String cpfToReceive, BigDecimal valueToTransfer){
        Account account = AuthenticateAccount(cpf, password);

        Account accountToReceive = searchByCpf(cpfToReceive);

        account.transferValue(valueToTransfer);
        accountToReceive.makeDeposit(valueToTransfer);

        accountRepository.save(account);
        accountRepository.save(accountToReceive);

        List<AccountResponseDTO> listDTO = new ArrayList<>();
        listDTO.add(new AccountResponseDTO(account));
        listDTO.add(new AccountResponseDTO(accountToReceive));

        return listDTO;

    }

    public String getBalance(String cpf, String password){

        if(cpf == null || cpf.isEmpty()){
            throw new RuntimeException("O campo Cpf deve estar preenchido!");

        }

        Account account = AuthenticateAccount(cpf, password);

        return account.getBalance();

    }

    public Account AuthenticateAccount(String cpf, String password){
        try {
            Account account = Optional.ofNullable(accountRepository.findByCpf(cpf))
                    .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

            if(!account.isAuthenticated(password)){
                throw new RuntimeException("Senha incorreta!");
            }
            return account;

        } catch (Exception err) {
            System.err.println("Erro ao autenticar a conta: " + err.getMessage());
            return null;
        }
    }

    public Account searchByCpf(String cpf){
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio");
        }

        return Optional.ofNullable(accountRepository.findByCpf(cpf))
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

}
