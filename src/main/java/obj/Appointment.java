package obj;
import java.time.LocalDate;
import java.util.Objects;

//Appointment Class
public abstract class Appointment implements Comparable<Appointment> {

    //private instance variables
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    //Constructor
    public Appointment(String description, LocalDate startDate, LocalDate endDate) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Getters
    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    //Setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Check if the appointment falls on or between the start and end dates
     *
     * @param date the date to check
     * @return true if the specified date is within the range of the appointment's
     * start and end dates, false otherwise
     */
    protected boolean inBetween(LocalDate date) {
        return ((date.isAfter(startDate) || date.isEqual(startDate)) && (date.isBefore(endDate) || date.isEqual(endDate)));
    }

    /**
     * Abstract method to check if the appointment occurs on a specific date
     *
     * @param date the date to check
     * @return true if the appointment occurs on the specified date, false otherwise
     */
    public abstract boolean occursOn(LocalDate date);

    /**
     * Convert the appointment details into a string representation
     *
     * @return a formatted string containing the appointment's description, start date, and end date
     */
    @Override
    public String toString() {
        return String.format("Description: %s | Starts: %s | Ends: %s", description, startDate, endDate);
    }

    /**
     * Check if two appointments are equal based on their string representations
     *
     * @param o the object to compare with this appointment
     * @return true if the two appointments are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment that)) return false;
        return Objects.equals(this.toString(), that.toString());
    }

    /**
     * Generate a hash code for the appointment
     *
     * @return an integer hash code based on the description, start date, and end date
     */
    @Override
    public int hashCode() {
        return Objects.hash(description, startDate, endDate);
    }

    /**
     * Compare this appointment to another appointment for ordering
     *
     * @param o the appointment to compare with this appointment
     * @return a negative integer, zero, or a positive integer as this appointment is less than,
     * equal to, or greater than the specified appointment
     */
    @Override
    public int compareTo(Appointment o) {
        if(!startDate.equals(o.startDate)) {
            return startDate.compareTo(o.startDate);
        }
        else if (!endDate.equals(o.endDate)) {
            return endDate.compareTo(o.endDate);
        }
        else {
            return description.compareTo(o.description);
        }
    }
}
