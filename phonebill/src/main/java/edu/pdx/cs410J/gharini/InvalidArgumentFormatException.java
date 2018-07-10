package edu.pdx.cs410J.gharini;

public class InvalidArgumentFormatException extends RuntimeException{
    public InvalidArgumentFormatException(){

    }
    public InvalidArgumentFormatException(String msg){
        super(msg);
    }
}
