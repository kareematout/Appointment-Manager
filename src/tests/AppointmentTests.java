package tests;

import obj.Appointment;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

public class AppointmentTests {

    private Appointment appointment;

    @Before
    // Sets the test start date, and test end date
    public void setup() {
        LocalDate start = LocalDate.of(2024, 10, 10);
        LocalDate end = LocalDate.of(2024, 10, 12);
        appointment = new Appointment("test", start, end);

    }
    @Test
    // Tests a date before the start date
    public void testBeforeStartDate() {
        LocalDate test = LocalDate.of(2024, 10, 9);
        boolean actual = appointment.occursOn(test);
        boolean expected = false;
        assertEquals(expected, actual);
    }
    // Tests a date on the start date

    @Test
    public void testOnStartDate() {
        LocalDate test = LocalDate.of(2024, 10, 10);
        boolean actual = appointment.occursOn(test);
        boolean expected = true;
        assertEquals(expected, actual);
    }

    // Tests a date between start and end date
    @Test
    public void testBetweenStartAndEndDate() {
        LocalDate test = LocalDate.of(2024, 10, 11);
        boolean actual = appointment.occursOn(test);
        boolean expected = true;
        assertEquals(expected, actual);
    }
    // Tests a date on the end date
    @Test
    public void testOnEndDate() {
        LocalDate test = LocalDate.of(2024, 10, 12);
        boolean actual = appointment.occursOn(test);
        boolean expected = true;
        assertEquals(expected, actual);
    }
    // Tests a date after the end date
    @Test
    public void testAfterEndDate() {
        LocalDate test = LocalDate.of(2024, 10, 13);
        boolean actual = appointment.occursOn(test);
        boolean expected = false;
        assertEquals(expected, actual);
    }
}