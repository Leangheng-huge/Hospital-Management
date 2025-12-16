import GUI_Panels.*;
import models.Doctor;
import models.Patient;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HospitalManagement extends JFrame {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private boolean[] rooms;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public HospitalManagement() {
        doctors = new ArrayList<>();
        doctors.add(new Doctor("Kim Sabu", "General"));
        doctors.add(new Doctor("Yoon Seo-jung", "Neurology"));
        doctors.add(new Doctor("Kang DOng-ju", "Cardiology"));
        doctors.add(new Doctor("Seo Woo-jin", "Surgery"));
        doctors.add(new Doctor("Cha Eun-jae", "Medicine"));

        rooms = new boolean[10];
        patients = new ArrayList<>();

        setupGUI();
    }

    private void setupGUI() {
        setTitle("Hospital Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new MenuPanel(this, cardLayout, mainPanel), "MENU");
        mainPanel.add(new AddPatientPanel(this, cardLayout, mainPanel, patients),
                "ADD_PATIENT");
        mainPanel.add(new AssignDoctorPanel(this, cardLayout, mainPanel, doctors, patients),
                "ASSIGN_DOCTOR");
        mainPanel.add(new AdmitPatientPanel(this, cardLayout, mainPanel, patients, rooms),
                "ADMIT_PATIENT");
        mainPanel.add(new GenerateBillPanel(this, cardLayout, mainPanel, patients),
                "GENERATE_BILL");
        mainPanel.add(new DischargePatientPanel(this, cardLayout, mainPanel, doctors, patients,
                rooms), "DISCHARGE_PATIENT");
        mainPanel.add(new ViewPatientsPanel(cardLayout, mainPanel, patients),
                "VIEW_PATIENTS");
        mainPanel.add(new ViewRoomsPanel(this,cardLayout, mainPanel, rooms),
                "VIEW_ROOMS");

        add(mainPanel);
        cardLayout.show(mainPanel, "MENU");
    }

    public Patient findPatient(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }

    public Patient findActivePatient(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id && !patient.isDischarged()) {
                return patient;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HospitalManagement hospital = new HospitalManagement();
            hospital.setVisible(true);
        });
    }
}