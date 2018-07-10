package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project1IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  public void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(-1));
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing few Command Line Arguments"));
  }

   @Test
    public void testTooManyCommandLineArguments() {
        MainMethodResult result = invokeMain("-README","-print","MyName","123-456-7890","234-456-7890","2009/12/12 12:12" ,"2009/12/12 12:20" , "extra args");
        assertThat(result.getExitCode(), equalTo(-1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Command Line has too many arguments"));
    }

    @Test
    public void testValidPhoneNumberFormat1(){
      MainMethodResult result = invokeMain ("-README","-print","MyName","123/456/7890","234-456-7890","2009/12/12 12:12" ,"2009/12/12 12:20");
      assertThat (result.getExitCode (),equalTo (-1));
      assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Phone Number Format , Usage : nnn-nnn-nnnn"));
    }

    @Test
    public void testValidPhoneNumberFormat2(){
        MainMethodResult result = invokeMain ("-README","-print","MyName","123-456-7890","2334-4536-7890","2009/12/12 12:12" ,"2009/12/12 12:20");
        assertThat (result.getExitCode (),equalTo (-1));
        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Phone Number Format , Usage : nnn-nnn-nnnn"));
    }

    @Test
    public  void testValidDateFormat1(){
        MainMethodResult result = invokeMain ("-README","-print","MyName","123-456-7890","234-436-7890","2009-12-12 12:12" ,"2009/12/12 12:20");
        assertThat (result.getExitCode (),equalTo (-1));
        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Date , Usage : yyyy/MM/dd hh:mm"));
    }

    @Test
    public  void testValidDateFormat2(){
        MainMethodResult result = invokeMain ("-README","-print","MyName","123-456-7890","234-436-7890","2009/12/12 12:12" ,"20/2009/12 12:20");
        assertThat (result.getExitCode (),equalTo (-1));
        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Date , Usage : yyyy/MM/dd hh:mm"));
    }

    @Test
    public  void testValidTimeFormat(){
        MainMethodResult result = invokeMain ("-README","-print","MyName","123-456-7890","234-436-7890","2009/12/12 10:00:00" ,"2009/12/12 12:20");
        assertThat (result.getExitCode (),equalTo (-1));
        assertThat (result.getTextWrittenToStandardError (),containsString ("Invalid Date , Usage : yyyy/MM/dd hh:mm"));
    }

}