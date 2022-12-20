package bank.service.impl;

import bank.entity.BankATM;
import bank.service.exceptions.BankAtmException;
import bank.service.AtmService;
import bank.utils.StatusATM;

import java.util.Objects;

public class AtmServiceImpl implements AtmService {

    @Override
    public void addMoney(BankATM bankATM, Double sumMoney) {
        try{
            if (Objects.equals(bankATM.getStatus(), StatusATM.NotWork)) {
                throw new BankAtmException("Попытка добавить деньги", "Банкомат не работает");
            }
            bankATM.setMoney(bankATM.getMoney() + sumMoney);
            bankATM.getBankOffice().setMoney(bankATM.getBankOffice().getMoney() + sumMoney);
            bankATM.getBank().setMoney( bankATM.getBank().getMoney() + sumMoney);
        } catch (BankAtmException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void subtractMoney(BankATM bankATM, Double sumMoney) {
        try{
            if (Objects.equals(bankATM.getStatus(), StatusATM.NotWork) || (Objects.equals(bankATM.getStatus(),
                    StatusATM.NoMoney)))
                throw new BankAtmException("Попытка cнять деньги", "Банкомат не выдаёт денег");
            if (bankATM.getMoney() < sumMoney)
                throw new BankAtmException("Попытка cнять деньги", String.format( "У банка не хватает %f денег",sumMoney-bankATM.getMoney()));
            if (Objects.equals(bankATM.getMoney(), sumMoney))
                bankATM.setStatus(StatusATM.NoMoney);
            bankATM.setMoney(bankATM.getMoney() - sumMoney);
            bankATM.getBankOffice().setMoney(bankATM.getBankOffice().getMoney() - sumMoney);
            bankATM.getBank().setMoney( bankATM.getBank().getMoney() - sumMoney);
        } catch (BankAtmException e) {
            throw new RuntimeException(e);
        }

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
