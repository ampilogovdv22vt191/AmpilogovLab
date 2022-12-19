package bank.service.exceptions;


public class PaymentException extends Exception {
    public PaymentException(String type, String message) {super(String.format("Ошибка при работе с счётом. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}
