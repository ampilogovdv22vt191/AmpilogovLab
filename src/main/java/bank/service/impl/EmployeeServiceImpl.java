package bank.service.impl;

import bank.entity.BankATM;
import bank.entity.Employee;
import bank.service.exceptions.EmployeeException;
import bank.service.EmployeeService;

import java.util.Objects;

public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public void toDistantWork(Employee employee) {
        employee.setDistantWork(Boolean.TRUE);
        permissionForCredit(employee, false);
    }

    @Override
    public void toOfficeWork(Employee employee) {

        employee.setDistantWork(Boolean.FALSE);
        permissionForCredit(employee, true);
    }

    @Override
    public void permissionForCredit(Employee employee, Boolean flag){
        employee.setCanLend(flag);

    }

    @Override
    public void setWorkerToBankomat(BankATM bankATM, Employee employee){
        try{
            if (!Objects.equals(bankATM.getBankOffice(),employee.getBankOffice())){
                throw new EmployeeException("Попытка привязать рабочего с банкоматом","Рабочий и банкомат не находятся в 1 офисе");
            }
            if (Objects.equals(bankATM.getEmployee(),employee))
                return;
            employee.setBankATM(bankATM);
            bankATM.setEmployee(employee);
            AtmServiceImpl atmService = new AtmServiceImpl();
            atmService.turnOnATM(bankATM);
        } catch (EmployeeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeWorkerFromBankomat(BankATM bankATM, Employee employee){
        if (!Objects.equals(employee.getBankATM(),bankATM))
            return;

        AtmServiceImpl atmService = new AtmServiceImpl();
        bankATM.setEmployee(null);
        employee.setBankATM(null);
        atmService.turnOffATM(bankATM);
    }
}
