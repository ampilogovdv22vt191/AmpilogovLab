package bank.service.exceptions;

public class BankException extends RuntimeException {
    public BankException(String type, String message) {super(String.format("Ошибка при работе с банком. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}
