package fr.ekod.cda.ja.tp7.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String email) {
        super("Username not found : " + email);
    }
}
