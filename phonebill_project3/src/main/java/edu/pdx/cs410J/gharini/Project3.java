package edu.pdx.cs410J.gharini;
import edu.pdx.cs410J.ParserException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Project3 {


    public static void main(String[] args){

        String customer_name = null;
        String callerNumber = null;
        String calleeNumber =null;
        String startDate = null;
        String startTime =null;
        String endDate =null;
        String endTime = null;
        String startAmPm = null;
        String endAmPm = null;
        String file = null;
        String prettyFile = null;
        Boolean prettyPrintToFile = false;
        ArrayList<String> options = new ArrayList<>();
        ArrayList<String> nonOptions = new ArrayList<>();
        ArrayList<String> files = new ArrayList<>();
        ArrayList<ArrayList<String>> list = null;
        try{
            list = PhoneCallHelper.loadOptions(args);
            options = list.get(0);
            nonOptions = list.get (1);
            files = list.get (2);
            int numOfOptions = options.size();
            int numOfNonOptions = nonOptions.size ();
            prettyPrintToFile =  PhoneCallHelper.checkifPrettyPrintToFile(args);
            PhoneCallHelper.checkNumOfArgs(numOfNonOptions,args ,prettyPrintToFile);
            int startPt = PhoneCallHelper.getStartPoint(prettyPrintToFile,options,nonOptions,files);
            if(options.contains ("-textFile")){
                file = args[Arrays.asList (args).indexOf ("-textFile") +1];
            }
            for (int i = startPt; i<args.length ; i++){
                if(customer_name == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    customer_name = args[i];
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
                    startTime = args[i];
                }else if (startAmPm == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkAmPmForCase (args[i]);
                    startAmPm = args[i];
                }else if(endDate == null ) {
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkDateFormat (args[i]);
                    endDate = args[i];
                }else if (endTime == null ){
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    endTime = args[i];
                }else if(endAmPm == null ) {
                    PhoneCallHelper.checkValidArgumentFormat (args[i]);
                    PhoneCallHelper.checkAmPmForCase (args[i]);
                    endAmPm = args[i];
                    PhoneCallHelper.checkTimeFormat ( startTime, startAmPm );
                    PhoneCallHelper.checkTimeFormat ( endTime, endAmPm );
                    PhoneCallHelper.checkDateDifference (startDate, startTime + " "+startAmPm,endDate,endTime+ " " +endAmPm);
                } else{
                    String msg = "Extraneous command line argument" ;
                    PhoneCallHelper.printErrorMessageAndExit (msg+" : "+args
                    [i]);
                }
            }
        }
        catch (InvalidArgumentFormatException|InvalidNumberOfArgumentsException|InvalidPhoneNumberException
              |InvalidDateAndTimeException|SameCallerAndCalleeException| InvalidOptionException ex) {
            PhoneCallHelper.printErrorMessageAndExit (ex.getMessage ());
        }
        catch(Exception e){
            PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
        }
        PhoneCall call = new PhoneCall(customer_name,callerNumber,calleeNumber,PhoneCallHelper.convertToDate (startDate,startTime,startAmPm),
                PhoneCallHelper.convertToDate (endDate,endTime,endAmPm));
        PhoneBill bill = new PhoneBill(customer_name);

        PhoneBill billFromFile;
        String filePath = null;
        bill.addPhoneCall (call);

        if(options.contains ("-print")){
            System.out.println ("_______________________________________________________________________________________________________________");
            PhoneCallHelper.printCall (call);
            System.out.println ("_______________________________________________________________________________________________________________");
        }

        if(options.contains ("--README")){
            System.out.println ("_______________________________________________________________________________________________________________");
            PhoneCallHelper.readme ();
            System.out.println ("_______________________________________________________________________________________________________________");
        }

        if(options.contains ("-textFile")){
            try {
                if(!new File (file).isAbsolute ()){
                    filePath = new File("").getAbsolutePath ();
                    TextDumper dumper = new TextDumper (filePath.concat ("/"+file),call,customer_name);
                    dumper.dump (bill);
                    TextParser parser = new TextParser (filePath.concat ("/"+file),customer_name);
                    billFromFile = parser.parse ();
                    // System.out.println (FileHelper.getPrettyContent (billFromFile));
                } else{
                    System.out.println (file);
                    TextDumper dumper = new TextDumper (file,call,customer_name);
                    dumper.dump (bill);
                    TextParser parser = new TextParser (file,customer_name);
                    billFromFile = parser.parse ();
                }
                //System.out.println ("___________________________________________________________________________________________________________");
                //System.out.println ("\nPhone Bill From the file : \n" + billFromFile.getPhoneCalls ());
                //System.out.println ("___________________________________________________________________________________________________________");
            } catch (IOException |ParserException | FileException e){
                PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
            }
        }

        if(options.contains ("-pretty") && nonOptions.contains ("-")) {
            PrettyPrinter pretty_printer = new PrettyPrinter (bill,customer_name);
            pretty_printer.dumpPrettyContentToStandardOut (bill);
        }

        if(prettyPrintToFile){
            try {
                int index = Arrays.asList (args).indexOf ("-pretty");
                prettyFile = args[index + 1];
                if (!new File (prettyFile).isAbsolute ()) {
                    filePath = new File("").getAbsolutePath ();
                    TextDumper dumper = new TextDumper (filePath.concat ("/"+file), call, customer_name);
                    dumper.dump (bill);
                    TextParser parser = new TextParser (filePath.concat ("/"+file), customer_name);
                    billFromFile = parser.parse ();
                    PrettyPrinter pretty_printer = new PrettyPrinter (prettyFile, billFromFile, customer_name);
                    pretty_printer.dump (billFromFile);
                }
                else{
                    TextDumper dumper = new TextDumper (file,call,customer_name);
                    dumper.dump (bill);
                    TextParser parser = new TextParser (file,customer_name);
                    billFromFile = parser.parse ();
                    PrettyPrinter pretty_printer = new PrettyPrinter (prettyFile, billFromFile, customer_name);
                    pretty_printer.dump (billFromFile);
                }
            } catch (IOException|ParserException | FileException e){
                PhoneCallHelper.printErrorMessageAndExit (e.getMessage ());
        }

        }
        System.exit(1);
    }
}
