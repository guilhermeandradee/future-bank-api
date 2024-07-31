package br.com.project.futureBank.entity;

import br.com.project.futureBank.entity.DTOS.AccountDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account {

    public Account(){

    }

    public Account(AccountDTO accountDTO){
        this.cpf = accountDTO.cpf();
        this.balance = new BigDecimal(0);
        this.adress = accountDTO.adress();
        this.password = accountDTO.password();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private BigDecimal balance;
    private String adress;
    private String password;

    public boolean isAuthenticated(String password){
        return Objects.equals(this.password, password);
    }

    public String makeDeposit(BigDecimal valueToDeposit){
        this.balance = this.balance.add(valueToDeposit);
        return "Valor adicionado -> " + valueToDeposit;
    }

    public String withdrawValue(BigDecimal valueToWithdraw){
        if(this.balance.compareTo(valueToWithdraw) >= 0){
            this.balance = this.balance.subtract(valueToWithdraw);

            return "Saque no valor de  -> " + valueToWithdraw;
        } else {
            return "Saldo insuficiente.";
        }
    }

    public String transferValue(BigDecimal valueToTransfer){
        if (valueToTransfer == null || valueToTransfer.signum() <= 0) {
            throw new RuntimeException("Valor para transferência deve ser maior que zero.");
        }

        if(this.balance.compareTo(valueToTransfer) >= 0){
            this.balance = this.balance.subtract(valueToTransfer);

            return "Transferência no valor de -> " + valueToTransfer;
        } else {

            throw new RuntimeException("Saldo insuficiente!");
        }
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getBalance() {
        return "R$" + balance;
    }

    public String getAdress() {
        return adress;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
