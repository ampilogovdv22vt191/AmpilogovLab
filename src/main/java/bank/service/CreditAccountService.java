package bank.service;

import bank.entity.*;

import java.time.LocalDate;

public interface CreditAccountService {


    /*Одобрение заявки на кредит. В случае одобрения, если выбранный сотрудник совпадает с сотрудником,
     * фактически оформляющим кредит, если банк имеет достаточную сумму для выдачи кредита и если выбранный
     * сотрудник может оформлять кредиты, то на платёжный счёт пользователя поступает запрошенная сумма, а
     * из банка списывается указанная в заявке сумма. В случае оформления возвращается true, иначе false.*/
    Boolean openCredit(Integer id, User user,BankOffice bankOffice, Employee employee, PaymentAccount paymentAccount,
                       LocalDate startDate, Integer countMonth, Double amount);


    /*Закрыть кредит*/
    Boolean closeCredit(User user, CreditAccount creditAccount, LocalDate ourDate);
}
