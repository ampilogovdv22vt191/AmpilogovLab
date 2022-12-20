package bank.service.exceptions;

public class BankOfficeException extends RuntimeException {
    public BankOfficeException(String type, String message) {super(String.format("Ошибка при работе с банком. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}
