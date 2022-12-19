package bank.service;

import bank.entity.Bank;
import bank.entity.PaymentAccount;
import bank.entity.User;

public interface PaymentAccountService {
    /*Добавление суммы денег на платёжный счёт*/
    void addMoney(PaymentAccount payAcc, Double sumMoney);

    /*Вычитание суммы денег с платёжного счёта*/
    void subtractMoney(PaymentAccount payAcc, Double sumMoney);

    //ввести новый платёжный счёт
    void addPayment(Integer id, User user, Bank bank );

    void DeletePayment(User user, Bank bank, PaymentAccount paymentAccount);
}
