package edu.pdx.cs410J.gharini;
import edu.pdx.cs410J.PhoneBillDumper;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class TextDumper implements PhoneBillDumper<PhoneBill> {

    private File file ;
    private String customer;
    private PhoneCall call;
    public TextDumper(String path , PhoneCall call , String customer){
        this.call= call;
        this.file = new File(path);
        this.customer = customer;
    }
    /**
     *
     * @param phoneBill  : The phone bill to write to the file
     * @throws IOException
     *
     * This method dumps the phone bill to the text file specified in the command line. The text file is checked for all the
     * required validations before the contents are written on to it.
     */
    @Override
    public void dump(PhoneBill phoneBill) throws IOException {
        FileHelper.checkIfFileExistsAndCreateIfOtherwise (this.file);
        BufferedWriter bw = null;
        FileWriter fw = null;
        String temp = FileHelper.readFile (this.file ,this.customer);
        try{
            SimpleDateFormat sdf = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
            fw = new FileWriter(this.file,true);
            bw = new BufferedWriter(fw);
            bw.write(phoneBill.getCustomer ());
            bw.write (",");
            bw.write (this.call.getCaller ());
            bw.write (",");
            bw.write (this.call.getCallee ());
            bw.write (",");
            bw.write (sdf.format (this.call.getStartTime ()));
            bw.write (",");
            bw.write (sdf.format (this.call.getEndTime ()));
            bw.write ("\n");
        }catch (IOException e){
              throw  new FileException ("The file could not be writted : \n" + e.getMessage ());
        } finally {
            bw.close();
            fw.close();
        }


    }

}
