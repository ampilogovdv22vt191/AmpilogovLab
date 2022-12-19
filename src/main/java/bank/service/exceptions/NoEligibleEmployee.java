package bank.service.exceptions;

public class NoEligibleEmployee extends Exception {
    public NoEligibleEmployee() {
        super("В выбранном офисе нет сотрудников, умеющих выдавать кредиты");
    }
}