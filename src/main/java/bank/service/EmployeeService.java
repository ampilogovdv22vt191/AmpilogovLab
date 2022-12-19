package bank.service;

import bank.entity.BankATM;
import bank.entity.BankOffice;
import bank.entity.Employee;

public interface EmployeeService {
    /*Отправка работника на удалённую работу*/
    void toDistantWork(Employee employee);
    /*Отправка работника на работу в офисе*/
    void toOfficeWork(Employee employee);

    /*Разрешить/Запретить выдавать кредиты*/
    void permissionForCredit(Employee employee, Boolean flag);


    //Направить работника обслуживать банкомат
    void setWorkerToBankomat(BankATM bankATM, Employee employee);

    //Прекратить чтобы сотрудник обслуживал банкомат
    void removeWorkerFromBankomat(BankATM bankATM, Employee employee);

}
