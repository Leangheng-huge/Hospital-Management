package GUI_Panels;

import models.Patient;
import models.Doctor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DischargePatientPanel extends JPanel {

    public DischargePatientPanel(Object parent, CardLayout cardLayout, JPanel mainPanel,
                                 List<Doctor> doctors, List<models.Patient> patients, boolean[] rooms) {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Discharge Patient");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel idLabel = new JLabel("Patient ID:");
        JTextField idField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 1;
        add(idLabel, gbc);
        gbc.gridx = 1;
        add(idField, gbc);

        JButton dischargeButton = new JButton("Discharge Patient");
        dischargeButton.setBackground(new Color(34, 139, 34));
        dischargeButton.setForeground(Color.WHITE);
        dischargeButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());

                // Find active patient
                Patient patient = null;
                for (Patient p : patients) {
                    if (p.getId() == id && !p.isDischarged()) {
                        patient = p;
                        break;
                    }
                }

                if (patient == null) {
                    JOptionPane.showMessageDialog(this,
                            "Patient not found or already discharged!");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to discharge patient " + patient.getName() + "?",
                        "Confirm Discharge", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Free up the room if patient was admitted
                    if (patient.isAdmitted()) {
                        int roomIndex = patient.getRoomNumber() - 101;
                        rooms[roomIndex] = false;
                    }

                    // Free up the doctor
                    String doctorName = patient.getAssignedDoctor();
                    if (!doctorName.equals("Not assigned")) {
                        for (Doctor doctor : doctors) {
                            if (doctor.getName().equals(doctorName)) {
                                doctor.setAvailable(true);
                                break;
                            }
                        }
                    }

                    // Discharge the patient
                    patient.setDischarged(true);
                    patient.setAdmitted(false);

                    JOptionPane.showMessageDialog(this,
                            "Patient " + patient.getName() + " has been discharged successfully!");
                    idField.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid models.Patient ID!");
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridx = 0; gbc.gridy = 2;
        add(dischargeButton, gbc);
        gbc.gridx = 1;
        add(backButton, gbc);
    }
}