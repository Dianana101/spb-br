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

    // используем обычный числовой ид, т.к. h2 не поддерживает UUID
    private long id;

    // у счета должна быть привязка к пользователю, но в рамках тестового предполагаем, что юзер один
    // private long userId;

    private double bonusBalance;

    private double mainBalance;

    private Currency currency;

    private AccountStatus status;
}
