package tests;

import obj.Appointment;

import org.junit.Test;
import org.junit.Before;

import java.time.LocalDate;

public class AppointmentTests {

    private Appointment appointment;

    @Before
    public void setup() {
        LocalDate start = LocalDate.of(2024, 10, 10);
        LocalDate end = LocalDate.of(2024, 10, 20);
        appointment = new Appointment("test", start, end);
    }

    @Test
    public void testBeforeStartDate() {
        // Todo: Test on the appointment object with date before the start which occursOn should return false.
    }
}