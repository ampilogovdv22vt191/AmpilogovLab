package bank.service.impl;

import bank.entity.*;
import bank.service.exceptions.*;
import bank.service.BankService;
import bank.service.CreditAccountService;
import bank.service.PaymentAccountService;
import bank.service.UserService;
import bank.utils.StatusATM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class UserServiceImpl implements UserService {

    @Override
    public void changeWork(User user, String newWork, Double newMonthSalary) {
        user.setWork(newWork);
        user.setMonthSalary(newMonthSalary);
        int creditRating = 0;
        int startRat = 0;
        int endRat = 1000;
        while ((startRat != 10000) && (creditRating == 0)) {
            if ((newMonthSalary <= endRat) && (newMonthSalary >= startRat))
                creditRating = endRat / 10;
            else {
                startRat += 1000;
                endRat += 1000;
            }
        }
        user.setCreditRating(creditRating);
    }

    @Override
    public String getInfo(User user){
        String res = "";

        res += user.toString();

        res += "\n\nИнформация о платёжных счётах:\n";
        for (PaymentAccount paymentAccount: user.getPaymentAccounts()){
            res += paymentAccount.toString();
            res += "\n\n";
        }

        res += "\n\nИнформация о кредитных счётах:\n";
        for (CreditAccount creditAccount: user.getCreditAccounts()){
            res += creditAccount.toString();
            res += "\n\n";
        }


        return res;
    }

}