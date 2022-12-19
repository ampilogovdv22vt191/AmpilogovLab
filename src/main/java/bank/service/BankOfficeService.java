package bank.service;

import bank.entity.Bank;
import bank.entity.BankATM;
import bank.entity.BankOffice;
import bank.entity.Employee;

public interface BankOfficeService {
    /*Добавление суммы денег в офис, и, соответственно, добавление суммы денег в банк,
    которому принадлежит данный офис*/
    void addMoney(BankOffice office, Double sumMoney);
    /*Вычитание суммы денег из офиса, и, соответственно, вычитание суммы денег из банка, которому принадлежит
     * данный офис, с проверкой того, достаточно ли денег в офисе, чтобы их вычесть. Если не достаточно, то
     * возвращается false, иначе true*/
    void subtractMoney(BankOffice office, Double sumMoney);

    /*Добавление нового банкомата в офис, и, соответственно, добавление нового банкомата в банк, которому
     * принадлежит данный офис, с проверкой того, можно ли добавить в этот офис новый банкомат.
     * Если нельзя достаточно, то возвращается false, иначе true*/
    void addATM(BankOffice office, BankATM bankATM);

    /*Вычитание банкомата из офиса, и, соответственно, вычитание банкомата из банка, которому
     * принадлежит данный офис, если указанный банкомат принадлежит офису*/
    void deleteATM(BankOffice office, BankATM bankATM);

    void addEmployee(BankOffice office, Employee employee);

    void deleteEmployee(BankOffice office, Employee employee);

}