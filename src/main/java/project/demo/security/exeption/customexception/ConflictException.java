package project.demo.security.exeption.customexception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
