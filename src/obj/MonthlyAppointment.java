package obj;

import java.time.LocalDate;

public class MonthlyAppointment extends Appointment {
    public MonthlyAppointment(String description, LocalDate startDate, LocalDate endDate) {
        super(description, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Monthly Appointment " + super.toString();
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return super.inBetween(date) && date.getDayOfMonth() == getStartDate().getDayOfMonth();
    }
}
