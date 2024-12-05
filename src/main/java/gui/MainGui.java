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

    private AppointmentManager manager;

    public MainGui() {
        manager = new AppointmentManager();
        addBtn.addActionListener(this::addAppointment);
        editBtn.addActionListener(this::editAppointment);
        deleteBtn.addActionListener(this::deleteAppointment);
        sortBox.addItem("Date");
        sortBox.addItem("Description");

        //displayAppointmentsBtn.addActionListener(this::UpdateListBox);
        //displayAllBtn.addActionListener(this::UpdateListBox);
        //sortBox.addActionListener(this::UpdateListBox);
        updateDisplayBtn.addActionListener(this::UpdateListBox);
    }

    private void UpdateListBox(ActionEvent e){
        String sortBoxValue = (String) sortBox.getSelectedItem();
        if (displayAllBtn.isSelected() && sortBoxValue.equals("Date")) {
            //Comparator<Appointment> comparator = Comparator.comparing(Appointment::getStartDate);
            Appointment [] appointments = manager.getAppointmentsOn(null, Comparator.comparing(Appointment::getStartDate));
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
            displayList.setListData(appointments);
            displayList.revalidate();
            displayList.repaint();
            ErrorMessageLbl.setText("Displaying All by 'Date'");
            ErrorMessageLbl.setForeground(Color.GREEN);

        } else if (displayAllBtn.isSelected() && sortBoxValue.equals("Description")){
            //Comparator<Appointment> comparator = Comparator.comparing(Appointment::getDescription);
            Appointment [] appointments = manager.getAppointmentsOn(null, Comparator.comparing(Appointment::getDescription));
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
            displayList.setListData(appointments);
            displayList.revalidate();
            displayList.repaint();
            ErrorMessageLbl.setText("Displaying All by 'Description'");
            ErrorMessageLbl.setForeground(Color.GREEN);

        } else if (displayAppointmentsBtn.isSelected() && sortBoxValue.equals("Date") && datePicker.getDate() != null){
            //Comparator<Appointment> comparator = Comparator.comparing(Appointment::getStartDate);
            Appointment[] appointments = manager.getAppointmentsOn(datePicker.getDate(), Comparator.comparing(Appointment::getStartDate));
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
            displayList.setListData(appointments);
            displayList.revalidate();
            displayList.repaint();
            ErrorMessageLbl.setText("Displaying by 'Date'");
            ErrorMessageLbl.setForeground(Color.GREEN);
        } else if (displayAppointmentsBtn.isSelected() && sortBoxValue.equals("Description") && datePicker.getDate() != null){
            //Comparator<Appointment> comparator = Comparator.comparing(Appointment::getDescription);
            Appointment[] appointments = manager.getAppointmentsOn(datePicker.getDate(), Comparator.comparing(Appointment::getDescription));
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
            displayList.setListData(appointments);
            displayList.revalidate();
            displayList.repaint();
            ErrorMessageLbl.setText("Displaying by 'Description'");
            ErrorMessageLbl.setForeground(Color.GREEN);
        } else if (datePicker.getDate() ==  null) {
            ErrorMessageLbl.setText("Enter a date");
            ErrorMessageLbl.setForeground(Color.RED);
        }
    }

    private void addAppointment(ActionEvent e) {
        try{

            AddAppointmentDialog dialog = new AddAppointmentDialog();
            dialog.pack();
            dialog.setVisible(true);
            Appointment appointment = dialog.getAppointment();

            if (appointment != null) {
                manager.add(appointment);
            }
            Appointment [] appointments = manager.getAppointmentsOn(null, null);
            displayList.setListData(appointments);
            ErrorMessageLbl.setText("Successfully Added");
            ErrorMessageLbl.setForeground(Color.GREEN);


        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void editAppointment(ActionEvent e) {
        try{
            if (displayList == null) {
                ErrorMessageLbl.setText("No appointment available");
                ErrorMessageLbl.setForeground(Color.RED);
            } else {
                Appointment appointmentToUpdate = displayList.getSelectedValue();
                if (appointmentToUpdate != null) {
                    AddAppointmentDialog dialog = new AddAppointmentDialog();
                    dialog.pack();
                    dialog.setVisible(true);
                    Appointment appointment = dialog.getAppointment();

                    manager.update(appointmentToUpdate, appointment);

                    Appointment[] appointments = manager.getAppointmentsOn(null, null);
                    displayList.setListData(appointments);
                    ErrorMessageLbl.setText("Successfully Updated");
                    ErrorMessageLbl.setForeground(Color.GREEN);
                    } else {
                        ErrorMessageLbl.setText("No appointment selected");
                        ErrorMessageLbl.setForeground(Color.RED);
                    }

                }

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

    }


    private void deleteAppointment(ActionEvent e){
        System.out.println("Delete btn clicked");
        Appointment appointmentDelete = displayList.getSelectedValue();
        if (appointmentDelete != null) {
            manager.delete(appointmentDelete);
            Appointment[] appointments = manager.getAppointmentsOn(null, null);
            displayList.setListData(appointments);
            ErrorMessageLbl.setText("Successfully Deleted");
            ErrorMessageLbl.setForeground(Color.GREEN);
        } else {
            ErrorMessageLbl.setText("Select an Appointment");
            ErrorMessageLbl.setForeground(Color.RED);
        }



    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Application App");
        frame.setContentPane(new MainGui().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
