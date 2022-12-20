package bank.service.exceptions;

public class NoEligibleEmployee extends RuntimeException {
    public NoEligibleEmployee() {
        super("В выбранном офисе нет сотрудников, умеющих выдавать кредиты");
    }
}