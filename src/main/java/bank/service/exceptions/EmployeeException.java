package bank.service.exceptions;

public class EmployeeException extends  Exception {
    public EmployeeException(String type, String message) {super(String.format("Ошибка при работе с банком. Суть ошибки: %s. Содержимое ошибки: %s",type, message));}
}
