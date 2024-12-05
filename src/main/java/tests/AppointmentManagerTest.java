package tests;


import obj.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.Assert.*;

// Milestone 4: JUnit tests
public class AppointmentManagerTest {

    private OnetimeAppointment onetime1;
    private OnetimeAppointment onetime2;
    private DailyAppointment daily1;
    private DailyAppointment daily2;
    private DailyAppointment daily3;
    private MonthlyAppointment monthly1;
    private MonthlyAppointment monthly2;
    private LocalDate start1;
    private LocalDate end1;
    private LocalDate start2;
    private LocalDate end2;

    /*
        Milestone 4 Tests:

        Tests the following AppointmentManager class methods:
        - add
        - delete
        - update
        - getAppointmentsOn
     */

    @Before
    public void setUp() {
        start1 = LocalDate.of(2020, 1, 1);
        end1 = LocalDate.of(2020, 1, 10);

        start2 = LocalDate.of(2020, 2, 5);
        end2 = LocalDate.of(2020, 2, 12);



        daily1 = new DailyAppointment("testDaily", start1, end1);
        daily2 = new DailyAppointment("testDaily", start2, end2);

        daily3 = new DailyAppointment("zDaily", start1, end1);

        onetime1 = new OnetimeAppointment("testOneTime", start1);
        onetime2 = new OnetimeAppointment("testOneTime", start2);

        monthly1 = new MonthlyAppointment("testMonthly", start1, end1);
        monthly2 = new MonthlyAppointment("testMonthly", start2, end2);
    }

    // Testing Add method works properly
    @Test
    public void testAddAppointment() {
        AppointmentManager appointmentManager = new AppointmentManager();
        appointmentManager.add(daily1);
        appointmentManager.add(monthly1);
        appointmentManager.add(onetime1);

        assertTrue(appointmentManager.getAppointments().contains(daily1));
        assertTrue(appointmentManager.getAppointments().contains(monthly1));
        assertTrue(appointmentManager.getAppointments().contains(onetime1));
    }

    // Testing trying to add duplicate, should throw exception
    @Test (expected = Exception.class)
    public void testAddDuplicateAppointment() {
        AppointmentManager appointmentManager = new AppointmentManager();
        appointmentManager.add(daily1);
        appointmentManager.add(daily1);
    }

    // Testing delete method works properly
    @Test
    public void testDeleteAppointment() {
        AppointmentManager appointmentManager = new AppointmentManager();

        appointmentManager.add(daily1);
        appointmentManager.delete(daily1);

        appointmentManager.add(monthly1);
        appointmentManager.delete(monthly1);

        appointmentManager.add(onetime1);
        appointmentManager.delete(onetime1);

        assertFalse(appointmentManager.getAppointments().contains(daily1));
        assertFalse(appointmentManager.getAppointments().contains(monthly1));
        assertFalse(appointmentManager.getAppointments().contains(onetime1));
    }

    // Testing delete method with non-existing appointment
    @Test (expected = Exception.class)
    public void testDeleteNonExistentAppointment() {
        AppointmentManager appointmentManager = new AppointmentManager();
        appointmentManager.delete(daily1);

        appointmentManager.delete(monthly1);

        appointmentManager.delete(onetime1);
    }

    // Testing update method works properly. Ensuring original appointment is not contained in HashSet after method call.
    @Test
    public void testUpdateAppointment() {
        AppointmentManager appointmentManager = new AppointmentManager();
        appointmentManager.add(daily1);
        appointmentManager.update(daily1, daily2);

        appointmentManager.add(monthly1);
        appointmentManager.update(monthly1, monthly2);

        appointmentManager.add(onetime1);
        appointmentManager.update(onetime1, onetime2);

        assertTrue(appointmentManager.getAppointments().contains(daily2));
        assertFalse(appointmentManager.getAppointments().contains(daily1));

        assertTrue(appointmentManager.getAppointments().contains(monthly2));
        assertFalse(appointmentManager.getAppointments().contains(monthly1));

        assertTrue(appointmentManager.getAppointments().contains(onetime2));
        assertFalse(appointmentManager.getAppointments().contains(onetime1));
    }

    // Testing getAppointmentsOn method with null
    @Test
    public void testNullGetAppointmentsOn() {
        AppointmentManager appointmentManager = new AppointmentManager();
        Comparator<Appointment> comparator = Comparator.comparing(Appointment::getDescription);

        appointmentManager.add(monthly1);
        appointmentManager.add(daily1);
        appointmentManager.add(onetime1);

        Appointment[] result = appointmentManager.getAppointmentsOn(null, comparator);

        assertArrayEquals(new Appointment[]{daily1,monthly1,onetime1}, result);
    }

    // Test getAppointmentsOn method
    @Test
    public void testGetAppointmentsOn() {
        AppointmentManager appointmentManager = new AppointmentManager();
        Comparator<Appointment> comparator = Comparator.comparing(Appointment::getDescription);

        appointmentManager.add(monthly1);
        appointmentManager.add(daily1);
        appointmentManager.add(onetime1);

        appointmentManager.add(monthly2);
        appointmentManager.add(daily2);
        appointmentManager.add(onetime2);

        Appointment[] result = appointmentManager.getAppointmentsOn(start1, comparator);

        assertArrayEquals(new Appointment[]{daily1,monthly1,onetime1}, result);
    }

    // Test getAppointmentsOn method to ensuring proper sorting
    @Test
    public void testGetAppointmentsOnSort() {
        AppointmentManager appointmentManager = new AppointmentManager();
        Comparator<Appointment> comparator = Comparator.comparing(Appointment::getDescription);

        appointmentManager.add(monthly1);
        appointmentManager.add(daily1);
        appointmentManager.add(onetime1);

        appointmentManager.add(monthly2);
        appointmentManager.add(daily2);
        appointmentManager.add(onetime2);

        appointmentManager.add(daily3);

        Appointment[] result = appointmentManager.getAppointmentsOn(start1, comparator);

        assertArrayEquals(new Appointment[]{daily1,monthly1,onetime1,daily3}, result);
    }

    /*
    @Test
    public void testGetAppointmentsOnSort2() {
        AppointmentManager appointmentManager = new AppointmentManager();
        Comparator<Appointment> comparator = Comparator.comparing(Appointment::getDescription);

        OnetimeAppointment onetime1 = new OnetimeAppointment("a", start1);
        OnetimeAppointment onetime2 = new OnetimeAppointment("b", start1);
        OnetimeAppointment onetime3 = new OnetimeAppointment("c", start1);
        DailyAppointment d = new DailyAppointment("a", start1, end1);

        appointmentManager.add(onetime1);
        appointmentManager.add(onetime3);
        appointmentManager.add(onetime2);
        appointmentManager.add(d);


        Appointment[] result = appointmentManager.getAppointmentsOn(start1, comparator);

        for (Appointment appointment : result) {
            System.out.println(appointment);
        }

        assertArrayEquals(new Appointment[]{onetime1,d,onetime2,onetime3}, result);
    }

    @Test

    public void testGetAppointmentsOnSort3() {
        AppointmentManager appointmentManager = new AppointmentManager();
        Comparator<Appointment> comparator = Comparator.comparing(Appointment::getStartDate);

        LocalDate start3 = LocalDate.of(2020, 3, 6);
        LocalDate start4 = LocalDate.of(2020, 4, 3);

        OnetimeAppointment onetime1 = new OnetimeAppointment("a", start1);
        OnetimeAppointment onetime2 = new OnetimeAppointment("a", start2);
        OnetimeAppointment onetime3 = new OnetimeAppointment("a", start3);
        DailyAppointment d = new DailyAppointment("a", start4, end1);

        appointmentManager.add(onetime1);
        appointmentManager.add(onetime3);
        appointmentManager.add(onetime2);
        appointmentManager.add(d);


        Appointment[] result = appointmentManager.getAppointmentsOn(null, comparator);

        for (Appointment appointment : result) {
            System.out.println(appointment);
        }

        //assertArrayEquals(new Appointment[]{onetime1,d,onetime2,onetime3}, result);
    }

    */
}
