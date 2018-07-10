package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.AbstractPhoneCall;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

    /**
     * @param: count  length of command line arguments (number of arguments)
     *
     */

  private static void checkNumOfArgs(int count){
      if(count < 5 ) {
          throw new InvalidNumberOfArgumentsException ("Missing few Command Line Arguments ");
      }else if(count > 7){
          throw new InvalidNumberOfArgumentsException ("Command Line has too many arguments ");
      }

  }
    /**
     * @param args  command line arguments
     * @return
     */
  private static ArrayList<String> loadOptions(String[] args){
    ArrayList<String> options = new ArrayList<>();
      for(int i = 0 ; i < args.length ; i++){
          String arg= args[i].toUpperCase();
          if(arg.equals("-README") || arg.equals("-PRINT")){
             options.add(arg);
        }
      }
          //if(options.size() == 0) throw new NoOptionsInTheArgumentException("No Options Provided in the command line arguments");
    return options;
  }

  private static void checkPhoneNumberFormat(String arg){
          String regex = "^\\(?([0-9]{3})\\)?[-\\s]?([0-9]{3})[-\\s]?([0-9]{4})";
          Pattern pattern = Pattern.compile(regex);
          Matcher matcher = pattern.matcher(arg);
          if(!matcher.matches ()){
              throw  new InvalidPhoneNumberException("Invalid Phone Number Format , Usage : nnn-nnn-nnnn");
          }

  }


  private static void checkDateFormat(String arg){
      DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy/MM/dd HH:mm" , Locale.US);
      //LocalDateTime startDateTime = LocalDateTime.parse (arg , dateTimeFormat );
      //SimpleDateFormat format = new SimpleDateFormat ("yyyy/MM/dd HH:mm" , Locale.US);
      //dateTimeFormat.setLenient (false);
      try {
          LocalDateTime startDateTime = LocalDateTime.parse (arg , dateTimeFormat );
        //Date date = format.parse(arg);
          dateTimeFormat.format(startDateTime);
      } catch(Exception pe){
          throw new InvalidDateAndTimeException ("Invalid Date , Usage : yyyy/MM/dd hh:mm " + arg);

      }
  }

  private static void checkValidArgumentFormat(String arg){
      if( arg.startsWith ("-")){
          throw new InvalidArgumentFormatException ("Invalid Argument : Argument cannot start with a - " + arg);
      }
  }


  private static void checkValidName(String arg){
      String regex = "^[\\p{L} .'-]+$";
      Pattern pattern = Pattern.compile (regex);
      Matcher matcher = pattern.matcher (arg);
      if(!matcher.matches ()){
          throw new InvalidNameException ("Invalid Name :" + arg);
      }
  }


  private  static void checkDateDifference(String startDate , String endDate){
      DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy/MM/dd HH:mm" , Locale.US);
      LocalDateTime startDateTime = LocalDateTime.parse (startDate , dateTimeFormat );
      LocalDateTime endDateTime = LocalDateTime.parse (endDate,dateTimeFormat);
      LocalDateTime currDate = LocalDateTime.now ();
      long diff = ChronoUnit.SECONDS.between (startDateTime ,endDateTime);
      long diff1 = ChronoUnit.SECONDS.between (endDateTime , currDate);

      if(diff<0){
          throw new InvalidStartAndEndTimesException ("Invalid Dates , Start Date should be before end date  " + startDate + " " + endDate);
      } else if(diff1<0){
          throw  new InvalidDateAndTimeException ("Invalid end date , end date should be before today!!  " + endDate);
      }
  }

  public  static void checkCallerAndCallee(String caller, String callee){
      if(caller.equals (callee)){
           throw new SameCallerAndCalleeException ("Caller and Callee should be different  :" + caller +" " + callee);
      }

  }

    private static String getStartAndEndDates(String arg) {
        String startDate = null;
        checkValidArgumentFormat (arg);
        checkDateFormat(arg);
        startDate = arg;
        return startDate;
    }

    private static String getPhoneNumbers(String arg) {
        String callerNumber = null;
        checkValidArgumentFormat (arg);
        checkPhoneNumberFormat (arg);
        callerNumber = arg;
        return callerNumber;
    }


    public static void readme(){
      String readme = "";
      System.out.println (readme);
  }

  public static void printCall(AbstractPhoneCall call){
     System.out.println (call);
  }

  public static void printErrorMessageAndExit(String msg){
      System.err.println (msg);
      System.exit (-1);

  }
  public static void main(String[] args) {
    String customer = null;
    String callerNumber = null;
    String calleeNumber = null;
    String startDate = null;
    String endDate = null;
    ArrayList<String> options = new ArrayList<>();

    try{
        int numOfArgs = args.length;
        checkNumOfArgs(numOfArgs);
        options = loadOptions(args);
        int numOfOptions = options.size();
        for (int i = numOfOptions; i<args.length ; i++){
            if(customer == null){
                checkValidArgumentFormat (args[i]);
                checkValidName (args[i]);
                customer = args[i];

            } else if (callerNumber == null){
                callerNumber = getPhoneNumbers (args[i]);

            } else if (calleeNumber == null){
                calleeNumber = getPhoneNumbers (args[i]);
                checkCallerAndCallee (callerNumber,calleeNumber);

            } else if (startDate == null){
                startDate = getStartAndEndDates (args[i]);

            } else if(endDate == null) {
                endDate = getStartAndEndDates (args[i]);
                checkDateDifference (startDate,endDate);

            }
        }
    }
    catch(InvalidArgumentFormatException iaf){
        printErrorMessageAndExit (iaf.getMessage ());
    }
    catch(InvalidNumberOfArgumentsException ina) {
        printErrorMessageAndExit (ina.getMessage ());
    }
    catch(NoOptionsInTheArgumentException noa){
        printErrorMessageAndExit (noa.getMessage ());
    }
    catch(InvalidPhoneNumberException ipn){
        printErrorMessageAndExit (ipn.getMessage ());
    }
    catch (InvalidDateAndTimeException idf){
        printErrorMessageAndExit (idf.getMessage ());
    }
    catch (InvalidStartAndEndTimesException ise){
        printErrorMessageAndExit (ise.getMessage ());
    }
    catch (SameCallerAndCalleeException scc){
        printErrorMessageAndExit (scc.getMessage ());
    }
      PhoneCall call = new PhoneCall(customer,callerNumber,calleeNumber,startDate,endDate);
      PhoneBill bill = new PhoneBill(customer);
      bill.addPhoneCall (call);
      for(String option:options){
         String m = option.toLowerCase ();
         if(m.equals ("-print")){
             printCall (call);
         }else if(m.equals ("-readme")){
             readme ();
         }
      }
    System.exit(1);

  }


}