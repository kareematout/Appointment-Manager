package obj;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class AppointmentManager {
    private HashSet<Appointment> appointments;

    public AppointmentManager() {
        appointments = new HashSet<>();
    }

    /**
     * Add an appointment to the collection
     *
     * @param appointment the appointment to be added
     * @throws IllegalArgumentException if the appointment already exists in the collection
     */
    public void add(Appointment appointment) {
        if (!appointments.add(appointment)) {
            throw new IllegalArgumentException("Appointment already exists");
        }
    }

    /**
     * Remove an appointment from the collection
     *
     * @param appointment the appointment to be removed
     * @throws IllegalArgumentException if the appointment does not exist in the collection
     */
    public void delete(Appointment appointment) {
        if (!appointments.remove(appointment)) {
            throw new IllegalArgumentException("Appointment does not exist");
        }
    }

    /**
     * Update an existing appointment by replacing it with a new one
     *
     * @param current the existing appointment to be replaced
     * @param newAppointment the new appointment to replace the existing one
     */
    public void update(Appointment current, Appointment newAppointment) {
        delete(current);
        add(newAppointment);
    }

    /**
     * Retrieve all appointments occurring on a specific date, ordered by a given comparator
     *
     * @param date  the date to filter appointments by (if null, all appointments are included)
     * @param order the comparator defining the ordering of the returned appointments
     * @return an array of appointments occurring on the specified date, sorted by the comparator
     */
    public Appointment[] getAppointmentsOn(LocalDate date, Comparator<Appointment> order) {
        PriorityQueue<Appointment> temp = new PriorityQueue<>(order);
        if (date == null) {
            temp.addAll(appointments);
        } else {
            for (Appointment appointment : appointments) {
                if (appointment.occursOn(date)) {
                    temp.add(appointment);
                }
            }
        }
        Appointment[] sortedAppointments = new Appointment[temp.size()];
        int index = 0;
        while (!temp.isEmpty()) {
            sortedAppointments[index++] = temp.poll();
        }
        return sortedAppointments;
    }

    /**
     * Retrieve the entire collection of appointments
     *
     * @return a HashSet containing all appointments
     */
    public HashSet<Appointment> getAppointments() {
        return appointments;
    }
}
