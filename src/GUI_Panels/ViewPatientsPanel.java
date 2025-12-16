package GUI_Panels;

import models.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ViewPatientsPanel extends JPanel {

    public ViewPatientsPanel(CardLayout cardLayout, JPanel mainPanel, List<Patient> patients) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("All Patients", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JTextArea patientArea = new JTextArea(20, 50);
        patientArea.setEditable(false);
        patientArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(patientArea);
        add(scrollPane, BorderLayout.CENTER);

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
                    sb.append("ID: ").append(patient.getId()).append(" | Name: ")
                            .append(patient.getName())
                            .append(" | Age: ").append(patient.getAge()).append(" | Disease: ")
                            .append(patient.getDisease()).append("\n");
                    sb.append("models.Doctor: ").append(patient.getAssignedDoctor())
                            .append(" | Room: ").append(patient.getRoomNumber() == 0 ?
                                    "Not assigned" : patient.getRoomNumber())
                            .append(" | Status: ").append(patient.isDischarged() ?
                                    "Discharged" : "Active").append("\n");
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
        add(buttonPanel, BorderLayout.SOUTH);
    }
}