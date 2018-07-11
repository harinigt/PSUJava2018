package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.AbstractPhoneCall;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

    /**
     * @param: numOfArgs : count of number of arguments passed in the command line
     * @param: numOfOptions : count of number of options in the command line arguments
     * @param : numOfNonOptions : count of number of non option arguments in the command line.
     *
     * @throws : InvalidNumberOfArgumentsException : The number of arguments passed in is incorrect.
     *
     * This method generates the appropriate exceptions if there are no command line arguments  , few arguments are missing or there are extra
     * arguments. There can no options/1 option or all options provided. The non option arguments can be 0 or 5.
     *
     */

  private static void checkNumOfArgs(int numOfNonOptions , int numOfOptions , int numOfArgs){
      if(numOfArgs ==0){
          throw new InvalidNumberOfArgumentsException ("Missing Command Line Arguments ");
      }else if(numOfNonOptions > 0 && numOfNonOptions < 5) {
          throw new InvalidNumberOfArgumentsException ("Missing few Command Line Arguments ");
      }else if(numOfNonOptions > 5){
          throw new InvalidNumberOfArgumentsException ("Command Line has too many arguments ");
      }

  }
    /**
     * @param args  command line arguments
     * @return list : List of lists ,the list of options and non option arguments.
     * @throws  : InvalidOptionException : The options are invalid/incorrect format.
     *
     * This method checks if the options entered in the command line adhere to the specified format. The options strict match -README -print
     * and are case sensitive. The method throws exceptions if the options are not in the correct format. The method also separates the
     * options and non option arguments.
     */
  private static ArrayList<ArrayList<String>>  loadOptions(String[] args){
    ArrayList<String> options = new ArrayList<>();
    ArrayList<String> nonOptions = new ArrayList<>();
    ArrayList<ArrayList<String>> list = new ArrayList<>();
      for(int i = 0 ; i < args.length ; i++){
          String arg= args[i].toLowerCase ();
          if((arg.contains ("readme") || arg.contains ("print"))&& !arg.startsWith ("-")){
              throw new InvalidOptionException ("Invalid Option , Usage : -README -print :" + arg);
          } else
          if(arg.equals("-readme") || arg.equals("-print")){
              if(args[i].matches ("-README") || args[i].matches ("-print")){
                  options.add (args[i]);
              } else
              {
                  throw new InvalidOptionException ("Options are case sensitive , Usage : -README , -print  :" + args[i]);
              }

        }else{
              nonOptions.add (args[i]);
          }
      }
      list.add (options);
      list.add (nonOptions);

    return list;
  }

    /**
     *
     * @param arg  : The Phone number
     * @throws InvalidPhoneNumberException  : The phone number is not in the correct format.
     *
     * This method checks if the phone numbers have the format nnn-nnn-nnnn. In case the phone numbers are in wrong format an exception
     * with appropriate message is thrown.
     */
  private static void checkPhoneNumberFormat(String arg){
          String regex = "^\\(?([0-9]{3})\\)?[-\\s]?([0-9]{3})[-\\s]?([0-9]{4})";
          Pattern pattern = Pattern.compile(regex);
          Matcher matcher = pattern.matcher(arg);
          if(!matcher.matches ()){
              throw  new InvalidPhoneNumberException("Invalid Phone Number Format , Usage : nnn-nnn-nnnn  : " + arg);
          }

  }

    /**
     *
     * @param caller  : The phone number of the caller.
     * @param callee  : The phone number of the callee.
     * @throws    SameCallerAndCalleeException : The caller and callee has same phone numbers.
     *
     * This method checks if the caller and callee has same phone number. In case they have same phone number
     * a SameCallerAndCalleeException is thrown that gives a message that the phone numbers are same.
     *
     */
    public  static void checkCallerAndCallee(String caller, String callee){
        if(caller.equals (callee)){
            throw new SameCallerAndCalleeException ("Caller and Callee should be different  :" + caller +" " + callee);
        }

    }

    /**
     *
     * @param arg  : The command line argument.
     * @throws InvalidArgumentFormatException : Argument format is wrong exception
     *
     *             This method checks if the command line argument is in the correct format.
     *             The arguments other than options cannot start with a hyphen (-). An exception is thrown
     *             when the argument doesnt have the correct format with an appropriate message.
     */
    private static void checkValidArgumentFormat(String arg){
        if(arg.startsWith ("-")){
            throw new InvalidArgumentFormatException ("Invalid Argument : Argument cannot start with a -  :" + arg);
        }
    }

    /**
     *
     * @param arg  : the date variable as string
     * @throws InvalidDateAndTimeException
     * This method checks of the Start Date/time and end Date/time is in the valid format. The valid format for time date/time
     * is "yyyy/MM/dd HH:mm" . If the dates are in any format other than the specified one , an exception is thrown with the appropriate message.
     */
  private static void checkDateAndTimeFormat(String arg){
      DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy/MM/dd HH:mm" , Locale.US);
      try {
          LocalDateTime startDateTime = LocalDateTime.parse (arg , dateTimeFormat );
          dateTimeFormat.format(startDateTime);
      } catch(Exception pe){
          throw new InvalidDateAndTimeException ("Invalid Date , Usage : yyyy/MM/dd hh:mm " + arg);

      }
  }

    /**
     *
     * @param startDate : The Start time of the phone call.
     * @param endDate   : The end time of the phone call.
     * @throws InvalidDateAndTimeException
     *
     * This method is used the check the difference between the start and end date. It ensures that the end date of phone call is after
     * the start date and the end date of the phone call is before current date and time. In case of failure to conform to the rule and exception
     * stating appropriate reason is thrown.
     */
  private  static void checkDateDifference(String startDate , String endDate){
      DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy/MM/dd HH:mm" , Locale.US);
      LocalDateTime startDateTime = LocalDateTime.parse (startDate , dateTimeFormat );
      LocalDateTime endDateTime = LocalDateTime.parse (endDate,dateTimeFormat);
      LocalDateTime currDate = LocalDateTime.now ();
      long diff = ChronoUnit.SECONDS.between (startDateTime ,endDateTime);
      long diff1 = ChronoUnit.SECONDS.between (endDateTime , currDate);

      if(diff<0){
          throw new InvalidDateAndTimeException ("Invalid Dates , Start Date should be before end date  " + startDate + " " + endDate);
      } else if(diff1<0){
          throw  new InvalidDateAndTimeException ("Invalid end date , end date should be before today!!  " + endDate);
      }
  }

    /**
     *
     * @param arg  : The start and end dates of the phone call.
     * @return date  : The date that is in valid format.
     *
     * This method parses the dates and checks for the valid format.
     */
    private static String getStartAndEndDates(String arg) {
        String date = null;
        checkValidArgumentFormat (arg);
        checkDateAndTimeFormat(arg);
        date = arg;
        return date;
    }

    /**
     *
     * @param arg  : The phone number (caller / callee ) f
     * @return phoneNumber : A valid phone number.
     *
     * This method parses the phone number and checks for the valid format.
     */

    private static String getPhoneNumbers(String arg) {
        String phoneNumber = null;
        checkValidArgumentFormat (arg);
        checkPhoneNumberFormat (arg);
        phoneNumber = arg;
        return phoneNumber;
    }

    /**
     * This method has the readme information and prints it on the console ouput when the command line has -README option.
     */
    public static void readme(){
      String readme = "Course    : Advanced Java Programming.\nName      : Harini Gowdagere Tulaisdas.\nemail     : gharini@pdx.edu\n";
      readme+="Project   : Project1\nObjective :\n";
      readme+="\t\tThe project 1 to implement a simple Phone Call Details generation has two classes phoneCall and phoneBill that represents the details of a phone call and phone bill respectively. " +
              "The phoneCall class has fields to hold the details of a phone call such as  customer name , caller (Phone number) , callee(phone number) ,  the start and end time of the call. " +
              "The phone bill has a collection of phone calls and the name of the customer \n" +
              "\n" +
              "\t\tThe application is invoked in the Project1 class. The Project1 class parses the command line arguments for the input." +
              " The command line arguments has the format [options] <args>. The [options] has two choices, -readme and -print. The other arguments are phone numbers of the caller and callee and " +
              " the start and end times of the phone call. The command line can have either one of the 2 options or both options and no arguments or all arguments for " +
              " a phone call. The program checks for the validity of all the arguments and failure to confirm validity results in a friendly error message. " +
              "\n\n" +
              "\t\tThe -print option in the input prints the phone call and the -readme option prints the readme information.  ";
      System.out.println (readme);
  }

    /**
     *
     * @param call  : An instance of a phone call .
     *
     *              This method prints the details of a phone call. This corresponds to the -print option in the command line arguments.
     */
  public static void printCall(AbstractPhoneCall call){
     System.out.println (call);
  }

    /**
     *
     * @param msg
     *
     * This method prints the error message on the console when an exception is raised . The program terminates with the status code -1 when this
     * method is executed.
     */
  public static void printErrorMessageAndExit(String msg){
      System.err.println (msg);
      System.exit (-1);

  }

  public static void checkArgumentPosition( int i , int numOfOptions){
      if(i == numOfOptions){

      }


  }
    /**
     *
     * @param args : The main method for the project1 takes input as command line arguments(args)
     *
     *             The Phone call and Phone bill object is constructed in this method by parsing the Command line arguments and
     *             performing respective error checks. The Phone call object has Customer , callerNumber , calleeNumber , start Date and end date fields.
     *
     *             The phone bill class has a collection of phone calls and the customer name.
     *
     *             All the exceptions thrown during the error checks are handled in the main method and the user is prompted with an appropriate message.
     */
  public static void main(String[] args) {
    String customer = null;
    String callerNumber = null;
    String calleeNumber = null;
    String startDate = null;
    String endDate = null;
    ArrayList<String> options = new ArrayList<>();
    ArrayList<ArrayList<String>> list = new ArrayList<>();

    try{
        list = loadOptions(args);
        options = list.get(0);
        int numOfArgs = args.length;
        int numOfOptions = options.size();
        int numOfNonOptions = list.get(1).size ();
        checkNumOfArgs(numOfNonOptions,numOfOptions,numOfArgs);
        for (int i = numOfOptions; i<args.length ; i++){
            if(customer == null ){
                checkValidArgumentFormat (args[i]);
                customer = args[i];

            } else if (callerNumber == null){
                callerNumber = getPhoneNumbers (args[i]);

            } else if (calleeNumber == null ){
                calleeNumber = getPhoneNumbers (args[i]);
                checkCallerAndCallee (callerNumber,calleeNumber);

            } else if (startDate == null ){
                startDate = getStartAndEndDates (args[i]);

            } else if(endDate == null ) {
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
    catch(InvalidPhoneNumberException ipn){
        printErrorMessageAndExit (ipn.getMessage ());
    }
    catch (InvalidDateAndTimeException idf){
        printErrorMessageAndExit (idf.getMessage ());
    }
    catch (SameCallerAndCalleeException scc){
        printErrorMessageAndExit (scc.getMessage ());
    }
    catch (InvalidOptionException ioe){
        printErrorMessageAndExit (ioe.getMessage ());
    } catch(Exception e){
        printErrorMessageAndExit ("Argument not in right place");
    }

      PhoneCall call = new PhoneCall(customer,callerNumber,calleeNumber,startDate,endDate);
      PhoneBill bill = new PhoneBill(customer);
      bill.addPhoneCall (call);
      for(String option:options){
         if(option.equals ("-print")){
             printCall (call);
         }else if(option.equals ("-README")){
             readme ();
         }
      }
    System.exit(1);

  }


}