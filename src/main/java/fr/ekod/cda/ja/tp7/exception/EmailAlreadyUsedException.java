package fr.ekod.cda.ja.tp7.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("Email is already used : " + email);
    }
}
