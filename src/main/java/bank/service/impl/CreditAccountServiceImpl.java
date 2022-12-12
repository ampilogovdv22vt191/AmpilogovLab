package bank.service.impl;

import bank.entity.*;
import bank.service.CreditAccountService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class CreditAccountServiceImpl implements CreditAccountService {

    @Override
    public Boolean openCredit(Integer id, User user,BankOffice bankOffice, Employee employee, PaymentAccount paymentAccount,
                              LocalDate startDate, Integer countMonth, Double amount)
    {
        if ((amount > paymentAccount.getBank().getMoney()) || !employee.getCanLend() || !bankOffice.getMayApplyCredit() ||
                !Objects.equals(employee.getBankOffice(), bankOffice) || !Objects.equals(paymentAccount.getBank(), bankOffice.getBank())
        )
            return false;

        CreditAccount creditAccount = new CreditAccount(id,user,paymentAccount.getBank(),employee,paymentAccount,startDate,countMonth,amount);
        ArrayList<CreditAccount> creditAccounts;
        if (user.getCreditAccounts() != null){
            creditAccounts = user.getCreditAccounts();
            boolean isHaveCreditInBank = false;
            for (CreditAccount account : creditAccounts) {
                if (Objects.equals(account.getBank(), paymentAccount.getBank())) {
                    isHaveCreditInBank = true;
                    break;
                }
            }

            if(isHaveCreditInBank)
                return false;

        }
        else
            creditAccounts = new ArrayList<>();
        creditAccounts.add(creditAccount);
        user.setCreditAccounts(creditAccounts);
        return true;
    }

    @Override
    public Boolean closeCredit(User user, CreditAccount creditAccount, LocalDate ourDate){
        if (ourDate.isBefore(creditAccount.getEndDate()))
            return false;

        ArrayList<CreditAccount> creditAccounts = user.getCreditAccounts();
        creditAccounts.remove(creditAccount);
        if (creditAccounts.size() == 0)
            user.setCreditAccounts(null);
        else
            user.setCreditAccounts(creditAccounts);

        return true;
    }
}
