package gui;

import obj.Appointment;
import obj.DailyAppointment;
import obj.MonthlyAppointment;
import obj.OnetimeAppointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import com.github.lgooddatepicker.components.DatePicker;

public class AddAppointmentDialog extends JDialog {
    // Declare widgets
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField desField;
    private JRadioButton optionOnetime;
    private JRadioButton optionDaily;
    private JRadioButton optionMonthly;
    private DatePicker startPicker;
    private DatePicker endPicker;
    private JLabel statusLbl;
    private JButton verifyStatusBtn;
    private JLabel request;
    private Appointment appointment;

    // Default Settings for Dialog
    public AddAppointmentDialog() {
        // Display settings
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Disable the OK button at first
        buttonOK.setEnabled(false);

        // Add listener to OK and Cancel button which will call respective methods
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        verifyStatusBtn.addActionListener(this::VerifyStatus);
    }

    // VerifyStatus method is called when the "Verify Status" button is pressed
    // The button ensures that the user inputs are valid
    private void VerifyStatus(ActionEvent e) {
        // Get the entered values from the user

        // Description
        String description = desField.getText();
        // Start Date
        LocalDate start = startPicker.getDate();
        // End Date
        LocalDate end = endPicker.getDate();


        // Checks to see if the description, start date and end date are filled
        // If any of them are empty, do not enable OK button
        if ((description.trim().isEmpty()) || (start == null) || (end == null)){
            statusLbl.setText("Enter Description, Start Date, and End Date");
            statusLbl.setForeground(Color.RED);
            buttonOK.setEnabled(false);
        } else {
            // If they are filled enable OK button
            statusLbl.setText("Ready to insert appointment");
            statusLbl.setForeground(Color.GREEN);
            buttonOK.setEnabled(true);
        }

        // Onetime Appointment
        if (optionOnetime.isSelected()){
            // Create the Onetime Appointment
            appointment = new OnetimeAppointment(description, start);

            // If the start date and end date are equal it is valid
            if (end.equals(start)){
                // Change the label and enable OK
                statusLbl.setText("Ready to insert appointment");
                statusLbl.setForeground(Color.GREEN);
                buttonOK.setEnabled(true);

            } else {
                // If the start date and end date are not the same
                // Change label and don't enable OK
                statusLbl.setText("Start Date should be Equal to End");
                statusLbl.setForeground(Color.RED);
                buttonOK.setEnabled(false);
            }
            // Daily Appointment
        } else if (optionDaily.isSelected()){
            appointment = new DailyAppointment(description, start, end);

            // Use the occursOn method to ensure the Start and End Date are proper
            if (appointment.occursOn(end) == true) {

                // If they are fine then change the label and enable OK
                statusLbl.setText("Ready to insert appointment");
                statusLbl.setForeground(Color.GREEN);
                buttonOK.setEnabled(true);
            } else {
                // If they are not fine tell user and disable OK
                statusLbl.setText("End Date should be after Start");
                statusLbl.setForeground(Color.RED);
                buttonOK.setEnabled(false);
            }

        // Monthly Appointment
        } else if (optionMonthly.isSelected()){
            appointment = new MonthlyAppointment(description, start, end);

            // Use occursOn to check if the date is proper
            if (appointment.occursOn(end) == true) {
                // If its fine change label and enable OK
                statusLbl.setText("Ready to insert appointment");
                statusLbl.setForeground(Color.GREEN);
                buttonOK.setEnabled(true);
            } else {
                // The end date should be after the start date
                // It should also be the same day of month as start date
                statusLbl.setText("End Date should be after Start Date, and have same Day");

                // Change label and disable OK
                statusLbl.setForeground(Color.RED);
                buttonOK.setEnabled(false);
            }
        }

    }

    private void onOK() {
        dispose();

    }

    // Return method
    public Appointment getAppointment(){
        return appointment;
    }

    private void onCancel() {
        // If user cancels set the appointment value to null
        appointment = null;
        dispose();
    }
}
