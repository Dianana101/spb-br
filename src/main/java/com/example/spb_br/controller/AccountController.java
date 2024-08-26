package com.example.spb_br.controller;

import com.example.spb_br.constant.PurchaseType;
import com.example.spb_br.dto.AccountDto;
import com.example.spb_br.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<AccountDto> saveAccount(@RequestBody @NonNull AccountDto accountDetails) {
        AccountDto savedAccount = accountService.saveAccount(accountDetails);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long accountId) {
        AccountDto savedAccount = accountService.findAccountById(accountId);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @GetMapping("/bankAccountOfEMoney/{id}")
    public double getAccountBonusBalance(@PathVariable("id") Long accountId) {
        return accountService.getAccountBonus(accountId);
    }

    @GetMapping("/money/{id}")
    public double getAccountTotalBalance(@PathVariable("id") Long accountId) {
        return accountService.getAccountTotal(accountId);
    }

    @GetMapping("/payment/{type}/{price}/{id}")
    public ResponseEntity<AccountDto> getAccountAfterPurchase(@PathVariable("type") String type, @PathVariable("price") double price, @PathVariable("id") Long accountId) {
        AccountDto account = accountService.getAccountAfterPurchase(accountId, PurchaseType.fromValue(type), price);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
