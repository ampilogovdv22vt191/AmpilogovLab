package bank.service;

import bank.entity.Bank;
import bank.entity.User;

import java.io.IOException;

public interface UserService {
    /*Смена пользователем работы, а соответственно, и заработной платы, и пересчёт его кредитного рейтинга*/
    void changeWork(User user, String newWork, Double newMonthSalary);

    String getInfo(User user);

    void saveToFile(String fileName, Bank bank, User user) throws IOException;
    void updateFromFile(String fileName, User user) throws IOException;

}
