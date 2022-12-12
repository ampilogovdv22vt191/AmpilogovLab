package bank.service.impl;

import bank.entity.Bank;
import bank.entity.PaymentAccount;
import bank.entity.User;
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
    public Boolean subtractMoney(PaymentAccount payAcc, Double sumMoney) {
        if (payAcc.getSum() < sumMoney)
            return Boolean.FALSE;
        payAcc.setSum(payAcc.getSum() - sumMoney);
        return Boolean.TRUE;
    }

    @Override
    public Boolean addPayment(Integer id, User user, Bank bank ){

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
        return true;
    }

    @Override
    public Boolean DeletePayment(User user, Bank bank, PaymentAccount paymentAccount){
        if (paymentAccount.getSum() < 0)
            return false;

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

        return true;
    }
}
