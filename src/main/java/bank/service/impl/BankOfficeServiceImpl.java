package bank.service.impl;

import bank.entity.*;
import bank.service.BankOfficeService;

import java.util.ArrayList;
import java.util.Objects;

public class BankOfficeServiceImpl implements BankOfficeService {

    @Override
    public void addMoney(BankOffice office, Double sumMoney) {
        Double sumBank = office.getBank().getMoney();
        Double sumOffice = office.getMoney();
        office.setMoney(sumOffice + sumMoney);
        office.getBank().setMoney(sumBank + sumMoney);
    }

    @Override
    public Boolean subtractMoney(BankOffice office, Double sumMoney) {
        Double sumBank = office.getBank().getMoney();
        Double sumOffice = office.getMoney();
        if (sumOffice < sumMoney)
            return Boolean.FALSE;
        office.setMoney(sumOffice + sumMoney);
        office.getBank().setMoney(sumBank + sumMoney);
        return Boolean.TRUE;
    }


    @Override
    public Boolean addATM(BankOffice office, BankATM bankATM) {
        if (!office.getMaySetATM() || bankATM.getBankOffice() != null
                || !Objects.equals(bankATM.getBank(), office.getBank())
                || office.getBank() == null)
            return false;

        if (office.getBankATMS() == null) {
            ArrayList<BankATM> array = new ArrayList<>();
            array.add(bankATM);
            office.setBankATMS(array);
        }
        else{
            ArrayList<BankATM> array = office.getBankATMS();
            array.add(bankATM);
            office.setBankATMS(array);
        }
        bankATM.setBankOffice(office);
        return true;
    }

    @Override
    public Boolean deleteATM(BankOffice office, BankATM bankATM) {
        if (!Objects.equals(bankATM.getBankOffice(),office))
            return false;
        ArrayList<BankATM> array = office.getBankATMS();
        array.remove(bankATM);
        if (array.size() == 0)
            office.setBankATMS(null);
        else
            office.setBankATMS(array);
        bankATM.setBankOffice(null);
        return true;
    }

    @Override
    public Boolean addEmployee(BankOffice office, Employee employee){
        if (!employee.getDistantWork() || employee.getBankOffice() != null
                || !Objects.equals(employee.getBank(), office.getBank()) || employee.getBank() == null )
            return false;

        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        ArrayList<Employee> array;
        if (office.getEmployees() == null) {
            array = new ArrayList<>();
            array.add(employee);
        }
        else{
            array = office.getEmployees();
            array.add(employee);
        }
        office.setEmployees(array);
        employeeService.toOfficeWork(employee);
        employee.setBankOffice(office);
        return true;
    }

    @Override
    public Boolean deleteEmployee(BankOffice office, Employee employee){

        if (!Objects.equals(employee.getBankOffice(),office))
            return false;

        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        ArrayList<Employee> array = office.getEmployees();
        array.remove(employee);
        if (array.size() == 0)
            office.setBankATMS(null);
        else
            office.setEmployees(array);

        employeeService.toDistantWork(employee);
        employee.setBankOffice(null);
        return true;
    }
}