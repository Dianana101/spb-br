package com.example.spb_br.entity;

import com.example.spb_br.constant.AccountStatus;
import com.example.spb_br.constant.Currency;
import com.example.spb_br.state.ActiveState;
import com.example.spb_br.state.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // используем обычный числовой ид, тк h2 не поддерживает UUID
    private long id;

    // @Column(name="user_id", nullable = false, updatable = false)
    // private long userId;

    @Column(name = "bonus_balance")
    private double bonusBalance = 0;

    @Column(name = "main_balance")
    private double mainBalance = 0;

    @Column(name = "currency", nullable = false)
    private Currency currency = Currency.RUR;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Transient
    @ToString.Exclude
    private State<Account> state;

    public Account() {
        this.state = new ActiveState();
    }

    public Account(long id, double bonusBalance, double mainBalance, Currency currency, AccountStatus status) {
        this.id = id;
        this.bonusBalance = bonusBalance;
        this.mainBalance = mainBalance;
        this.currency = currency;
        this.status = status;
        this.state = new ActiveState();
    }

    public void performAction() {
        state.performAction(this);
    }
}
