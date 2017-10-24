package model.exeption;

public class DeleteTableException extends Exception {
    public DeleteTableException() {
    }

    public DeleteTableException(String message) {
        super(message);
    }
}
