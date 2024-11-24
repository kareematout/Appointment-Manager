package gui;

import obj.Appointment;
import obj.DailyAppointment;
import obj.MonthlyAppointment;
import obj.OnetimeAppointment;

import javax.swing.*;
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
    private Appointment appointment;


    public AddAppointmentDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
    }

    private void onOK() {


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
        dispose();





    }

    public Appointment getAppointment(){
        return appointment;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
