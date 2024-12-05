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


    public AddAppointmentDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.setEnabled(false);

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
    private void VerifyStatus(ActionEvent e) {
        String description = desField.getText();
        LocalDate start = startPicker.getDate();
        LocalDate end = endPicker.getDate();
        if (optionOnetime.isSelected()){
            appointment = new OnetimeAppointment(description, start);
        } else if (optionDaily.isSelected()){
            appointment = new DailyAppointment(description, start, end);
        } else if (optionMonthly.isSelected()){
            appointment = new MonthlyAppointment(description, start, end);

        }
        // Checks to see if the description, start date and end date are filled
        if ((description.trim().isEmpty()) || (start == null) || (end == null)){
            statusLbl.setText("Enter Description, Start Date, and End Date");
            statusLbl.setForeground(Color.RED);
            buttonOK.setEnabled(false);
        } else {
            statusLbl.setText("Ready to insert appointment");
            statusLbl.setForeground(Color.GREEN);
            buttonOK.setEnabled(true);
        }

        if (optionOnetime.isSelected()){
            appointment = new OnetimeAppointment(description, start);
            if (end.equals(start)){
                statusLbl.setText("Ready to insert appointment");
                statusLbl.setForeground(Color.GREEN);
                buttonOK.setEnabled(true);

            } else {
                statusLbl.setText("Start Date should be Equal to End");
                statusLbl.setForeground(Color.RED);
                buttonOK.setEnabled(false);
            }
        } else if (optionDaily.isSelected()){
            appointment = new DailyAppointment(description, start, end);
            if (appointment.occursOn(end) == true) {
                statusLbl.setText("Ready to insert appointment");
                statusLbl.setForeground(Color.GREEN);
                buttonOK.setEnabled(true);
            } else {
                statusLbl.setText("End Date should be after Start");
                statusLbl.setForeground(Color.RED);
                buttonOK.setEnabled(false);
            }


        } else if (optionMonthly.isSelected()){
            appointment = new MonthlyAppointment(description, start, end);
            if (appointment.occursOn(end) == true) {
                statusLbl.setText("Ready to insert appointment");
                statusLbl.setForeground(Color.GREEN);
                buttonOK.setEnabled(true);
            } else {
                statusLbl.setText("Enter Correct End Date");
                statusLbl.setForeground(Color.RED);
                buttonOK.setEnabled(false);
            }
        }

    }

    private void onOK() {

        /*
        String description = desField.getText();
        LocalDate start = startPicker.getDate();
        LocalDate end = endPicker.getDate();
        if (optionOnetime.isSelected()){
            appointment = new OnetimeAppointment(description, start);
        } else if (optionDaily.isSelected()){
            appointment = new DailyAppointment(description, start, end);
        } else if (optionMonthly.isSelected()){
            appointment = new MonthlyAppointment(description, start, end);

        }
        */
        dispose();





    }

    public Appointment getAppointment(){
        return appointment;
    }

    private void onCancel() {
        // add your code here if necessary
        appointment = null;
        dispose();
    }
}
