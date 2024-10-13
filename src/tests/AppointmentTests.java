package tests;

import obj.Appointment;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

public class AppointmentTests {

    private Appointment appointment;

    @Before
    public void setup() {
        LocalDate start = LocalDate.of(2024, 10, 10);
        LocalDate end = LocalDate.of(2024, 10, 12);
        appointment = new Appointment("test", start, end);

    }
    @Test
    public void testBeforeStartDate() {
        LocalDate test = LocalDate.of(2024, 10, 9);
        boolean actual = appointment.occursOn(test);
        boolean expected = false;
        assertEquals(expected, actual);
    }
    @Test
    public void testOnStartDate() {
        LocalDate test = LocalDate.of(2024, 10, 10);
        boolean actual = appointment.occursOn(test);
        boolean expected = true;
        assertEquals(expected, actual);
    }
    @Test
    public void testBetweenStartAndEndDate() {
        LocalDate test = LocalDate.of(2024, 10, 11);
        boolean actual = appointment.occursOn(test);
        boolean expected = true;
        assertEquals(expected, actual);
    }
    @Test
    public void testOnEndDate() {
        LocalDate test = LocalDate.of(2024, 10, 12);
        boolean actual = appointment.occursOn(test);
        boolean expected = true;
        assertEquals(expected, actual);
    }
    @Test
    public void testAfterEndDate() {
        LocalDate test = LocalDate.of(2024, 10, 13);
        boolean actual = appointment.occursOn(test);
        boolean expected = false;
        assertEquals(expected, actual);
    }
}