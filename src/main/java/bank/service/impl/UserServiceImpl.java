package bank.service.impl;

import bank.entity.Bank;
import bank.entity.CreditAccount;
import bank.entity.PaymentAccount;
import bank.entity.User;
import bank.entity.jsonClasses.JsonCreditAcc;
import bank.entity.jsonClasses.JsonPayAcc;
import bank.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class UserServiceImpl implements UserService {

    Gson gson = new Gson();
    Type payAccArrType = new TypeToken<ArrayList<JsonPayAcc>>() {}.getType();
    Type credAccArrType = new TypeToken<ArrayList<JsonCreditAcc>>() {}.getType();

    @Override
    public void changeWork(User user, String newWork, Double newMonthSalary) {
        user.setWork(newWork);
        user.setMonthSalary(newMonthSalary);
        int creditRating = 0;
        int startRat = 0;
        int endRat = 1000;
        while ((startRat != 10000) && (creditRating == 0)) {
            if ((newMonthSalary <= endRat) && (newMonthSalary >= startRat))
                creditRating = endRat / 10;
            else {
                startRat += 1000;
                endRat += 1000;
            }
        }
        user.setCreditRating(creditRating);
    }

    @Override
    public String getInfo(User user){
        String res = "";

        res += user.toString();

        res += "\n\nИнформация о платёжных счётах:\n";
        for (PaymentAccount paymentAccount: user.getPaymentAccounts()){
            res += paymentAccount.toString();
            res += "\n\n";
        }

        res += "\n\nИнформация о кредитных счётах:\n";
        for (CreditAccount creditAccount: user.getCreditAccounts()){
            res += creditAccount.toString();
            res += "\n\n";
        }


        return res;
    }

    @Override
    public void saveToFile(String fileName, Bank bank, User user) throws IOException {
        String payAccStr = gson.toJson(this.makeJsonPayAcc(bank.getId(), user));
        String creditAccStr = gson.toJson(this.makeJsonCreditAcc(bank.getId(),user));
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);
        writer.write("Платёжные счета:\n" + payAccStr + "\n\nКредитные счета:\n" + creditAccStr);
        writer.close();
    }

    @Override
    public void updateFromFile(String fileName, User user) throws IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        boolean first = true;
        while (line != null) {
            if (!line.isEmpty()) {
                if (line.charAt(0) == '[') {
                    if (first) {
                        first = false;
                        this.makePayAccFromJson(gson.fromJson(line, payAccArrType), user);
                    } else {
                        this.makeCreditAccFromJson(gson.fromJson(line, credAccArrType), user);
                    }
                }
            }
            line = reader.readLine();
        }
    }


    private ArrayList<JsonPayAcc> makeJsonPayAcc(Integer bankID, User user) {
        ArrayList<JsonPayAcc> jsonPay = new ArrayList<>();
        for (PaymentAccount paymentAccount : user.getPaymentAccounts()) {
            if (Objects.equals(paymentAccount.getBank().getId(), bankID)) {
                jsonPay.add(new JsonPayAcc(paymentAccount));
            }
        }
        return jsonPay;
    }

    private void makePayAccFromJson(ArrayList<JsonPayAcc> jsonPayAcc, User user) {
        ArrayList<PaymentAccount> payAcc = user.getPaymentAccounts();
        if (!jsonPayAcc.isEmpty()) {
            for (int i = 0; i < payAcc.size(); i++) {
                for (int j = 0; j < jsonPayAcc.size(); j++) {
                    if (Objects.equals(payAcc.get(i).getId(), jsonPayAcc.get(j).getId())) {
                        payAcc.get(i).updateFromJsonClass(jsonPayAcc.get(j));
                    }
                }
            }
            user.setPaymentAccounts(payAcc);
        }
    }

    private ArrayList<JsonCreditAcc> makeJsonCreditAcc(Integer bankID, User user) {
        ArrayList<JsonCreditAcc> jsonCredit = new ArrayList<>();
        for (CreditAccount creditAccount : user.getCreditAccounts()) {
            if (Objects.equals(creditAccount.getBank().getId(), bankID)) {
                jsonCredit.add(new JsonCreditAcc(creditAccount));
            }
        }
        return jsonCredit;
    }

    private void makeCreditAccFromJson(ArrayList<JsonCreditAcc> jsonCreditAcc, User user) {
        ArrayList<CreditAccount> creditAccounts = user.getCreditAccounts();
        if (!jsonCreditAcc.isEmpty()) {
            for (int i = 0; i < creditAccounts.size(); i++) {
                for (int j = 0; j < jsonCreditAcc.size(); j++) {
                    if (Objects.equals(creditAccounts.get(i).getId(), jsonCreditAcc.get(j).getId())) {
                        creditAccounts.get(i).updateFromJsonClass(jsonCreditAcc.get(j));
                    }
                }
            }
            user.setCreditAccounts(creditAccounts);
        }
    }

}