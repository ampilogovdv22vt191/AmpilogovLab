package bank.service.exceptions;

public class CreditException extends RuntimeException {
    public CreditException(String type, String message) {super(String.format("Ошибка при работе с кредитным счётом. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}
