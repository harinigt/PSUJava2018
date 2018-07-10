package edu.pdx.cs410J.gharini;

public class SameCallerAndCalleeException extends RuntimeException {

    public SameCallerAndCalleeException(){

    }

    public SameCallerAndCalleeException(String msg){
        super(msg);
    }
}
