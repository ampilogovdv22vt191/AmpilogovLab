package bank.service.impl;

import bank.entity.Bank;
import bank.entity.PaymentAccount;
import bank.entity.User;
import bank.service.exceptions.PaymentException;
import bank.service.BankService;
import bank.service.PaymentAccountService;

import java.util.ArrayList;
import java.util.Objects;

public class PaymentAccountServiceImpl implements PaymentAccountService {

    @Override
    public void addMoney(PaymentAccount payAcc, Double sumMoney) {
        payAcc.setSum(payAcc.getSum() + sumMoney);
    }

    @Override
    public void subtractMoney(PaymentAccount payAcc, Double sumMoney) {
        payAcc.setSum(payAcc.getSum() - sumMoney);
    }

    @Override
    public void addPayment(Integer id, User user, Bank bank ){

        if (user.getPaymentAccounts() != null){
            for (PaymentAccount paymentAccount: user.getPaymentAccounts())
            {
                if (paymentAccount.getBank() == bank)
                    return;
            }
        }
        PaymentAccount paymentAccount = new PaymentAccount(id, user, bank);
        ArrayList<PaymentAccount> paymentAccounts;
        if(user.getPaymentAccounts() == null){
            paymentAccounts = new ArrayList<>();
        }
        else{
            paymentAccounts = user.getPaymentAccounts();
        }
        paymentAccounts.add(paymentAccount);
        BankService bankService = new BankServiceImpl();
        bankService.addUser(bank,user);
        user.setPaymentAccounts(paymentAccounts);
    }

    @Override
    public void DeletePayment(User user, Bank bank, PaymentAccount paymentAccount){
        try{
            if (paymentAccount.getSum() < 0){
                throw new PaymentException("Попытка закрыть счёт", "На счету не погашен долг");
            }
            ArrayList<PaymentAccount> paymentAccounts = user.getPaymentAccounts();
            paymentAccounts.remove(paymentAccount);
            user.setPaymentAccounts(paymentAccounts);
            boolean flag = false;

            for (PaymentAccount account : paymentAccounts) {
                if (Objects.equals(account.getBank(), bank)) {
                    flag = true;
                    break;
                }
            }
            if (!flag){
                BankService bankService = new BankServiceImpl();
                bankService.deleteUser(bank,user);
            }
        } catch (PaymentException e) {
            throw new RuntimeException(e);
        }
    }
}
