package bank.service.impl;

import bank.entity.BankATM;
import bank.service.AtmService;
import bank.utils.StatusATM;

import java.util.Objects;

public class AtmServiceImpl implements AtmService {

    @Override
    public Boolean addMoney(BankATM bankATM, Double sumMoney) {
        if (!Objects.equals(bankATM.getStatus(), StatusATM.Work)) {
            return Boolean.FALSE;
        }
        bankATM.setMoney(bankATM.getMoney() + sumMoney);
        bankATM.getBankOffice().setMoney(bankATM.getBankOffice().getMoney() + sumMoney);
        bankATM.getBank().setMoney( bankATM.getBank().getMoney() + sumMoney);
        return Boolean.TRUE;
    }


    @Override
    public Boolean subtractMoney(BankATM bankATM, Double sumMoney) {
        if ((Objects.equals(bankATM.getStatus(), StatusATM.NotWork)) || (Objects.equals(bankATM.getStatus(),
                StatusATM.NoMoney)) || (bankATM.getMoney() < sumMoney))
            return Boolean.FALSE;
        if (Objects.equals(bankATM.getMoney(), sumMoney))
            bankATM.setStatus(StatusATM.NoMoney);
        bankATM.setMoney(bankATM.getMoney() - sumMoney);
        bankATM.getBankOffice().setMoney(bankATM.getBankOffice().getMoney() - sumMoney);
        bankATM.getBank().setMoney( bankATM.getBank().getMoney() - sumMoney);
        return Boolean.TRUE;
    }

    @Override
    public void turnOnATM(BankATM bankATM){
        bankATM.setCanDepositMoney(true);
        bankATM.setCanPayMoney(true);
    }

    @Override
    public void turnOffATM(BankATM bankATM){
        bankATM.setCanDepositMoney(false);
        bankATM.setCanPayMoney(false);
    }
}
