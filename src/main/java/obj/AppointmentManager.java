package obj;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AppointmentManager {
    private HashSet<Appointment> appointments;

    public AppointmentManager() {
        appointments = new HashSet<>();
    }

    public void add(Appointment appointment) {
        if (!appointments.add(appointment)) {
            throw new IllegalArgumentException("Appointment already exists");
        }
    }

    public void delete(Appointment appointment) {
        if (!appointments.remove(appointment)) {
            throw new IllegalArgumentException("Appointment does not exist");
        }
    }

    public void update(Appointment current, Appointment newAppointment) {
        delete(current);
        add(newAppointment);
    }

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
        return temp.toArray(new Appointment[0]);
    }
}
