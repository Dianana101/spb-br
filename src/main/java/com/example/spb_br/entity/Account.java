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
    private long id;

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

    // реализация State, по умолчанию ставим счету статус Активный
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

    // здесь в зависимости от состояния, в котором находится счет будет разрешена либо отклонена операция с ним
    public void performAction() {
        state.performAction(this);
    }
}
