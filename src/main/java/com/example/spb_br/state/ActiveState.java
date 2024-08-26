package com.example.spb_br.state;

import com.example.spb_br.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class ActiveState implements State<Account> {

    @Override
    public void performAction(Account context) {
        System.out.println("Perform action on account with id: " + context.getId());
    }
}
