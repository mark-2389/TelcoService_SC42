package it.polimi.db2.telcoservice_sc42.exception;

public class ClientNotCorrespondingException extends Throwable {
    String info;

    public ClientNotCorrespondingException(String s) {
        this.info = s;
    }
}
