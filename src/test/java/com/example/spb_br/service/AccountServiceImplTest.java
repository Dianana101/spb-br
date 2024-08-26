package com.example.spb_br.service;

import com.example.spb_br.constant.AccountStatus;
import com.example.spb_br.constant.Currency;
import com.example.spb_br.constant.PurchaseType;
import com.example.spb_br.dto.AccountDto;
import com.example.spb_br.exception.FailedPurchaseException;
import com.example.spb_br.exception.PerformActionsException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountServiceImplTest {

	@Autowired
	private AccountServiceImpl accountServiceImpl;

	private AccountDto accountDto;

	@BeforeEach
	public void init() {
		// Изначальная сумма в (А) = 5000 рублей.
		AccountDto newAccount = new AccountDto(1l, 0, 5000, Currency.RUR, AccountStatus.ACTIVE);
		accountDto = accountServiceImpl.saveAccount(newAccount);
		assertEquals(this.accountDto.getBonusBalance(), 0.0);
		assertEquals(this.accountDto.getStatus(), AccountStatus.ACTIVE);
	}

	// основной тест-кейс из тз
	@Test
	@Order(1)
	public void getAccountAfterPurchaseTest() {
		long accountId = 1L;
		PurchaseType online = PurchaseType.ONLINE_PURCHASE;
		PurchaseType shop = PurchaseType.SHOP_PURCHASE;
		// 1. Online/100
		this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountId, online, 100);
		assertEquals(this.accountDto.getBonusBalance(), 17.0);
		assertEquals(this.accountDto.getMainBalance(), 4900.0);

		// 2. Shop/120
		this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountId, shop, 120);
		assertEquals(this.accountDto.getBonusBalance(), 29.0);
		assertEquals(this.accountDto.getMainBalance(), 4780.0);

		// 3. Online/301
		this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountId, online, 301);
		assertEquals(this.accountDto.getBonusBalance(), 119.3);
		assertEquals(this.accountDto.getMainBalance(), 4479.0);

		// 4. Online/17
		this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountId, online, 17);
		assertEquals(this.accountDto.getBonusBalance(), 121.0);
		assertEquals(this.accountDto.getMainBalance(), 4462.0);

		// 5. Shop/1000
		this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountId, shop, 1000);
		assertEquals(this.accountDto.getBonusBalance(), 221.0);
		assertEquals(this.accountDto.getMainBalance(), 3462.0);

		// 6. Online/21
		this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountId, online, 21);
		assertEquals(this.accountDto.getBonusBalance(), 224.57);
		assertEquals(this.accountDto.getMainBalance(), 3441.0);

		// 7. Shop/570
		this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountId, shop, 570);
		assertEquals(this.accountDto.getBonusBalance(), 281.57);
		assertEquals(this.accountDto.getMainBalance(), 2871.0);

		// 8. Online/700
		this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountId, online, 700);
		assertEquals(this.accountDto.getBonusBalance(), 491.57);
		assertEquals(this.accountDto.getMainBalance(), 2171.0);
	}

	@Test
	@Order(2)
	void exceptionTesting() {
		// должны свалиться, если пытаемся купить на сумму больше, чем есть на балансе
		FailedPurchaseException exception = assertThrows(FailedPurchaseException.class, () -> {
					this.accountDto = accountServiceImpl.getAccountAfterPurchase(1l, PurchaseType.ONLINE_PURCHASE, 6000);
				});

		assertEquals("Not enough money to make a purchase!", exception.getMessage());
	}

	@Test
	@Order(3)
	void spendBonusMoneyTest() {
		// если недостаточно денег на основном балансе, то снимаем с бонусов
		accountDto.setMainBalance(0);
		accountDto.setBonusBalance(1000);
		accountDto = accountServiceImpl.saveAccount(accountDto);

		this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountDto.getId(), PurchaseType.ONLINE_PURCHASE, 1000);

		assertEquals(this.accountDto.getBonusBalance(), 300.0);
		assertEquals(this.accountDto.getMainBalance(), 0);
	}

	@Test
	@Order(4)
	void performActionOnDeletedAccountTest() {
		// Должны свалиться, если пытаемся купить что-то с заблоченного счета
		PerformActionsException exception = assertThrows(PerformActionsException.class, () -> {
			accountDto.setMainBalance(200);
			accountDto.setBonusBalance(300);
			accountDto.setStatus(AccountStatus.BLOCKED);
			accountDto = accountServiceImpl.saveAccount(accountDto);
			this.accountDto = accountServiceImpl.getAccountAfterPurchase(accountDto.getId(), PurchaseType.ONLINE_PURCHASE, 1000);
		});

		assertEquals("No actions available on blocked account!", exception.getMessage());
	}
}
