package obj;

import java.time.LocalDate;

public class DailyAppointment extends Appointment{
    public DailyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Daily Appointment " + super.toString();
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return super.inBetween(date);
    }
}
