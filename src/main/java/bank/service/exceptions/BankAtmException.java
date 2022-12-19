package bank.service.exceptions;

public class BankAtmException extends Exception {
    public BankAtmException(String type, String message) {super(String.format("Ошибка при работе с банком. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}