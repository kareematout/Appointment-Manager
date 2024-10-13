package tests;
import classes.Appointment;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.Before;

public class AppointmentTests {
    private Appointment appointment;
    @Before
    public void setup() {
        LocalDate start = LocalDate.of(2024, 10, 10);
        LocalDate end = LocalDate.of(2024, 10, 20);
    }

    @Test
    public void testBeforeStartDate() {

    }
}