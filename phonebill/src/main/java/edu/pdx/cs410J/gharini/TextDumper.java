package edu.pdx.cs410J.gharini;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
public class TextDumper implements PhoneBillDumper<PhoneBill> {

    //private static String pathname = "/Users/harinirahul/PSUJava2018/phonebill/src/main/java/edu/pdx/cs410J/gharini/";

    private File file ;
    private String customer;

    private PhoneCall call;


    public TextDumper(String path , PhoneCall call , String customer){
        this.call= call;
        this.file = new File(path);
        this.customer = customer;
    }
    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        FileHelper.checkIfFileExistsAndCreateIfOtherwise (this.file);

        BufferedWriter bw = null;
        FileWriter fw = null;
       // System.out.println ("from dumper");
        String temp = FileHelper.readFile (this.file ,this.customer);
        try{
            fw = new FileWriter(this.file,true);
            bw = new BufferedWriter(fw);
            bw.write(phoneBill.getCustomer ());
            bw.write (",");
            bw.write (this.call.getCaller ());
            bw.write (",");
            bw.write (this.call.getCallee ());
            bw.write (",");
            bw.write (this.call.getStartTimeString ());
            bw.write (",");
            bw.write (this.call.getEndTimeString ());
            bw.write ("\n");


        }catch (IOException e){
              e.printStackTrace ();
        } finally {
            bw.close();
            fw.close();
        }


    }

}
