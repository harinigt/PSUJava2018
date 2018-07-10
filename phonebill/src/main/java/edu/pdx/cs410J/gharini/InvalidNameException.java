package edu.pdx.cs410J.gharini;

public class InvalidNameException extends RuntimeException {
   public InvalidNameException(){

   }
   public InvalidNameException(String msg){
       super(msg);
   }
}
