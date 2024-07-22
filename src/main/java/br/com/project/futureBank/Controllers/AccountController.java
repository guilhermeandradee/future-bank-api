package br.com.project.futureBank.Controllers;

import br.com.project.futureBank.entity.Account;
import br.com.project.futureBank.entity.DTOS.*;
import br.com.project.futureBank.services.AccountService;
import br.com.project.futureBank.util.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/get-all")
    public ResponseEntity<List<AccountResponseDTO>> getAll(){

        return ResponseEntity.ok().body(accountService.getAllAccounts()
                .stream()
                .map(AccountResponseDTO::new)
                .toList());
    }

    @GetMapping("/get-balance")
    public ResponseEntity<String> getBalance(BalanceDTO balanceDTO){
        try {
            return ResponseEntity.ok().body("Saldo de " + accountService.getBalance(balanceDTO.cpf(), balanceDTO.password()));

        } catch (RuntimeException err){
            return ResponseEntity.badRequest().body("Não foi poss-ivel consultar saldo -> " + err.getMessage());
        }
    }

    @PostMapping("/get-by-cpf")
    public ResponseEntity<ResponseAPI<Account>> getByCpf(@RequestBody AccountCpfDTO accountCpfDTO){
        try {
            Account account = accountService.searchByCpf(accountCpfDTO.cpf());

            ResponseAPI<Account> response = new ResponseAPI<>(account, "Conta encontrada com sucesso", true);

            return ResponseEntity.ok(response);

        } catch (RuntimeException err){
            ResponseAPI<Account> responseAPI = new ResponseAPI<>(null, err.getMessage(), false);

            return ResponseEntity.badRequest().body(responseAPI);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Account> saveAccount(@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.saveAccount(accountDTO));
    }

    @PutMapping("/make-deposit")
    public ResponseEntity<String> makeDeposit(@RequestBody DepositRequestDTO depositRequestDTO){

        try {
            accountService.depositValue(depositRequestDTO.cpf(), depositRequestDTO.password(), depositRequestDTO.value());
            return ResponseEntity.ok().body("Saldo depositado com sucesso!");

        } catch (RuntimeException err){

            return ResponseEntity.badRequest().body("Não foi possível adicionar saldo! -> " + err.getMessage());
        }

    }

    @PutMapping("/withdraw-value")
    public ResponseEntity<String> withdrawValue(@RequestBody DepositRequestDTO depositRequestDTO){
        try {
            accountService.withdrawValue(depositRequestDTO.cpf(), depositRequestDTO.password(), depositRequestDTO.value());

            return ResponseEntity.ok().body("Saque efetuado com sucesso!");
        } catch (RuntimeException err){

            return ResponseEntity.badRequest().body("Não foi possível sacar! -> " + err.getMessage());
        }
    }

    @PutMapping("/transfer-value")
    public ResponseEntity<String> transferValue(@RequestBody TransferValueRequestDTO transferRequestDTO){

        try {
            accountService.transferValue(transferRequestDTO.cpf(), transferRequestDTO.password(), transferRequestDTO.cpfToReceive(), transferRequestDTO.value());

            return ResponseEntity.ok().body("Transferência realizada.");
        } catch (RuntimeException err){
            return ResponseEntity.badRequest().body("A tranferência não foi efetuada -> " + err);
        }
    }

}
