package bank.service.impl;

import bank.entity.*;
import bank.service.BankService;

import java.util.ArrayList;
import java.util.Objects;

public class BankServiceImpl implements BankService {
    @Override
    public void addMoney(Bank bank, Double sumMoney) {
        Double sum = bank.getMoney();
        bank.setMoney(sum + sumMoney);
    }

    @Override
    public Boolean subtractMoney(Bank bank, Double sumMoney) {
        Double sum = bank.getMoney();
        if (sumMoney > sum)
            return Boolean.FALSE;
        bank.setMoney(sum - sumMoney);
        return Boolean.TRUE;
    }

    @Override
    public Boolean addBankATM (Bank bank, BankATM bankATM){
        if (bankATM.getBank() != null || Objects.equals(bankATM.getBank(),bank))
            return false;


        if (bank.getBankATMS() == null) {
            ArrayList<BankATM> array =new ArrayList<BankATM>();
            array.add(bankATM);
            bank.setBankATMS(array);
        }
        else{
            ArrayList<BankATM> array = bank.getBankATMS();
            array.add(bankATM);
            bank.setBankATMS(array);
        }

        bankATM.setBank(bank);
        return true;
    }

    @Override
    public Boolean deleteBankATM(Bank bank, BankATM bankATM){
        if (!Objects.equals(bankATM.getBank(),bank))
            return false;

        BankOfficeServiceImpl bankOfficeService =new BankOfficeServiceImpl();

        if (bankATM.getBankOffice() != null){
            ArrayList<BankOffice> bankOffices =bank.getBankOffices();
            bankOfficeService.deleteATM(bankOffices.get(bankOffices.indexOf(bankATM.getBankOffice())),bankATM);
            bank.setBankOffices(bankOffices);
        }


        ArrayList<BankATM> bankATMS = bank.getBankATMS();
        bankATMS.remove(bankATM);
        if (bankATMS.size() != 0)
            bank.setBankATMS(bankATMS);
        else
            bank.setBankATMS(null);
        bankATM.setBank(null);
        return true;
    }

    @Override
    public Boolean addEmployee(Bank bank, Employee employee){
        if (employee.getBank() != null || Objects.equals(employee.getBank(),bank))
            return false;

        employee.setDistantWork(true);
        ArrayList<Employee> array;
        if (bank.getEmployees() == null) {
            array =new ArrayList<>();
            array.add(employee);
        }
        else{
            array = bank.getEmployees();
            array.add(employee);
        }
        bank.setEmployees(array);
        employee.setBank(bank);
        return true;
    }

    @Override
    public Boolean deleteEmployee(Bank bank, Employee employee){
        if (!Objects.equals(employee.getBank(),bank))
            return false;

        BankOfficeServiceImpl bankOfficeService =new BankOfficeServiceImpl();

        if (!employee.getDistantWork()){
            ArrayList<BankOffice> bankOffices =bank.getBankOffices();
            bankOfficeService.deleteEmployee(bankOffices.get(bankOffices.indexOf(employee.getBankOffice())),employee);
            bank.setBankOffices(bankOffices);
        }

        ArrayList<Employee> employees = bank.getEmployees();
        employees.remove(employee);
        if (employees.size() != 0)
            bank.setEmployees(employees);
        else
            bank.setEmployees(null);
        bank.getBankATMS().get(bank.getBankATMS().indexOf(employee.getBankATM())).setEmployee(null);
        employee.setBankOffice(null);
        employee.setBankATM(null);
        employee.setBank(null);
        return true;
    }

    @Override
    public Boolean addOffice(Bank bank, BankOffice bankOffice){
        if (Objects.equals(bankOffice.getBank(),bank))
            return false;

        ArrayList<BankOffice> array;
        if (bank.getBankOffices() == null) {
            array =new ArrayList<>();
            array.add(bankOffice);
        }
        else{
            array = bank.getBankOffices();
            array.add(bankOffice);
        }
        bank.setBankOffices(array);
        bankOffice.setBank(bank);
        return true;
    }

    @Override
    public Boolean deleteOffice(Bank bank, BankOffice bankOffice){
        if (!Objects.equals(bankOffice.getBank(),bank))
            return false;

        BankOfficeServiceImpl bankOfficeService =new BankOfficeServiceImpl();
        ArrayList<BankATM> bankATMS = bankOffice.getBankATMS();
        bankATMS.forEach( bankATM -> {
            bankOfficeService.deleteATM(bankOffice,bankATM);
        });

        ArrayList<Employee> employees = bankOffice.getEmployees();
        employees.forEach(employee -> {
            bankOfficeService.deleteEmployee(bankOffice,employee);
        });

        ArrayList<BankOffice> bankOffices = bank.getBankOffices();
        bankOffices.remove(bankOffice);
        if (bankOffices.size() == 0)
            bank.setBankOffices(null);
        else
            bank.setBankOffices(bankOffices);
        bankOffice.setBank(null);
        return true;
    }

    @Override
    public Boolean addUser(Bank bank, User user){
        if (bank.getClients()!= null && bank.getClients().contains(user))
            return false;
        ArrayList<User> bankArray;
        if (bank.getClients() == null) {
            bankArray =new ArrayList<>();
            bankArray.add(user);
        }
        else{
            bankArray = bank.getClients();
            bankArray.add(user);
        }
        bank.setClients(bankArray);


        ArrayList<Bank> userArray;
        if (user.getBanks() == null) {
            userArray =new ArrayList<>();
        }
        else{
            userArray = user.getBanks();
        }
        userArray.add(bank);
        user.setBanks(userArray);
        return true;
    }

    @Override
    public  Boolean deleteUser(Bank bank, User user){

        ArrayList<User> users = bank.getClients();
        users.remove(user);

        if (users.size() == 0)
            bank.setClients(null);
        else
            bank.setClients(users);

        return true;
    }

    @Override
    public String getInfo(Bank bank){
        String res = "";

        res += bank.toString();

        res += "\n\nИнформация об офисах:\n";

        for (BankOffice bankOffice: bank.getBankOffices()){
            res += bankOffice.toString();
            res += "\n\n";
        }

        res += "\n\nИнформация о банкоматах:\n";

        for (BankATM bankATM: bank.getBankATMS()){
            res += bankATM.toString();
            res += "\n\n";
        }

        res += "\nИнформация о сотрудниках:\n";
        for (Employee employee: bank.getEmployees()){
            res += employee.toString();
            res += "\n\n";
        }

        res += "\nИнформация о клиетах:\n";
        for (User user: bank.getClients()){
            res += user.toString();
            res += "\n\n";
        }
        return res;
    }
}
