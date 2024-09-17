package br.com.project.futureBank.Controllers;

import br.com.project.futureBank.entity.Account;
import br.com.project.futureBank.entity.DTOS.*;
import br.com.project.futureBank.services.AccountService;
import br.com.project.futureBank.services.TokenService;
import br.com.project.futureBank.util.ResponseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", originPatterns = "*")
@RequestMapping("account")
public class AccountController {

    @Autowired
    TokenService tokenService;

    private boolean isAuthorized(String token, String cpf) {
        return tokenService.validateToken(token, cpf);
    }

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
            return ResponseEntity.badRequest().body("Não foi possível consultar saldo -> " + err.getMessage());
        }
    }

    @PostMapping ("/get-by-cpf")
    public ResponseEntity<ResponseAPI<Account>> getByCpf(@RequestBody AccountCpfDTO accountCpfDTO){

        if (!isAuthorized(accountCpfDTO.token(), accountCpfDTO.cpf())) {
            ResponseAPI responseAPI = new ResponseAPI<>(null, "O usuário deve estar authenticado!", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseAPI);
        }

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
    public ResponseEntity<ResponseAPI<?>> saveAccount(@RequestBody AccountDTO accountDTO){
        try {
            ResponseAPI<AccountResponseDTO> response = new ResponseAPI<>(accountService.saveAccount(accountDTO), "Conta criada com sucesso", true);

            return ResponseEntity.ok(response);

        } catch (RuntimeException err){

            ResponseAPI<String> response = new ResponseAPI<>(null, err.getMessage(), false);

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/make-deposit")
    public ResponseEntity<ResponseAPI<?>> makeDeposit(@RequestBody DepositRequestDTO depositRequestDTO){

        if (!isAuthorized(depositRequestDTO.token(), depositRequestDTO.cpf())) {
            ResponseAPI responseAPI = new ResponseAPI<>(null, "O usuário deve estar authenticado!", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseAPI);
        }

        try {
            accountService.depositValue(depositRequestDTO.cpf(), depositRequestDTO.password(), depositRequestDTO.value());
            ResponseAPI<Object> responseAPI = new ResponseAPI<>(null, "Saldo depositado com sucesso!", true);

            return ResponseEntity.ok().body(responseAPI);

        } catch (RuntimeException err){

            return ResponseEntity.badRequest().body(new ResponseAPI<>(null, err.getMessage(), false));
        }

    }

    @PutMapping("/withdraw-value")
    public ResponseEntity<ResponseAPI> withdrawValue(@RequestBody DepositRequestDTO depositRequestDTO){

        if (!isAuthorized(depositRequestDTO.token(), depositRequestDTO.cpf())) {
            ResponseAPI responseAPI = new ResponseAPI<>(null, "O usuário deve estar authenticado!", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseAPI);
        }

        try {
            accountService.withdrawValue(depositRequestDTO.cpf(), depositRequestDTO.password(), depositRequestDTO.value());

            ResponseAPI<Object> responseAPI = new ResponseAPI<>(null, "Saque efetuado com sucesso!", true);

            return ResponseEntity.ok().body(responseAPI);

        } catch (RuntimeException err){

            ResponseAPI<Object> responseAPI = new ResponseAPI<>(null, "Não foi possível sacar! -> " + err.getMessage(), false);

            return ResponseEntity.badRequest().body(responseAPI);
        }
    }

    @PutMapping("/transfer-value")
    public ResponseEntity<ResponseAPI<?>> transferValue(@RequestBody TransferValueRequestDTO transferRequestDTO){

        if (!isAuthorized(transferRequestDTO.token(), transferRequestDTO.cpf())) {
            ResponseAPI responseAPI = new ResponseAPI<>(null, "O usuário deve estar authenticado!", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseAPI);
        }

        try {
            accountService.transferValue(transferRequestDTO.cpf(), transferRequestDTO.password(), transferRequestDTO.cpfToReceive(), transferRequestDTO.value());

            ResponseAPI responseAPI = new ResponseAPI<>(null, "Valor transferido com sucesso!", true);

            return ResponseEntity.ok(responseAPI);
        } catch (RuntimeException err){
            return ResponseEntity.badRequest().body(new ResponseAPI<>(null, err.getMessage(), false));
        }
    }

}
