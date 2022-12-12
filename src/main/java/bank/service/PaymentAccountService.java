package bank.service;

import bank.entity.Bank;
import bank.entity.PaymentAccount;
import bank.entity.User;

public interface PaymentAccountService {
    /*Добавление суммы денег на платёжный счёт*/
    void addMoney(PaymentAccount payAcc, Double sumMoney);

    /*Вычитание суммы денег с платёжного счёта*/
    Boolean subtractMoney(PaymentAccount payAcc, Double sumMoney);

    //ввести новый платёжный счёт
    Boolean addPayment(Integer id, User user, Bank bank );

    Boolean DeletePayment(User user, Bank bank, PaymentAccount paymentAccount);
}
