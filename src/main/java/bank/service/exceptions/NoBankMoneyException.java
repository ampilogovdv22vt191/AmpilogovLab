package bank.service.exceptions;

public class NoBankMoneyException extends RuntimeException {
    public NoBankMoneyException() {
        super("В банке недостаточно средств для выдачи кредита");
    }
}
