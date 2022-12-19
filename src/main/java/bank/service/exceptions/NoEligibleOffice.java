package bank.service.exceptions;

public class NoEligibleOffice extends Exception {
    public NoEligibleOffice() {
        super("У выбранного банка нет офисов, удовлетворяющих условиям клиента");
    }
}
