package tests;

import obj.Appointment;
import obj.DailyAppointment;
import obj.MonthlyAppointment;
import obj.OnetimeAppointment;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;

public class AppointmentTests {

    private OnetimeAppointment onetime;
    private DailyAppointment daily;
    private MonthlyAppointment monthly;
    private LocalDate start1;
    private LocalDate end1;

    // Sets the test start date, and test end date
    @Before
    public void setup() {
        start1 = LocalDate.of(2024, 10, 10);
        end1 = LocalDate.of(2024, 10, 20);
        daily = new DailyAppointment("testDaily", start1, end1);
        onetime = new OnetimeAppointment("testOneTime", start1);
        monthly = new MonthlyAppointment("testMonthly", start1, end1);

    }

    // Tests a date before the start date
    @Test
    public void testBeforeStartDate() {
        LocalDate date = LocalDate.of(2024, 10, 9);
        assertFalse(daily.occursOn(date));
        assertFalse(onetime.occursOn(date));
        assertFalse(monthly.occursOn(date));
    }

    // Tests a date on the start date
    @Test
    public void testOnStartDate() {
        assertTrue(daily.occursOn(start1));
        assertTrue(onetime.occursOn(start1));
        assertTrue(monthly.occursOn(start1));
    }

    // Tests a date between start and end date
    @Test
    public void testBetweenStartAndEndDate() {
       LocalDate date = LocalDate.of(2024, 10, 15);
       assertTrue(daily.occursOn(date));
       assertFalse(onetime.occursOn(date));
       assertFalse(monthly.occursOn(date));
    }

    // Tests a date on the end date
    @Test
    public void testOnEndDate() {
        assertTrue(daily.occursOn(end1));
        assertFalse(onetime.occursOn(end1));
        assertFalse(monthly.occursOn(end1));
    }

    // Tests a date after the end date
    @Test
    public void testAfterEndDate() {
       LocalDate date = LocalDate.of(2024, 10, 21);
       assertFalse(daily.occursOn(date));
       assertFalse(onetime.occursOn(date));
       assertFalse(monthly.occursOn(date));
    }

    // Tests constructor of OnetimeAppointment class
    @Test
    public void testOneTimeAppointmentConstructor() {
        LocalDate date1 = onetime.getStartDate();
        LocalDate date2 = onetime.getEndDate();
        boolean start = onetime.occursOn(date1);
        boolean end = onetime.occursOn(date2);
        assertEquals(start, end);
    }

    // Tests occursOn method for MonthlyAppointment
    @Test
    public void testMonthlyAppointmentOccursOn() {
        LocalDate date = LocalDate.of(2024, 10, 10);
        assertTrue(monthly.occursOn(date));
    }

    // Tests occursOn method for OnetimeAppointment
    @Test
    public void testOnetimeAppointmentOccursOn() {
        LocalDate date = LocalDate.of(2024, 10, 10);
        assertTrue(onetime.occursOn(date));
    }

    // Tests comparison of appointment objects
    @Test
    public void testCompareTo() {
        LocalDate start1 = LocalDate.of(2024, 10, 22);
        LocalDate start2 = LocalDate.of(2024, 10, 29);
        LocalDate end1 = LocalDate.of(2024, 10, 30);
        LocalDate end2 = LocalDate.of(2024, 10, 31);

        Appointment a1 = new OnetimeAppointment("appointment3", start2);
        Appointment a2 = new DailyAppointment("appointment2", start1, end1);
        Appointment a3 = new MonthlyAppointment("appointment4", start2, end2);
        Appointment a4 = new MonthlyAppointment("appointment3", start2, end2);

        Appointment[] appointments = {a1, a2, a3, a4};
        Appointment[] expected = {a2,a1,a4,a3};

        Arrays.sort(appointments);

        assertArrayEquals(expected, appointments);
    }
}