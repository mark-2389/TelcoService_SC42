package it.polimi.db2.telcoservice_sc42.exception;

public class InvalidChoiceServiceException extends Exception {
    private final String message;

    public InvalidChoiceServiceException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
