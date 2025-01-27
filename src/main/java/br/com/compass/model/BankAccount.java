package br.com.compass.model;

import br.com.compass.model.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_account")
@Getter
@Setter
@AllArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(unique = true)
    private String accountNumber;


    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    public BankAccount() {
        this.id = null;
        this.balance = BigDecimal.ZERO;
        this.accountNumber = String.valueOf(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return accountType + " - " + accountNumber;
    }
}
