package com.example.spb_br.state;

import com.example.spb_br.entity.Account;
import com.example.spb_br.exception.PerformActionsException;
import org.springframework.stereotype.Component;

@Component
public class BlockedState implements State<Account> {
    @Override
    public void performAction(Account context) {
        throw new PerformActionsException("No actions available on blocked account!");
    }
}
