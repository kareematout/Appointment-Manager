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

    /*
        Milestone 2 Tests:

        Tests occursOn method for:
        - before start date
        - on start date
        - between start and end date
        - on end date
        - after end date
     */

    // Sets up start date, end date, and appointment types for tests
    @Before
    public void setup() {
        start1 = LocalDate.of(2024, 10, 10);
        end1 = LocalDate.of(2024, 12, 10);
        daily = new DailyAppointment("testDaily", start1, end1);
        onetime = new OnetimeAppointment("testOneTime", start1);
        monthly = new MonthlyAppointment("testMonthly", start1, end1);

    }

    // Tests a date before the start date
    @Test
    public void testBeforeStartDate() {
        LocalDate date = LocalDate.of(2024, 10, 9);

        //Expect false
        assertFalse(daily.occursOn(date));
        assertFalse(onetime.occursOn(date));
        assertFalse(monthly.occursOn(date));
    }

    // Tests a date on the start date
    @Test
    public void testOnStartDate() {
        // Expect true for all
        assertTrue(daily.occursOn(start1));
        assertTrue(onetime.occursOn(start1));
        assertTrue(monthly.occursOn(start1));
    }

    // Tests a date between start and end date
    @Test
    public void testBetweenStartAndEndDate() {
       LocalDate date = LocalDate.of(2024, 11, 10);
       // Expect true for daily and monthly
       assertTrue(daily.occursOn(date));
       assertTrue(monthly.occursOn(date));

       // Expect false for both onetime
       assertFalse(onetime.occursOn(date));
    }

    // Tests a date on the end date
    @Test
    public void testOnEndDate() {
        // Expect true for daily and monthly
        assertTrue(daily.occursOn(end1));
        assertTrue(monthly.occursOn(end1));

        // Expect false for both onetime
        assertFalse(onetime.occursOn(end1));
    }

    // Tests a date after the end date
    @Test
    public void testAfterEndDate() {
       LocalDate date = LocalDate.of(2025, 1, 21);
       // Expect false for all
       assertFalse(daily.occursOn(date));
       assertFalse(onetime.occursOn(date));
       assertFalse(monthly.occursOn(date));
    }

    /*
        Milestone 3 Tests:

        Tests the following:
        - constructor of OnetimeAppointment class (making sure end date is same as start date)
        - occursOn for OnetimeAppointment class
        - occursOn for MonthlyAppointment class
        - comparison of Appointment objects using array to see if result is same as expected
     */

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

    // Tests Appointments with same date but different descriptions
    @Test
    public void testComparesToSameDateDiffDescription() {
        LocalDate date1 = LocalDate.of(2024, 10, 22);

        Appointment a1 = new OnetimeAppointment("Workout", date1);
        Appointment a2 = new OnetimeAppointment("Meeting", date1);
        Appointment a3 = new OnetimeAppointment("Doctors Appointment", date1);
        Appointment a4 = new DailyAppointment("Code Review", date1, date1);
        Appointment a5 = new DailyAppointment("Presentation", date1, date1);
        Appointment a6 = new DailyAppointment("Final Exam", date1, date1);
        Appointment a7 = new MonthlyAppointment("Zoom Meeting", date1, date1);
        Appointment a8 = new MonthlyAppointment("Class", date1, date1);
        Appointment a9 = new MonthlyAppointment("Business Meeting", date1, date1);

        Appointment[] appointments = {a1,a2,a3,a4,a5,a6,a7,a8,a9};

        Appointment[] expected = {a9,a8,a4,a3,a6,a2,a5,a1,a7};

        Arrays.sort(appointments);
        assertArrayEquals(expected, appointments);
    }
}