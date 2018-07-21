package edu.pdx.cs410J.gharini;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;


public class test {


        public static void main(String[] args) {

            Date curDate = new Date();


            LocalDate datePart = LocalDate.parse("2013-01-02");
            LocalTime timePart = LocalTime.parse("04:05:06");
            LocalDateTime dt = LocalDateTime.of(datePart, timePart);



            String DateToStr = DateFormat.getInstance().format(dt);
            System.out.println(DateToStr);
//
//            DateToStr = DateFormat.getTimeInstance().format(curDate);
//            System.out.println(DateToStr);
//
//            DateToStr = DateFormat.getDateInstance().format(curDate);
//            System.out.println(DateToStr);
//
//            DateToStr = DateFormat.getDateTimeInstance().format(curDate);
//            System.out.println(DateToStr);

//            DateToStr = DateFormat.getTimeInstance(DateFormat.SHORT).format(dt);
//            System.out.println(DateToStr);

//            DateToStr = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(
//                    curDate);
//            System.out.println(DateToStr);

//            DateToStr = DateFormat.getTimeInstance(DateFormat.LONG).format(dt);
//            System.out.println(DateToStr);
//
//            DateToStr = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
//                    DateFormat.SHORT).format(curDate);
//            System.out.println(DateToStr);

//            try {
//                Date strToDate = DateFormat.getDateInstance()
//                        .parse("July 17, 1989");
//                System.out.println(strToDate.toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }

        }
    }

