package bank.service.impl;

import bank.entity.BankATM;
import bank.entity.Employee;
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
    public Boolean setWorkerToBankomat(BankATM bankATM, Employee employee){
        if (!Objects.equals(bankATM.getBankOffice(),employee.getBankOffice()) || bankATM.getBankOffice() == null)
            return false;

        employee.setBankATM(bankATM);
        bankATM.setEmployee(employee);
        AtmServiceImpl atmService = new AtmServiceImpl();
        atmService.turnOnATM(bankATM);
        return  true;
    }

    @Override
    public Boolean removeWorkerFromBankomat(BankATM bankATM, Employee employee){
        if (!Objects.equals(employee.getBankATM(),bankATM))
            return false;

        AtmServiceImpl atmService = new AtmServiceImpl();
        bankATM.setEmployee(null);
        employee.setBankATM(null);
        atmService.turnOffATM(bankATM);
        return true;
    }
}
