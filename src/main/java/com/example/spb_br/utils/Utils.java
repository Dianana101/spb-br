package com.example.spb_br.utils;

import com.example.spb_br.constant.Bonuses;
import com.example.spb_br.constant.PurchaseType;
import com.example.spb_br.exception.FailedPurchaseException;

public class Utils {
    public static double makePurchase(double balance, double price) {
        if (balance - price < 0) {
            throw new FailedPurchaseException("Not enough money to make a purchase!");
        }
        return balance - price;
    }

    public static double earnBonuses(double price, PurchaseType type) {
        if (type == PurchaseType.ONLINE_PURCHASE) {
            if (price < 20) {
                return Utils.getBonus(price, Bonuses.ONLINE_COMISSION);
            }
            if (price > 300) {
                return Utils.getBonus(price, Bonuses.ONLINE_CASHBACK);
            }
            return Utils.getBonus(price, Bonuses.ONLINE_BONUS);
        }
        return Utils.getBonus(price, Bonuses.CASH_BONUS);
    }

    private static double getBonus(double price, double bonus) {
        return price * (bonus / 100);
    }
}
