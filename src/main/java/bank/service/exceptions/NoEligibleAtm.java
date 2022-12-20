package bank.service.exceptions;

public class NoEligibleAtm extends RuntimeException {
    public NoEligibleAtm() {
        super("В выбранном офисе нет банкоматов, удовлетворяющих условиям клиента");
    }
}
