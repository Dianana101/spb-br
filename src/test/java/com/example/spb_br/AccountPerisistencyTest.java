package com.example.spb_br;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.spb_br.constant.AccountStatus;
import com.example.spb_br.entity.Account;
import com.example.spb_br.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import  org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountPerisistencyTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createAccountShouldReturnAccountWithDefaultValues() {
        Account testAccount = new Account();

        entityManager.persist(testAccount);
        entityManager.flush();

        Account persistedAccount = accountRepository.findById(testAccount.getId()).orElseThrow();

        assertThat(persistedAccount.getBonusBalance()).isEqualTo(0);
        assertThat(persistedAccount.getMainBalance()).isEqualTo(0);
        assertThat(persistedAccount.getStatus()).isEqualTo(AccountStatus.ACTIVE);
    }

    @Test
    public void createAccountShouldReturnAccountWithProvidedValues() {
        Account testAccount = new Account();

        testAccount.setBonusBalance(200);
        testAccount.setMainBalance(100);
        testAccount.setStatus(AccountStatus.BLOCKED);

        entityManager.persist(testAccount);
        entityManager.flush();

        Account persistedAccount =  accountRepository.findById(testAccount.getId()).orElseThrow();

        assertThat(persistedAccount.getBonusBalance()).isEqualTo(200);
        assertThat(persistedAccount.getMainBalance()).isEqualTo(100);
        assertThat(persistedAccount.getStatus()).isEqualTo(AccountStatus.BLOCKED);
    }
}
