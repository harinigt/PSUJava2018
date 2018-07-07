package edu.pdx.cs410J.gharini;

public class InvalidNumberOfArgumentsException extends RuntimeException {
    public String getInvalidNumberOfArguments() {
        return InvalidNumberOfArguments;
    }

    private final String InvalidNumberOfArguments;

    public InvalidNumberOfArgumentsException( String InvalidNumberOfArguments){
        super(InvalidNumberOfArguments);
        this.InvalidNumberOfArguments = InvalidNumberOfArguments;

    }

}
