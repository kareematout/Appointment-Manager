package obj;

import java.time.LocalDate;

public class OnetimeAppointment extends Appointment{
    public OnetimeAppointment(String description, LocalDate startDate){
        super(description, startDate, startDate);
    }

    @Override
    public String toString() {
        return "One Time Appointment " + super.toString();
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return date.equals(getStartDate());
    }
}
