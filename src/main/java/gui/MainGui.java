package gui;

import com.github.lgooddatepicker.components.DatePicker;
import obj.Appointment;
import obj.AppointmentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

public class MainGui {

    // Declare the J widgets
    private JPanel panel;
    private JRadioButton displayAllBtn;
    private JRadioButton displayAppointmentsBtn;
    private JComboBox<String> sortBox;
    private JList<Appointment> displayList;
    private JButton addBtn;
    private JButton deleteBtn;
    private JButton editBtn;
    private DatePicker datePicker;
    private JLabel ErrorMessageLbl;
    private JLabel messageLbl;
    private JButton updateDisplayBtn;

    // Declare a new AppointmentManager
    private AppointmentManager manager;

    public MainGui() {
        // Create a new AppointmentManager with a value
        manager = new AppointmentManager();

        // Create the listeners for the buttons "Add" "Update" and "Delete"
        // When clicked, it will call the method
        addBtn.addActionListener(this::addAppointment);
        editBtn.addActionListener(this::editAppointment);
        deleteBtn.addActionListener(this::deleteAppointment);

        // Sets up the sort Box with 2 options, Date and Description
        sortBox.addItem("Date");
        sortBox.addItem("Description");

        // Creates a listener for the "Update Display" button which calls the UpdateListBox method
        updateDisplayBtn.addActionListener(this::UpdateListBox);
    }

    // This method is called when the "Update Display" button is pressed
    // It takes in the user inputs for the display settings and updates them accordingly
    private void UpdateListBox(ActionEvent e){

        // Gets the value of the sort box ("Date" or "Description")
        String sortBoxValue = (String) sortBox.getSelectedItem();

        // Option 1
        // The Display All button is selected, and the Date sort box is selected
        if (displayAllBtn.isSelected() && sortBoxValue.equals("Date")) {

            // Create comparator which sorts by Start Date, then End Date, then Description
            Comparator<Appointment> comparator = Comparator.comparing(Appointment::getStartDate)
                    .thenComparing(Appointment::getEndDate).thenComparing(Appointment::getDescription);

            // Call the getAppointmentsOn and search using the previously defined comparator
            // Takes in the appointments in the HashSet
            // Since Display All is chosen, date is null to include all appointments
            Appointment [] appointments = manager.getAppointmentsOn(null, comparator);

            // Set the display list to the array
            displayList.setListData(appointments);

            // Ensures user interface is consistent
            displayList.revalidate();
            displayList.repaint();

            // Change error label
            ErrorMessageLbl.setText("Displaying All by 'Date'");
            ErrorMessageLbl.setForeground(Color.GREEN);

            // Option 2
            // Display all is selected, and Description sort box is selected
        } else if (displayAllBtn.isSelected() && sortBoxValue.equals("Description")){
            // Create comparator that sorts by Description, then Start Date, then End Date
            Comparator<Appointment> comparator = Comparator.comparing(Appointment::getDescription)
                    .thenComparing(Appointment::getStartDate).thenComparing(Appointment::getEndDate);

            // Call the getAppointmentsOn and search using the previously defined comparator
            // Takes in the appointments in the HashSet
            // Since Display All is chosen, date is null to include all appointments
            Appointment [] appointments = manager.getAppointmentsOn(null, comparator);

            // Add the array to the display list
            displayList.setListData(appointments);

            // Ensure user interface is consistent
            displayList.revalidate();
            displayList.repaint();

            // Change error label
            ErrorMessageLbl.setText("Displaying All by 'Description'");
            ErrorMessageLbl.setForeground(Color.GREEN);

            // Option 3
            // Display by Date is selected, and sort box is "Date"
        } else if (displayAppointmentsBtn.isSelected() && sortBoxValue.equals("Date") && datePicker.getDate() != null){
            Comparator<Appointment> comparator = Comparator.comparing(Appointment::getStartDate)
                    .thenComparing(Appointment::getEndDate).thenComparing(Appointment::getDescription);

            // Call the getAppointmentsOn and search using the previously defined comparator
            // Takes in the appointments in the HashSet
            // Since Display Appointments is selected, date is selected date
            Appointment[] appointments = manager.getAppointmentsOn(datePicker.getDate(), comparator);

            // Set array to display list
            displayList.setListData(appointments);

            // Ensure user interface
            displayList.revalidate();
            displayList.repaint();

            // Change label
            ErrorMessageLbl.setText("Displaying by 'Date'");
            ErrorMessageLbl.setForeground(Color.GREEN);

            // Option 4
            // Display by Date is selected, and sort box is "Description"
        } else if (displayAppointmentsBtn.isSelected() && sortBoxValue.equals("Description") && datePicker.getDate() != null){
            Comparator<Appointment> comparator = Comparator.comparing(Appointment::getDescription)
                    .thenComparing(Appointment::getStartDate).thenComparing(Appointment::getEndDate);

            // Call the getAppointmentsOn and search using the previously defined comparator
            // Takes in the appointments in the HashSet
            // Since Display Appointments is chosen, date is selected date
            Appointment[] appointments = manager.getAppointmentsOn(datePicker.getDate(), comparator);

            // Add the array to the display list
            displayList.setListData(appointments);

            // Ensure user interface
            displayList.revalidate();
            displayList.repaint();

            // Change error label
            ErrorMessageLbl.setText("Displaying by 'Description'");
            ErrorMessageLbl.setForeground(Color.GREEN);

            // If the user does not select a date change error label
        } else if (datePicker.getDate() ==  null) {
            ErrorMessageLbl.setText("Enter a date");
            ErrorMessageLbl.setForeground(Color.RED);
        }
    }

    // addAppointment is called when the "Add" button is selected
    private void addAppointment(ActionEvent e) {
        try{
            // Create a new AddAppointmentDialog and set default measures
            AddAppointmentDialog dialog = new AddAppointmentDialog();
            dialog.pack();
            dialog.setVisible(true);

            // Get the appointment value
            Appointment appointment = dialog.getAppointment();

            // If the appointment exists, use the AppointmentManager add method
            if (appointment != null) {
                manager.add(appointment);
            }
            // Use getAppointments on to create an array
            // The appointments are from the HashSet in manager
            // Date is null to include all appointments
            // Comparator is null, so it sorts by the comparable order
            Appointment [] appointments = manager.getAppointmentsOn(null, null);

            // Set the display list to the array
            displayList.setListData(appointments);

            // Update error message
            ErrorMessageLbl.setText("Successfully Added");
            ErrorMessageLbl.setForeground(Color.GREEN);


        } catch (IllegalArgumentException ex) {
            //System.out.println(ex.getMessage());
            ErrorMessageLbl.setText("Can't Add Duplicate");
            ErrorMessageLbl.setForeground(Color.RED);
        }

    }

    // editAppointment method is called when the "Update" button is clicked
    private void editAppointment(ActionEvent e) {
        try{

            // Checks if user has created any appointments
            // If there are no appointments change label
            if (displayList == null) {
                ErrorMessageLbl.setText("No appointment available");
                ErrorMessageLbl.setForeground(Color.RED);
            } else {
                // If an appointment is selected run the update

                // get the current appointment selected in display list
                Appointment appointmentToUpdate = displayList.getSelectedValue();

                // Check if selected appointment exists
                if (appointmentToUpdate != null) {
                    // Create new Appointment Dialog and set default parameters
                    AddAppointmentDialog dialog = new AddAppointmentDialog();
                    dialog.pack();
                    dialog.setVisible(true);

                    // Set the appointment value
                    Appointment appointment = dialog.getAppointment();

                    // Call the AppointmentManager update method
                    // This method deletes the old appointment and adds the new one
                    manager.update(appointmentToUpdate, appointment);

                    // Call getAppointmentsOn
                    // Date is null so include all appointments, comparator is null so use comparable sort
                    Appointment[] appointments = manager.getAppointmentsOn(null, null);

                    // Add the array to the display list
                    displayList.setListData(appointments);

                    // Change error label
                    ErrorMessageLbl.setText("Successfully Updated");
                    ErrorMessageLbl.setForeground(Color.GREEN);
                    } else {
                        // If the user did not select an appointment do not create dialog
                        // Change error label
                        ErrorMessageLbl.setText("No appointment selected");
                        ErrorMessageLbl.setForeground(Color.RED);
                    }

                }

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }

    // deleteAppointment is called when "Delete" button is clicked
    private void deleteAppointment(ActionEvent e){

        // Get the selected appointment from the display list
        Appointment appointmentDelete = displayList.getSelectedValue();
        // Check to see if user clicked on an appointment
        if (appointmentDelete != null) {
            // If an appointment is selected use AppointmentManager delete method
            manager.delete(appointmentDelete);

            // Use getAppointmentsOn to update array
            Appointment[] appointments = manager.getAppointmentsOn(null, null);

            // Update display list with array
            displayList.setListData(appointments);

            // Change error label
            ErrorMessageLbl.setText("Successfully Deleted");
            ErrorMessageLbl.setForeground(Color.GREEN);
        } else {
            // If user does not select an appointment don't delete anything
            // Change error label
            ErrorMessageLbl.setText("Select an Appointment");
            ErrorMessageLbl.setForeground(Color.RED);
        }

    }


    // Default set up for JFrame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Appointment App");
        frame.setContentPane(new MainGui().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
