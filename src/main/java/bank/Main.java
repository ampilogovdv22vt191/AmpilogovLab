package bank;

import bank.entity.*;
import bank.service.*;
import bank.service.impl.*;
import bank.utils.FullName;
import bank.utils.StatusATM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ArrayList<Bank> banks = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        BankService bankService = new BankServiceImpl();
        BankOfficeService bankOfficeService = new BankOfficeServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        CreditAccountService creditAccountService = new CreditAccountServiceImpl();
        PaymentAccountService paymentAccountService = new PaymentAccountServiceImpl();
        UserService userService = new UserServiceImpl();

        for (int i = 0; i < 5; i++) {
            banks.add(new Bank(i, String.format("bank_name%d", i)));
            for (int j = 0; j < 3; j++) {
                bankService.addOffice(banks.get(i), new BankOffice(i * 3 + j, String.format("office_name%d", i * 3 + j),
                        String.format("adress%d", i * 3 + j), true, 500.0));
                bankService.addBankATM(banks.get(i), new BankATM(i * 3 + j, String.format("bank_ATM%d", i * 3 + j),
                        StatusATM.Work, 500.0));

                bankOfficeService.addATM(banks.get(i).getBankOffices().get(j), banks.get(i).getBankATMS().get(j));
                for (int k = 0; k < 5; k++) {
                    Employee employee = new Employee(5 * (j + 3 * i) + k, String.format("Ivan%d", 5 * (j + 3 * i) + k),
                            String.format("Serov%d", 5 * (j + 3 * i) + k), new Date(19081917), String.format("work%d", k), (double) 500 * k);

                    bankService.addEmployee(banks.get(i), employee);
                    bankOfficeService.addEmployee(banks.get(i).getBankOffices().get(j), employee);
                }
                employeeService.setWorkerToBankomat(banks.get(i).getBankOffices().get(j).getBankATMS().get(0),
                        banks.get(i).getBankOffices().get(j).getEmployees().get(0));
            }
        }

        for (int i = 0; i < 5; i++) {
            users.add(new User(i, String.format("Sevy%d", i),
                    String.format("Ivanov%d", i), new Date(10112000), String.format("job%d", i)));

            for (int j = 0; j < 2; j++) {

                int bankIndex;
                if (i + j == 5)
                    bankIndex = 0;
                else
                    bankIndex = i + j;

                paymentAccountService.addPayment(i * 2 + j, users.get(i), banks.get(bankIndex));
                creditAccountService.openCredit(i * 2 + j, users.get(i),
                        banks.get(bankIndex).getBankOffices().get(0), banks.get(bankIndex).getBankOffices().get(0).getEmployees().get(0),
                        users.get(i).getPaymentAccounts().get(j), LocalDate.now(), 24, 100.0);
            }
        }

        System.out.println(bankService.getInfo(banks.get(0)));

        System.out.println(userService.getInfo(users.get(0)));
    }
}