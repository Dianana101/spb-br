package com.example.spb_br.dto;

import com.example.spb_br.constant.AccountStatus;
import com.example.spb_br.constant.Currency;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    /**
     * Id (используем обычный числовой ид, т.к. h2 не поддерживает UUID)
     */
    private long id;

    /**
     * Количество денег на бонусном балансе
     */
    private double bonusBalance;

    /**
     * Количество денег на основном балансе
     */
    private double mainBalance;

    /**
     * Валюта счетов
     */
    private Currency currency;

    /**
     * Статус счета
     */
    private AccountStatus status;
}
