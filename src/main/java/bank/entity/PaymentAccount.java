package bank.entity;

import bank.entity.common.Account;
import bank.entity.jsonClasses.JsonPayAcc;

public class PaymentAccount extends Account {
    private Double sum;

    public PaymentAccount(Integer id, User user, Bank bank) {
        super(id,user,bank);
        this.sum = 0.0D;
    }

    @Override
    public String toString() {
        String str = "id " + id +
                "\nБанк: " + bank.getName() +
                "\nПользователь: " + user.getFullName() +
                "\nСумма денег: " + sum +
                "\n";
        return str;
    }

    public void updateFromJsonClass(JsonPayAcc jsonPayAcc) {
        this.setId(jsonPayAcc.getId());
        this.getBank().setId(jsonPayAcc.getBankID());
        this.getUser().setId(jsonPayAcc.getUserID());
        this.setSum(jsonPayAcc.getSum());
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
