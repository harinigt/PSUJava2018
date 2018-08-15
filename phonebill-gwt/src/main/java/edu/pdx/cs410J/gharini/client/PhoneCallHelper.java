package edu.pdx.cs410J.gharini.client;
import edu.pdx.cs410J.AbstractPhoneCall;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class PhoneCallHelper {
    /**
     * @param msg This method prints the error message on the console when an exception is raised . The program terminates with the status code -1 when this
     *            method is executed.
     */
     static void printErrorMessageAndExit(String msg) {
        System.err.println (msg);


    }

    /**
     * @param call : An instance of a phone call .
     *             <p>
     *             This method prints the details of a phone call. This corresponds to the -print option in the command line arguments.
     */
    static void printCall(AbstractPhoneCall call) {
        System.out.println (call);
    }


    /**
     * This method has the readme information and prints it on the console ouput when the command line has -README option.
     */
    static String readme() {
        String readme = "Course    : Advanced Java Programming.\nName      : Harini Gowdagere Tulaisdas.\nemail     : gharini@pdx.edu\n";
        readme += "Project   : Project4\nObjective :\n";
        readme += "\t\tThe Project 4 involves developing a a REST - ful Phone Bill Web Service. The Project 4 is built on Project 1,2 and 3. The web application allows adding the phone call , " +
                "  searching for all the phone calls between the specified times and displaying all the phone calls for a customer. The application can be accessed through web browser as well as the the command line. " +
                "The command line has the format “[options]<args> “ the optional arguments in this project are -host , -port , -search and -print . The option -host is followed by the host name and the option -port is " +
                "followed by the port number. These arguments are necessary to establish connection with the server. The -search option requires customer name and the start and end times. The other arguments of the phone" +
                " call are similar to the previous projects. The other arguments are used to construct a phone call and add it to the Phonebill. Unlike Previous projects that server stores multiple phone bills and supports" +
                " REST - ful web service. The phone bill information is stored in server and is available as long as the server is available. " +
                "The -print option prints the latest phone call information added to the Phonebill.The -README prints the readme information on the standard output and exits the application. " +
                "\n\nThe -README option in the arguments prints readme information but doesn’t validate any other arguments.\n \tAll the necessary validations for the  other arguments follow the " +
                "same protocol as the previous assignments.";
        return readme;
    }

    /**
     * @param arg : The phone number (caller / callee ) f
     * @return phoneNumber : A valid phone number.
     * <p>
     * This method parses the phone number and checks for the valid format.
     */

    static String getPhoneNumbers(String arg) {
        String phoneNumber = null;
        checkValidArgumentFormat (arg);
        checkPhoneNumberFormat (arg);
        phoneNumber = arg;
        return phoneNumber;
    }





    /**
     * @param arg : The command line argument.
     * @throws InvalidArgumentFormatException : Argument format is wrong exception
     *                                        <p>
     *                                        This method checks if the command line argument is in the correct format.
     *                                        The arguments other than options cannot start with a hyphen (-). An exception is thrown
     *                                        when the argument doesnt have the correct format with an appropriate message.
     */
    static void checkValidArgumentFormat(String arg) {
        if (arg.length () > 1 && arg.startsWith ("-")) {
            throw new InvalidArgumentFormatException ("Invalid Argument : Argument cannot start with a -  :" + arg);
        }
    }

    /**
     * @param arg : The Phone number
     * @throws InvalidPhoneNumberException : The phone number is not in the correct format.
     *                                     <p>
     *                                     This method checks if the phone numbers have the format nnn-nnn-nnnn. In case the phone numbers are in wrong format an exception
     *                                     with appropriate message is thrown.
     */
    static void checkPhoneNumberFormat(String arg) {
        if (!arg.matches ("^\\(?([0-9]{3})\\)?[-\\s]?([0-9]{3})[-\\s]?([0-9]{4})")) {
            throw new InvalidPhoneNumberException ("Invalid Phone Number Format , Usage : nnn-nnn-nnnn  : " + arg);
        }

    }
    /**
     * @param caller : The phone number of the caller.
     * @param callee : The phone number of the callee.
     * @throws SameCallerAndCalleeException : The caller and callee has same phone numbers.
     *                                      <p>
     *                                      This method checks if the caller and callee has same phone number. In case they have same phone number
     *                                      a SameCallerAndCalleeException is thrown that gives a message that the phone numbers are same.
     */
     static void checkCallerAndCallee(String caller, String callee) {
        if (caller.equals (callee)) {
            throw new SameCallerAndCalleeException ("Caller and Callee should be different  :" + caller + " " + callee);
        }

    }
    /**
     *
     * @param ampm : the am/pm argument from the command line
     */
    static void checkAmPmForCase(String ampm){
         if(!ampm.matches (("am|pm"))){
             throw new InvalidDateAndTimeException ("The am/pm is case sensitive : " + ampm);
         }
    }

    /**
     * @param arg : the date variable as string
     * @throws InvalidDateAndTimeException This method checks if the Start Date and end Date of the phone call is in the valid format. The valid format for time date/time
     *                                     is "yyyy/MM/dd " . If the dates are in any format other than the specified one , an exception is thrown with the appropriate message.
     */
     static void checkDateFormat(String arg) {
         DateTimeFormat sdf = DateTimeFormat.getFormat ("MM/dd/yyyy");

        if (!arg.matches ("\\d{2}/\\d{2}/\\d{4}"))
            throw new InvalidDateAndTimeException ("Invalid Date Format , Usage : MM/dd/yyyy  " + arg);
        try {
            sdf.parseStrict(arg);
        } catch ( Exception e) {
            throw new InvalidDateAndTimeException ("Invalid Date , Usage : MM/dd/yyyy  " + arg);

        }
    }

    /**
     * @param time : the time variable as string
     * @param am_pm: the am/pm variable as string
     * @throws InvalidDateAndTimeException This method checks if the Start time and end time is in the valid format. The valid format for time
     *                                     is " HH:mm" . If the times are in any format other than the specified one , an exception is thrown with the appropriate message.
     */

     static void checkTimeFormat(String time , String am_pm) {
        DateTimeFormat sdf = DateTimeFormat.getFormat ("hh:mm a");
        String callTime = time + ' ' + am_pm;

        //String pattrn = "(1[012]|[1-9]):[0-5][0-9](am|pm)";
         String pattrn = "(1[0-2]|[1-9]):[0-5][0-9] (am|pm)";

        if (!callTime.matches (pattrn))
            throw new InvalidDateAndTimeException ("Invalid time format , Time must be in 12 hr format:" + callTime);
        try {
            Date date = sdf.parseStrict (callTime);
        } catch (  Exception  dpe) {
            throw new InvalidDateAndTimeException ("Invalid time , Usage : HH:mm " + callTime);
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
      static void checkDateDifference(String startDate ,String startTime, String endDate, String endTime ){
         String strDate = startDate + " " + startTime;
         String eDate = endDate + " " + endTime;
         DateTimeFormat sdf = DateTimeFormat.getFormat ("MM/dd/yyyy hh:mm a" );

         try {

                 Date d1 = sdf.parseStrict (strDate);
                 Date d2 = sdf.parseStrict (eDate);
                 Date cur_date = new Date ();
                 long diff = d2.getTime () - d1.getTime ();
                 long diff1 = cur_date.getTime () - d2.getTime ();
              if (d1.after (d2)) {
                  throw new InvalidDateAndTimeException ("Invalid Dates , Start Date : "+strDate +" should be before end date : " + eDate);
              }
              else if (d2.after (new Date ())) {
                  throw  new InvalidDateAndTimeException ("Invalid end date , end date " + eDate +" should be before today!!  " );
              }
             }catch (Exception e) {
             throw new InvalidDateAndTimeException ("Invalid date ");
          }

    }

    /**
     *
     * @param date : The date of the phone call
     * @param time : The time of Phone call
     * @param ampm  : am/pm of the phone call
     * @return  : the time formatted using simple date format
     *
     */
    static Date convertToDate(String date , String time , String ampm){
          Date sDate = null;
        try{
            DateTimeFormat sdf =  DateTimeFormat.getFormat ("MM/dd/yyyy hh:mm a");

            sDate = sdf.parseStrict (date+' '+time+' '+ampm);
        }catch (Exception pe){
            throw  new InvalidDateAndTimeException ("Invalid date , Usage : mm/dd/yyyy hh:mm a \n" + date+' '+time+' '+ampm);
        }
      return sDate;
    }


}
