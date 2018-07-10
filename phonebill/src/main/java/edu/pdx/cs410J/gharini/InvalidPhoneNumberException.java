package edu.pdx.cs410J.gharini;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException() {

    }
    public InvalidPhoneNumberException( String msg){
        super(msg);
    }
}
