package com.example.spb_br.mapper;

import com.example.spb_br.dto.AccountDto;
import com.example.spb_br.entity.Account;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class AccountMapper {
    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract("_ -> new")
    public static AccountDto mapToAccountDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getBonusBalance(),
                account.getMainBalance(),
                account.getCurrency(),
                account.getStatus()
        );
    }

    @Contract("_ -> new")
    public static @NotNull Account mapToAccount(AccountDto accountDto) {
        return new Account(
                accountDto.getId(),
                accountDto.getBonusBalance(),
                accountDto.getMainBalance(),
                accountDto.getCurrency(),
                accountDto.getStatus()
        );
    }
}
