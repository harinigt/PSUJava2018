package edu.pdx.cs410J.gharini;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneBill } class.
 */
public class PhoneBillTest {

    @Test
    public void testGetCustomer(){
        String customer = "Harry";
        PhoneBill bill = new PhoneBill(customer);
        assertThat (bill.getCustomer (),containsString (customer));
    }
}
