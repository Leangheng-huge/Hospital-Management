import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

class Doctor {
    private String name;
    private String specialization;
    private boolean available;

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
        this.available = true;
    }

    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}

class Patient {
    private int id;
    private String name;
    private int age;
    private String disease;
    private String assignedDoctor;
    private int roomNumber;
    private double bill;
    private boolean admitted;
    private boolean discharged;

    public Patient(int id, String name, int age, String disease) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.assignedDoctor = "Not assigned";
        this.roomNumber = 0;
        this.bill = 0.0;
        this.admitted = false;
        this.discharged = false;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDisease() { return disease; }
    public String getAssignedDoctor() { return assignedDoctor; }
    public void setAssignedDoctor(String assignedDoctor) { this.assignedDoctor = assignedDoctor; }
    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public double getBill() { return bill; }
    public void setBill(double bill) { this.bill = bill; }
    public boolean isAdmitted() { return admitted; }
    public void setAdmitted(boolean admitted) { this.admitted = admitted; }
    public boolean isDischarged() { return discharged; }
    public void setDischarged(boolean discharged) { this.discharged = discharged; }
}

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

        mainPanel.add(createMenuPanel(), "MENU");
        mainPanel.add(createAddPatientPanel(), "ADD_PATIENT");
        mainPanel.add(createAssignDoctorPanel(), "ASSIGN_DOCTOR");
        mainPanel.add(createAdmitPatientPanel(), "ADMIT_PATIENT");
        mainPanel.add(createGenerateBillPanel(), "GENERATE_BILL");
        mainPanel.add(createDischargePatientPanel(), "DISCHARGE_PATIENT");
        mainPanel.add(createViewPatientsPanel(), "VIEW_PATIENTS");
        mainPanel.add(createViewRoomsPanel(), "VIEW_ROOMS");

        add(mainPanel);
        cardLayout.show(mainPanel, "MENU");
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        String[] buttonLabels = {
                "Add Patient", "Assign Doctor", "Admit Patient to Room", "Generate Bill",
                "Discharge Patient", "View All Patients", "View Room Status", "Exit"
        };

        String[] cardNames = {
                "ADD_PATIENT", "ASSIGN_DOCTOR", "ADMIT_PATIENT", "GENERATE_BILL",
                "DISCHARGE_PATIENT", "VIEW_PATIENTS", "VIEW_ROOMS", "EXIT"
        };

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setPreferredSize(new Dimension(250, 40));
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);

            final String cardName = cardNames[i];
            button.addActionListener(e -> {
                if (cardName.equals("EXIT")) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to exit?", "Exit",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                } else {
                    cardLayout.show(mainPanel, cardName);
                }
            });

            gbc.gridx = 0;
            gbc.gridy = i + 1;
            panel.add(button, gbc);
        }

        return panel;
    }

    private JPanel createAddPatientPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Add New Patient");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        JLabel nameLabel = new JLabel("Patient Name:");
        JTextField nameField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(ageLabel, gbc);
        gbc.gridx = 1;
        panel.add(ageField, gbc);

        JLabel diseaseLabel = new JLabel("Disease:");
        JTextField diseaseField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(diseaseLabel, gbc);
        gbc.gridx = 1;
        panel.add(diseaseField, gbc);

        JButton addButton = new JButton("Add Patient");
        addButton.setBackground(new Color(34, 139, 34));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String disease = diseaseField.getText().trim();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name cannot be empty!");
                    return;
                }

                if (findPatient(id) != null) {
                    JOptionPane.showMessageDialog(this, "Patient with ID " + id + " already exists!");
                    return;
                }

                Patient patient = new Patient(id, name, age, disease);
                patients.add(patient);
                JOptionPane.showMessageDialog(this, "Patient added successfully!");

                idField.setText("");
                nameField.setText("");
                ageField.setText("");
                diseaseField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Age!");
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createAssignDoctorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Assign Doctor");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        JLabel doctorLabel = new JLabel("Select Doctor:");
        JComboBox<String> doctorCombo = new JComboBox<>();

        for (Doctor doctor : doctors) {
            if (doctor.isAvailable()) {
                doctorCombo.addItem(doctor.getName() + " - " + doctor.getSpecialization());
            }
        }

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(doctorLabel, gbc);
        gbc.gridx = 1;
        panel.add(doctorCombo, gbc);

        JButton assignButton = new JButton("Assign Doctor");
        assignButton.setBackground(new Color(34, 139, 34));
        assignButton.setForeground(Color.WHITE);
        assignButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Patient patient = findActivePatient(id);

                if (patient == null) {
                    JOptionPane.showMessageDialog(this, "Patient not found or already discharged!");
                    return;
                }

                if (!patient.getAssignedDoctor().equals("Not assigned")) {
                    JOptionPane.showMessageDialog(this, "Patient already has a doctor assigned: " + patient.getAssignedDoctor());
                    return;
                }

                doctorCombo.removeAllItems();
                List<Doctor> availableDoctors = new ArrayList<>();
                for (Doctor doctor : doctors) {
                    if (doctor.isAvailable()) {
                        availableDoctors.add(doctor);
                        doctorCombo.addItem(doctor.getName() + " - " + doctor.getSpecialization());
                    }
                }

                if (availableDoctors.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No doctors available at the moment!");
                    return;
                }

                if (doctorCombo.getSelectedIndex() >= 0) {
                    Doctor selectedDoctor = availableDoctors.get(doctorCombo.getSelectedIndex());
                    patient.setAssignedDoctor(selectedDoctor.getName());
                    selectedDoctor.setAvailable(false);
                    JOptionPane.showMessageDialog(this, "Doctor " + selectedDoctor.getName() + " assigned to " + patient.getName());
                    idField.setText("");

                    doctorCombo.removeAllItems();
                    for (Doctor doctor : doctors) {
                        if (doctor.isAvailable()) {
                            doctorCombo.addItem(doctor.getName() + " - " + doctor.getSpecialization());
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Patient ID!");
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(assignButton, gbc);
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createAdmitPatientPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Admit Patient to Room");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        JLabel roomLabel = new JLabel("Room Number (101-110):");
        JTextField roomField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(roomLabel, gbc);
        gbc.gridx = 1;
        panel.add(roomField, gbc);

        JButton admitButton = new JButton("Admit Patient");
        admitButton.setBackground(new Color(34, 139, 34));
        admitButton.setForeground(Color.WHITE);
        admitButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                int roomNum = Integer.parseInt(roomField.getText().trim());

                Patient patient = findActivePatient(id);
                if (patient == null) {
                    JOptionPane.showMessageDialog(this, "Patient not found or already discharged!");
                    return;
                }

                if (patient.isAdmitted()) {
                    JOptionPane.showMessageDialog(this, "Patient is already admitted to room " + patient.getRoomNumber());
                    return;
                }

                if (roomNum < 101 || roomNum > 110) {
                    JOptionPane.showMessageDialog(this, "Invalid room number! Must be between 101-110.");
                    return;
                }

                int roomIndex = roomNum - 101;
                if (rooms[roomIndex]) {
                    JOptionPane.showMessageDialog(this, "Room " + roomNum + " is already occupied!");
                    return;
                }

                rooms[roomIndex] = true;
                patient.setRoomNumber(roomNum);
                patient.setAdmitted(true);
                JOptionPane.showMessageDialog(this, "Patient " + patient.getName() + " admitted to room " + roomNum);

                idField.setText("");
                roomField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!");
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(admitButton, gbc);
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createGenerateBillPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Generate Bill");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        JTextArea billArea = new JTextArea(10, 30);
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(billArea);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        gbc.gridwidth = 1;
        JButton generateButton = new JButton("Generate Bill");
        generateButton.setBackground(new Color(34, 139, 34));
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Patient patient = findActivePatient(id);

                if (patient == null) {
                    JOptionPane.showMessageDialog(this, "Patient not found or already discharged!");
                    return;
                }

                double roomCharge = 100.0;
                double medicineCharge = 50.0;
                double consultationCharge = 75.0;
                double total = roomCharge + medicineCharge + consultationCharge;

                patient.setBill(total);

                StringBuilder bill = new StringBuilder();
                bill.append("===== HOSPITAL BILL =====\n\n");
                bill.append("Patient: ").append(patient.getName()).append("\n");
                bill.append("ID: ").append(patient.getId()).append("\n");
                bill.append("Age: ").append(patient.getAge()).append("\n");
                bill.append("Disease: ").append(patient.getDisease()).append("\n\n");
                bill.append("Room Charge:         $").append(String.format("%.2f", roomCharge)).append("\n");
                bill.append("Medicine Charge:     $").append(String.format("%.2f", medicineCharge)).append("\n");
                bill.append("Consultation Charge: $").append(String.format("%.2f", consultationCharge)).append("\n");
                bill.append("-------------------------\n");
                bill.append("Total Amount:        $").append(String.format("%.2f", total)).append("\n");

                billArea.setText(bill.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Patient ID!");
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(generateButton, gbc);
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createDischargePatientPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Discharge Patient");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        JButton dischargeButton = new JButton("Discharge Patient");
        dischargeButton.setBackground(new Color(34, 139, 34));
        dischargeButton.setForeground(Color.WHITE);
        dischargeButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Patient patient = findActivePatient(id);

                if (patient == null) {
                    JOptionPane.showMessageDialog(this, "Patient not found or already discharged!");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to discharge patient " + patient.getName() + "?",
                        "Confirm Discharge", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (patient.isAdmitted()) {
                        int roomIndex = patient.getRoomNumber() - 101;
                        rooms[roomIndex] = false;
                    }

                    String doctorName = patient.getAssignedDoctor();
                    if (!doctorName.equals("Not assigned")) {
                        for (Doctor doctor : doctors) {
                            if (doctor.getName().equals(doctorName)) {
                                doctor.setAvailable(true);
                                break;
                            }
                        }
                    }

                    patient.setDischarged(true);
                    patient.setAdmitted(false);

                    JOptionPane.showMessageDialog(this, "Patient " + patient.getName() + " has been discharged successfully!");
                    idField.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Patient ID!");
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(dischargeButton, gbc);
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createViewPatientsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("All Patients", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea patientArea = new JTextArea(20, 50);
        patientArea.setEditable(false);
        patientArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(patientArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(34, 139, 34));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            if (patients.isEmpty()) {
                sb.append("No patients in the system!");
            } else {
                for (Patient patient : patients) {
                    sb.append("ID: ").append(patient.getId()).append(" | Name: ").append(patient.getName())
                            .append(" | Age: ").append(patient.getAge()).append(" | Disease: ").append(patient.getDisease()).append("\n");
                    sb.append("Doctor: ").append(patient.getAssignedDoctor())
                            .append(" | Room: ").append(patient.getRoomNumber() == 0 ? "Not assigned" : patient.getRoomNumber())
                            .append(" | Status: ").append(patient.isDischarged() ? "Discharged" : "Active").append("\n");
                    sb.append("Bill: $").append(String.format("%.2f", patient.getBill())).append("\n");
                    sb.append("-------------------------------------------\n");
                }
            }
            patientArea.setText(sb.toString());
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createViewRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Room Status", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea roomArea = new JTextArea(15, 40);
        roomArea.setEditable(false);
        roomArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(roomArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(34, 139, 34));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("===== ROOM STATUS =====\n\n");
            for (int i = 0; i < rooms.length; i++) {
                int roomNum = i + 101;
                String status = rooms[i] ? "Occupied" : "Available";
                sb.append("Room ").append(roomNum).append(": ").append(status).append("\n");
            }
            roomArea.setText(sb.toString());
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private Patient findPatient(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }

    private Patient findActivePatient(int id) {
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