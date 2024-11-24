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
     * Check if the appointment occurs on a given date
     *
     * @param date the date that will be checked
     * @return true or false depending on if the appointment occurs on a given date
     */
    protected boolean inBetween(LocalDate date) {
        return ((date.isAfter(startDate) || date.isEqual(startDate)) && (date.isBefore(endDate) || date.isEqual(endDate)));
    }

    public abstract boolean occursOn(LocalDate date);

    @Override
    public String toString() {
        return String.format("Description: %s | Starts: %s | Ends: %s", description, startDate, endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment that)) return false;
        return Objects.equals(this.toString(), that.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, startDate, endDate);
    }

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
