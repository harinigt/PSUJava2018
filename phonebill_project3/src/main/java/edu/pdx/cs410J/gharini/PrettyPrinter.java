package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrettyPrinter implements PhoneBillDumper {
    private File file ;
    private String customer;
    private PhoneBill bill;

    /**
     *
     * @param path
     * @param bill
     * @param customer
     */
    public PrettyPrinter(String path , PhoneBill bill , String customer){
        this.bill= bill;
        this.file = new File(path);
        this.customer = customer;
    }

    /**
     *
     * @param bill
     * @param customer
     */
    public PrettyPrinter(PhoneBill bill , String customer){
        this.bill= bill;
        this.customer = customer;
    }

    /**
     *
     * @param bill
     * @throws IOException
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        FileHelper.checkIfFileExistsAndCreateIfOtherwise (this.file);
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{
            fw = new FileWriter(this.file,false);
            bw = new BufferedWriter(fw);
            bw.write(FileHelper.getPrettyContent (this.bill));

        }catch (IOException e){
            e.printStackTrace ();
        } finally {
            bw.close();
            fw.close();
        }

    }

    /**
     *
     * @param bill
     */
    public void dumpPrettyContentToStandardOut(AbstractPhoneBill bill){
        String prettyContent  = FileHelper.getPrettyContent ((PhoneBill)bill);
        System.out.println (FileHelper.getPrettyContent ((PhoneBill)bill));

    }


}
