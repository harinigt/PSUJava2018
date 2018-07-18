package edu.pdx.cs410J.gharini;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.ArrayList;



/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {

    /**
     *
     * @param args : The main method for the project2 takes input as command line arguments(args)
     *
     *             The Phone call and Phone bill object is constructed in this method by parsing the Command line arguments and
     *             performing respective error checks. The Phone call object has Customer , callerNumber , calleeNumber , start Date and end date fields.
     *
     *             The phone bill class has a collection of phone calls and the customer name.
     *
     *             The functionality to write the phone bill to a text file and to read a text file containing
     *             phone bill and convert it to a PhoneBill object is implemented in this project.
     *
     *             All the exceptions thrown during the error checks are handled in the main method and the user is prompted with an appropriate message.
     */
    public static void main(String[] args) {
        String customer = null;
        String callerNumber = null;
        String calleeNumber = null;
        String startDate = null;
        String endDate = null;
        String startTime = null;
        String endTime = null;
        String file = null;

        ArrayList<String> options = new ArrayList<>();
        ArrayList<ArrayList<String>> list;

        try{
            list = PhoneCallHelper.loadOptions(args);
            options = list.get(0);
            file = list.get (1).get (0);
            int numOfArgs = args.length;
            int numOfOptions = options.size();
            int numOfNonOptions = list.get(1).size ();
            PhoneCallHelper.checkNumOfArgs(numOfNonOptions,numOfArgs);
            for (int i = numOfOptions+1; i<args.length ; i++){
                if(customer == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    customer = args[i];

                } else if (callerNumber == null){
                    callerNumber = PhoneCallHelper.getPhoneNumbers (args[i]);

                } else if (calleeNumber == null ){
                    calleeNumber = PhoneCallHelper.getPhoneNumbers (args[i]);
                    PhoneCallHelper.checkCallerAndCallee (callerNumber,calleeNumber);

                } else if (startDate == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkDateFormat (args[i]);
                    startDate = args[i];

                } else if (startTime == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkTimeFormat (args[i]);
                    startTime = args[i];

                }else if(endDate == null ) {
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkDateFormat (args[i]);
                    endDate = args[i];


                }else if (endTime == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkTimeFormat (args[i]);
                    endTime = args[i];
                    PhoneCallHelper.checkDateDifference (startDate, startTime,endDate,endTime);


                }
            }
        }
        catch(InvalidArgumentFormatException iaf){
            PhoneCallHelper.printErrorMessageAndExit (iaf.getMessage ());
        }
        catch(InvalidNumberOfArgumentsException ina) {
            PhoneCallHelper.printErrorMessageAndExit (ina.getMessage ());
        }
        catch(InvalidPhoneNumberException ipn){
            PhoneCallHelper.printErrorMessageAndExit (ipn.getMessage ());
        }
        catch (InvalidDateAndTimeException idf){
            PhoneCallHelper.printErrorMessageAndExit (idf.getMessage ());
        }
        catch (SameCallerAndCalleeException scc){
            PhoneCallHelper.printErrorMessageAndExit (scc.getMessage ());
        }
        catch (InvalidOptionException ioe){
            PhoneCallHelper.printErrorMessageAndExit (ioe.getMessage ());
        }
        catch(Exception e){
            PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
        }
        PhoneCall call = new PhoneCall(customer,callerNumber,calleeNumber,startDate, startTime,endDate,endTime);
        PhoneBill bill = new PhoneBill(customer);
        PhoneBill billFromFile;
        bill.addPhoneCall (call);
        for(String option:options){
            if(option.equals ("-print")){
                PhoneCallHelper.printCall (call);
            }else if(option.equals ("-README")){
                PhoneCallHelper.readme ();
            }else if(option.equals ("-textFile")){
                try {
                    String filePath = new File("").getAbsolutePath ();
                    TextDumper dumper = new TextDumper (filePath.concat ("/"+file),call,customer);
                    dumper.dump (bill);
                    TextParser parser = new TextParser (filePath.concat ("/"+file),customer);
                    billFromFile = parser.parse ();
                    System.out.println ("Phone Bill From the file : " + billFromFile.getPhoneCalls ());
                } catch (IOException e){
                    PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
                } catch (ParserException e) {
                    PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
                } catch (FileException fe){
                    PhoneCallHelper.printErrorMessageAndExit (fe.getMessage ());
                }
            }
        }
        System.exit(1);
    }

}