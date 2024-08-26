package com.example.spb_br.service;

import com.example.spb_br.constant.PurchaseType;
import com.example.spb_br.dto.AccountDto;

public interface AccountService {
    public AccountDto saveAccount(AccountDto accountDto);
    public AccountDto findAccountById(long accountId);
    public double getAccountTotal(long accountId);
    public double getAccountBonus(long accountId);
    public AccountDto getAccountAfterPurchase(long accountId, PurchaseType type, double price);
    public AccountDto activateAccount(long accountId);
    public AccountDto blockAccount(long accountId);
}
