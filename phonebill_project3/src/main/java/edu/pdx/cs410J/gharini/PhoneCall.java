package edu.pdx.cs410J.gharini;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class PhoneCall extends AbstractPhoneCall {
  private String customer ;
  private String callerNumber ;
  private String calleeNumber ;
  private Date startTime;
  private Date endTime;

  public PhoneCall( String customer,String callerNumber , String calleeNumber,Date startTime,
                   Date endTime ) {

    this.customer = customer;
    this.callerNumber = callerNumber;
    this.calleeNumber = calleeNumber;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public String getCaller() {
    return this.callerNumber;
  }

  @Override
  public String getCallee() {
    return this.calleeNumber;
  }

  @Override
  public String getStartTimeString() {
     return shortFormat (getStartTime ());
  }
  @Override
  public String getEndTimeString() {
     return shortFormat (getEndTime());
  }

  @Override
  public Date getStartTime() {
    return this.startTime;
  }

  @Override
  public Date getEndTime() {
    return this.endTime;
  }

  private static  String shortFormat(Date dateTime){
    DateFormat dateFormat = DateFormat.getDateTimeInstance (DateFormat.SHORT,DateFormat.SHORT,Locale.US);
    dateFormat.setLenient (false);
    return dateFormat.format (dateTime);
  }

}
