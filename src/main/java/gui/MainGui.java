package gui;

import com.github.lgooddatepicker.components.DatePicker;
import obj.Appointment;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainGui {
    private JPanel panel;
    private JRadioButton displaAllBtn;
    private JRadioButton displayAppointmentsBtn;
    private JComboBox sortBox;
    private JList displayList;
    private JButton addBtn;
    private JButton deleteBtn;
    private JButton editBtn;
    private DatePicker datePicker;

    //private AppointmentManager manager;

    public MainGui() {
        //manager = new AppointmentManager();
        addBtn.addActionListener(this::addAppointment);
        editBtn.addActionListener(this::addAppointment);
        deleteBtn.addActionListener(this::deleteAppointment);
    }

    private void addAppointment(ActionEvent e) {
        try{
            AddAppointmentDialog dialog = new AddAppointmentDialog();
            dialog.pack();
            dialog.setVisible(true);
            Appointment appointment = dialog.getAppointment();
            if (e.getSource().equals(addBtn)) {
                //manager.add(appointment);
                //Appointment [] appointments = manager.getAppointmentsOn(null, null);
                //displayList.setListData(appointments);
            }

            else if (e.getSource().equals(editBtn))
                System.out.println("Edit btn clicked");

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void deleteAppointment(ActionEvent e){
        System.out.println("Delete btn clicked");

    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Application App");
        frame.setContentPane(new MainGui().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
