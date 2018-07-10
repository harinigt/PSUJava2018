package edu.pdx.cs410J.gharini;

public class InvalidDateAndTimeException extends RuntimeException {
    public InvalidDateAndTimeException(){

    }

    public  InvalidDateAndTimeException(String msg){
        super(msg);
    }
}
