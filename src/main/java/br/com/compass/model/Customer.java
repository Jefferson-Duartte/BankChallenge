package br.com.compass.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name field cannot be null!")
    @NotBlank(message = "The name field cannot be empty!")
    @Column(nullable = false, length = 50)
    private String name;

    @NotNull(message = "The birthDate field cannot be null!")
    @NotBlank(message = "The birthDate field cannot be empty!")
    @Column(nullable = false)
    private LocalDate birthDate;

    @NotNull(message = "The cpf field cannot be null!")
    @NotBlank(message = "The cpf field cannot be empty!")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @NotNull(message = "The password field cannot be null!")
    @NotBlank(message = "The password field cannot be empty!")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "The phoneNumber field cannot be null!")
    @NotBlank(message = "The phoneNumber field cannot be empty!")
    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BankAccount> accounts = new ArrayList<>();

    public void setAccounts(BankAccount account){
        account.setCustomer(this);
        this.accounts.add(account);
    }

    public Customer(String name, String birthDate, String cpf, String password, String phoneNumber) {
        this.id = null;
        this.name = name;
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.cpf = cpf;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
