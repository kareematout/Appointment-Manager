package classes;
import java.time.LocalDate;

//Appointment Class
public class Appointment {

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
    public boolean occursOn(LocalDate date) {
        return ((date.isAfter(startDate) || date.isEqual(startDate)) && (date.isBefore(endDate) || date.isEqual(endDate)));
    }
}
