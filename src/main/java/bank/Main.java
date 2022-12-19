package bank;

import bank.entity.*;
import bank.service.*;
import bank.service.impl.*;
import bank.utils.FullName;
import bank.utils.StatusATM;

import java.awt.dnd.DragGestureEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static ArrayList<Double> getCritetiaForBanks(ArrayList<Bank> banks){
        ArrayList<Double> criteria = new ArrayList<>();
        for (Bank bank: banks)
        {
            criteria.add(bank.getBankOffices().size() + bank.getBankATMS().size() +
                    bank.getEmployees().size() + (20 - bank.getInterestRate()));

        }
        return criteria;
    }

    static void mainLab_2()  {
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
                        LocalDate.now(), 24, 100.0);
            }
        }

        System.out.println(bankService.getInfo(banks.get(0)));

        System.out.println(userService.getInfo(users.get(0)));
    }

    static void mainLab_3() {
        Scanner input = new Scanner(System.in);
        ArrayList<Bank> banks = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        BankService bankService = new BankServiceImpl();
        AtmService atmService = new AtmServiceImpl();
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
                atmService.addMoney(banks.get(i).getBankATMS().get(j), 2000.0);

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
            }
        }


        banks.sort(new BankComparatorImpl());
        Collections.reverse(banks);
        ArrayList<Double> criteria = getCritetiaForBanks(banks);

        System.out.println("Введите id банка из представленных на экране. Банки расположены в порядке предпочтения от лучшего к худшему");
        for (int index = 0; index < banks.size(); index++) {
            System.out.println("Информация о банке "+ banks.get(index).getId().toString() + ":\n"+banks.get(index).toString());
            System.out.println("Критерий банка: "+criteria.get(index).toString()+ "\n");
        }
        System.out.print("Выбранный id: ");
        int choseBankID = input.nextInt();
        Bank choseBank = null;
        for (int index = 0; index < banks.size(); index++) {
            if (banks.get(index).getId() == choseBankID) {
                choseBank = banks.get(index);
                break;
            }
            if (index == banks.size()-1)
                choseBank = banks.get(index);
        }


        System.out.println("Выберите id офиса из выбранного вами банка:");
        for (BankOffice bankOffice: choseBank.getBankOffices()) {
            System.out.println("Информация о офисе "+ bankOffice.getId().toString() + ":\n" + bankOffice.toString() +"\n");
        }
        System.out.print("Выбранный id: ");
        int choseOfficeID = input.nextInt();
        BankOffice choseOffice = null;

        for (int index = 0; index < choseBank.getBankOffices().size(); index++) {
            if (choseBank.getBankOffices().get(index).getId() == choseOfficeID) {
                choseOffice = choseBank.getBankOffices().get(index);
                break;
            }
            if (index == banks.size()-1)
                choseOffice = choseBank.getBankOffices().get(index);
        }

        System.out.println("Выберите id сотрудника для выбранного вами офиса:");
        for (Employee employee: choseOffice.getEmployees()) {
            System.out.println("Информация о сотруднике" + employee.getId() + ":\n" + employee.toString() +"\n");
        }
        System.out.print("Выбранный id: ");
        int choseEmployeeID = input.nextInt();
        Employee choseEmployee = null;
        for (int index = 0; index < choseOffice.getEmployees().size(); index++) {
            if (choseOffice.getEmployees().get(index).getId() == choseOfficeID) {
                choseEmployee = choseOffice.getEmployees().get(index);
                break;
            }
            if (index == banks.size()-1)
                choseEmployee = choseOffice.getEmployees().get(index);
        }

        System.out.println("Выберите id пользователя для которого вы будете брать кредит:");
        for (User user: users) {
            System.out.println("Информация о пользователе"+ user.getId() + ":\n" + user.toString() +"\n");
        }
        System.out.print("Выбранный id: ");
        int choseUserID = input.nextInt();
        User choseUser = null;
        for (int index = 0; index < users.size(); index++) {
            if (users.get(index).getId() == choseOfficeID) {
                choseUser = users.get(index);
                break;
            }
            if (index == banks.size()-1)
                choseUser = users.get(index);
        }

        System.out.print("Выберите количество месяцев, в течении которых вы будете покрывать кредит: ");
        int month = input.nextInt();

        System.out.print("Выберите размер кредита: ");
        int amount = input.nextInt();


        System.out.println("Пытаемся открыть кредит");
        creditAccountService.openCredit(1, choseUser, choseOffice, choseEmployee, LocalDate.now(), month, (double)amount );
        System.out.println("Успех");
    }

    public static void main(String[] args) {
        mainLab_3();
    }
}