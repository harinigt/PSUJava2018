package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {
  String customer = null;
  String callerNumber = null;
  String calleeNumber = null;
  String startTime = null;
  String endTime = null;
  ArrayList<String> options = new ArrayList<>();

  private static void CheckNumArgs(int count){



  }


  private static void loadOptions(){

  }

  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    System.err.println("Missing command line arguments");

    int numOfArgs = args.length;


    try {
      CheckNumArgs(numOfArgs);
    } catch (InvalidNumberOfArgumentsException ex1){

      System.out.println();

    }





    for (String arg : args) {

    }
    System.exit(1);
  }

}