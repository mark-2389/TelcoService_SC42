package it.polimi.db2.telcoservice_sc42.exception;

public class ClientNotFoundException extends TSException {
    private String reason = "";

    public ClientNotFoundException() { }

    public ClientNotFoundException(String reason) {
        this.reason = reason;
    }
}
