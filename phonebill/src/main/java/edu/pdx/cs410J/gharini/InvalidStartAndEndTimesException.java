package edu.pdx.cs410J.gharini;

public class InvalidStartAndEndTimesException extends RuntimeException {

    public InvalidStartAndEndTimesException(){

    }

    public InvalidStartAndEndTimesException(String msg){
        super(msg);
    }
}
