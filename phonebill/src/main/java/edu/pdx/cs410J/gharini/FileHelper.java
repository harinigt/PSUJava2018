package edu.pdx.cs410J.gharini;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
public class FileHelper {


    /**
     *
     * @param phonebill : The path to the file
     *                  This method checks if a text file exists in the location. If the file doesn't exist
     *                  a new text file is created.
     */

    public static void checkIfFileExistsAndCreateIfOtherwise(File phonebill){

        try {
            if(phonebill.exists () && phonebill.isDirectory ())
            {
                throw new FileException ("This is a directory :" + phonebill );
            }
            else if (phonebill.exists () && !phonebill.isFile ()) {
                throw new FileException ("This is not a file :" + phonebill);
            } else
            {
                phonebill.createNewFile ();
            }
        } catch (FileNotFoundException fe){
            PhoneCallHelper.printErrorMessageAndExit (fe.getMessage ());

        }catch (UnsupportedEncodingException ue){
            PhoneCallHelper.printErrorMessageAndExit (ue.getMessage ());
        }
        catch (IOException ie) {
            PhoneCallHelper.printErrorMessageAndExit (ie.getMessage ());
        }

    }

    /**
     *
     * @param phonebill : the text file to read from
     * @return The contents of the file as String
     * This method reads the contents of the file to a string and returns it.
     */


    public static String readFile(File phonebill , String customer){
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(phonebill)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                if(sCurrentLine != null){
                    compareName(sCurrentLine , customer);
                    contentBuilder.append(sCurrentLine).append("\n");
                }
            }
        }
        catch (IOException e) {
            throw new FileException ("The file is malformed : " + phonebill);
        } catch (FileException fe)
        {
            throw new FileException (fe.getMessage ());
        }
        return contentBuilder.toString();
    }

    /**
     *
     * @param line
     * @param customer
     */

    public  static void compareName(String line , String customer){
        String [] values = line.split (",");
        if(!values[0].equals (customer)){
            throw new FileException ("The customer on the file is different : " + customer + " " + values[0]);
        }
    }

    /**
     *
     * @param content
     * @return
     */
    public static HashMap splitContent(String content){
        String [] values ;
        String name = null;
        HashMap<String, ArrayList<ArrayList <String>>> hashmap = new HashMap<String, ArrayList<ArrayList <String>>>();
        ArrayList<ArrayList<String>> callContent = new ArrayList<ArrayList<String>>();
        if(((content.split ("\n")).length ) == 1){
            ArrayList<String> temp = new ArrayList<String> ();
            values = content.split (",");
            name = values[0] ;
            helperToExtractCallContent (values, callContent, temp);
            hashmap.put (name,callContent) ;
            }
            else  if(((content.split ("\n")).length ) > 1){
                 String [] eachCall = (content.split ("\n"));
                 for(String call : eachCall){
                     ArrayList<String> temp = new ArrayList<String> ();
                     values = call.split (",");
                     name = values[0];
                     helperToExtractCallContent (values, callContent, temp);
                 }
                 hashmap.put (name,callContent);

                }

        return hashmap;
    }

    private static void helperToExtractCallContent(String[] values, ArrayList<ArrayList<String>> callContent, ArrayList<String> temp) {
        for(int i=1 ; i < values.length ; i++){
            temp.add (values[i]);
        }
        callContent.add (temp);
    }

    public static PhoneBill loadPhoneBill(HashMap map , String customer){
        String callDetails = map.values ().toString ();
        String[] callDetailz = callDetails.split ("]");
        String callerNumber = null;
        String calleeNumber = null;
        String startDate = null;
        String endDate = null;
        String startTime = null;
        String endTime = null;
        PhoneBill billFromFile  = new PhoneBill (customer);
        for(String callInfo : callDetailz){
            callInfo = callInfo.replace ("[","");
            callInfo= callInfo.trim ();
            String [] eachCallInfo = callInfo.split (",");
            String[] dateTime = null ;
            for(String val : eachCallInfo){
                try{
                    if(callerNumber == null){
                        callerNumber = PhoneCallHelper.getPhoneNumbers (val.trim ());

                    }
                    else if(calleeNumber == null){
                        calleeNumber = PhoneCallHelper.getPhoneNumbers (val.trim ());
                        PhoneCallHelper.checkCallerAndCallee (callerNumber,calleeNumber);
                    }
                    else if(startDate == null){
                        dateTime = val.trim().split (" ");
                        PhoneCallHelper.checkValidArgumentFormat (dateTime[0]);
                        PhoneCallHelper.checkDateFormat (dateTime[0]);
                        startDate = dateTime[0];
                        PhoneCallHelper.checkValidArgumentFormat (dateTime[1]);
                        PhoneCallHelper.checkTimeFormat (dateTime[1]);
                        startTime = dateTime[1];
                    }
                    else if(endDate == null ) {
                        dateTime = val.trim().split (" ");
                        PhoneCallHelper.checkValidArgumentFormat (dateTime[0]);
                        PhoneCallHelper.checkDateFormat (dateTime[0]);
                        endDate = dateTime[0];
                        PhoneCallHelper.checkValidArgumentFormat (dateTime[1]);
                        PhoneCallHelper.checkTimeFormat (dateTime[1]);
                        endTime = dateTime[1];
                        PhoneCallHelper.checkDateDifference (startDate, startTime,endDate,endTime);

                    }
                } catch(InvalidArgumentFormatException iaf){
                    PhoneCallHelper.printErrorMessageAndExit ("The Content on the file" +"\n" + iaf.getMessage ());
                }catch(InvalidPhoneNumberException ipn){
                    PhoneCallHelper.printErrorMessageAndExit ("The Content on the file" +"\n"+ ipn.getMessage ());
                }
                catch (InvalidDateAndTimeException idf){
                    PhoneCallHelper.printErrorMessageAndExit ("The Content on the file" +"\n"  + idf.getMessage ());
                }
                catch (SameCallerAndCalleeException scc){
                    PhoneCallHelper.printErrorMessageAndExit ("The Content on the file" +"\n" +scc.getMessage ());
                }

            }
            PhoneCall callFromFile = new PhoneCall (customer,callerNumber,calleeNumber,startDate,startTime,endDate,endTime);
            billFromFile.addPhoneCall (callFromFile);

        }


        return billFromFile;
    }
}
