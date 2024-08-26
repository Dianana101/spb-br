package com.example.spb_br.service;

import com.example.spb_br.constant.AccountStatus;
import com.example.spb_br.constant.PurchaseType;
import com.example.spb_br.dto.AccountDto;
import com.example.spb_br.entity.Account;
import com.example.spb_br.exception.AccountNotFoundException;

import com.example.spb_br.mapper.AccountMapper;
import com.example.spb_br.repository.AccountRepository;
import com.example.spb_br.state.ActiveState;
import com.example.spb_br.state.BlockedState;
import com.example.spb_br.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Override
    public double getAccountTotal(long accountId) {
        AccountDto account = this.findAccountById(accountId);
        this.setAccountState(account);
        return account.getMainBalance() + account.getBonusBalance();
    }

    @Override
    public double getAccountBonus(long accountId) {
        AccountDto account = this.findAccountById(accountId);
        this.setAccountState(account);
        return account.getBonusBalance();
    }

    @Override
    public AccountDto getAccountAfterPurchase(long accountId, PurchaseType type, double price) {
        AccountDto accountDto = this.findAccountById(accountId);
        this.setAccountState(accountDto);
        Account account = AccountMapper.mapToAccount(accountDto);
//        if (account.getStatus() != AccountStatus.ACTIVE) {
//            throw new FailedPurchaseException("Your account is restricted! No purchase available at the moment.");
//        }

        double mainBalance = account.getMainBalance();
        double bonusBalance = account.getBonusBalance();

        if (mainBalance - price > 0) { // когда денег для оплаты  достаточно на основном счете
            double mainBalanceRest = Utils.makePurchase(mainBalance, price);
            account.setMainBalance(mainBalanceRest);
        } else {
            // когда на основном счете не хватает средств, то снимаем все что можно с основного, и частично с бонусного счета,
            // либо запрос падает с ошибкой о недостатке средств NotSufficientMoneyException
            double bonusBalanceRest = Utils.makePurchase(bonusBalance, price - mainBalance);
            account.setMainBalance(0);
            account.setBonusBalance(bonusBalanceRest);
        }

        // если до этого не упали нигде с ошибкой и не откатили транзакцию,
        // то в рамках той же транзакции вычисляем и начисляем бонус
        double purchaseBonus = Utils.earnBonuses(price, type);
        account.setBonusBalance(account.getBonusBalance() + purchaseBonus);

        // сохраняем обновленный счет в бд
        Account savedAccount = accountRepository.saveAndFlush(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto activateAccount(long accountId) {
        return this.changeAccountStatusAndSave(accountId, AccountStatus.ACTIVE);
    }

    @Override
    public AccountDto blockAccount(long accountId) {
        return this.changeAccountStatusAndSave(accountId, AccountStatus.BLOCKED);
    }

    @Override
    public AccountDto saveAccount(AccountDto accountDto) {
        Account newAccount = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(newAccount);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto findAccountById(long accountId) {
        Account foundAccount = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with id: " + accountId + " does not exist!"));
        return AccountMapper.mapToAccountDto(foundAccount);
    }

    private AccountDto changeAccountStatusAndSave(long accountId, AccountStatus status) {
        AccountDto accountDto = this.findAccountById(accountId);
        Account account = AccountMapper.mapToAccount(accountDto);
        account.setStatus(status);
        Account savedAccount = accountRepository.saveAndFlush(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    private void setAccountState(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        account.setState(account.getStatus() != AccountStatus.ACTIVE ? new BlockedState() : new ActiveState());
        account.performAction();
    }
}
