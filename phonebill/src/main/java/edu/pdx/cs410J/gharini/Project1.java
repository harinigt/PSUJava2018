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
     * @param: length of command line arguments (number of arguments)
     * * @return
     */

  private static void checkNumOfArgs(int count){
      if(count < 5 ) {
          throw new InvalidNumberOfArgumentsException ("Too Many Arguments ");
      }else if(count > 7){
          throw new InvalidNumberOfArgumentsException ("Missing Few Arguments ");
      }

  }
    /**
     * @param args
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
      SimpleDateFormat format = new SimpleDateFormat ("yyyy/MM/dd HH:mm" , Locale.US);
      format.setLenient (false);
      try {
        Date date = format.parse(arg);
        format.format(date);
      } catch(ParseException pe){
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


  public static void readme(){
      String readme = "";
      System.out.println (readme);
  }

  public static void printCall(AbstractPhoneCall call){
     System.out.println (call);
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
                checkValidArgumentFormat (args[i]);
                checkPhoneNumberFormat (args[i]);
                callerNumber = args[i];

            } else if (calleeNumber == null){
                checkValidArgumentFormat (args[i]);
                checkPhoneNumberFormat (args[i]);
                calleeNumber = args[i];
                checkCallerAndCallee (callerNumber,calleeNumber);

            } else if (startDate == null){
                checkValidArgumentFormat (args[i]);
                checkDateFormat(args[i]);
                startDate = args[i];

            } else if(endDate == null) {
                checkValidArgumentFormat (args[i]);
                checkDateFormat (args[i]);
                endDate = args[i];
                checkDateDifference (startDate,endDate);

            }
        }
    }
    catch(InvalidArgumentFormatException iaf){
        System.err.println (iaf.getMessage ());
        // iaf.printStackTrace ();
        System.exit (-1);
    }
    catch(InvalidNumberOfArgumentsException ina) {
        System.err.println (ina.getMessage ());
        //ina.printStackTrace ();
        System.exit(-1);

    }
    catch(NoOptionsInTheArgumentException noa){
        System.err.println (noa.getMessage ());
        //noa.printStackTrace ();
        System.exit(-1);
    }
    catch(InvalidPhoneNumberException ipn){
        System.err.println (ipn.getMessage ());
        //ipn.printStackTrace ();
        System.exit(-1);
    }
    catch (InvalidDateAndTimeException idf){
        System.err.println (idf.getMessage ());
        //idf.printStackTrace ();
        System.exit(-1);
    }
    catch (InvalidStartAndEndTimesException ise){
        System.err.println (ise.getMessage ());
        //ise.printStackTrace ();
        System.exit (-1);
    }
    catch (SameCallerAndCalleeException scc){
        System.err.println (scc.getMessage ());
        //scc.printStackTrace ();
        System.exit (-1);
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