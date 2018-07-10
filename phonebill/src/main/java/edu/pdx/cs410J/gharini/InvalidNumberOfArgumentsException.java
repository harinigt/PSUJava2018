package edu.pdx.cs410J.gharini;

public class InvalidNumberOfArgumentsException extends RuntimeException {
    public InvalidNumberOfArgumentsException() {
    }

    public InvalidNumberOfArgumentsException( String msg){
        super(msg);


    }

}
