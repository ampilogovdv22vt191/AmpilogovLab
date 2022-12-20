package bank.service.exceptions;

public class BadUserRatingException extends RuntimeException {
    public BadUserRatingException(Integer bankRating, Integer userRating) {
        super("Кредитный рейтинг пользователя недостаточно высок для выдачи кредита (рейтинг банка - "
                + bankRating + "; кредитный рейтинг пользователя - " + userRating + ")");
    }
}
