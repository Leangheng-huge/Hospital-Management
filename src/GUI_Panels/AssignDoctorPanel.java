package GUI_Panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AssignDoctorPanel extends JPanel {

    public AssignDoctorPanel(Object parent, CardLayout cardLayout, JPanel mainPanel,
                             List doctors, List patients) {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Assign Doctor");
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

        JLabel doctorLabel = new JLabel("Select Doctor:");
        JComboBox<String> doctorCombo = new JComboBox<>();

        // Populate doctors using reflection
        for (Object doc : doctors) {
            try {
                boolean available = (boolean) doc.getClass().getMethod("isAvailable").invoke(doc);
                if (available) {
                    String name = (String) doc.getClass().getMethod("getName").invoke(doc);
                    String spec = (String) doc.getClass().getMethod("getSpecialization").invoke(doc);
                    doctorCombo.addItem(name + " - " + spec);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        gbc.gridx = 0; gbc.gridy = 2;
        add(doctorLabel, gbc);
        gbc.gridx = 1;
        add(doctorCombo, gbc);

        JButton assignButton = new JButton("Assign models.Doctor");
        assignButton.setBackground(new Color(34, 139, 34));
        assignButton.setForeground(Color.WHITE);
        assignButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());

                // Find active patient using reflection
                Object patient = null;
                for (Object p : patients) {
                    int pId = (int) p.getClass().getMethod("getId").invoke(p);
                    boolean discharged = (boolean) p.getClass().getMethod("isDischarged").invoke(p);
                    if (pId == id && !discharged) {
                        patient = p;
                        break;
                    }
                }

                if (patient == null) {
                    JOptionPane.showMessageDialog(this,
                            "Patient not found or already discharged!");
                    return;
                }

                String assignedDoc = (String) patient.getClass().getMethod("getAssignedDoctor")
                        .invoke(patient);
                if (!assignedDoc.equals("Not assigned")) {
                    JOptionPane.showMessageDialog(this,
                            "Patient already has a doctor assigned: " + assignedDoc);
                    return;
                }

                doctorCombo.removeAllItems();
                List<Object> availableDoctors = new ArrayList<>();
                for (Object doctor : doctors) {
                    boolean available = (boolean) doctor.getClass().getMethod("isAvailable")
                            .invoke(doctor);
                    if (available) {
                        availableDoctors.add(doctor);
                        String name = (String) doctor.getClass().getMethod("getName")
                                .invoke(doctor);
                        String spec = (String) doctor.getClass().getMethod("getSpecialization")
                                .invoke(doctor);
                        doctorCombo.addItem(name + " - " + spec);
                    }
                }

                if (availableDoctors.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "No doctors available at the moment!");
                    return;
                }

                if (doctorCombo.getSelectedIndex() >= 0) {
                    Object selectedDoctor = availableDoctors.get(doctorCombo.getSelectedIndex());
                    String docName = (String) selectedDoctor.getClass().getMethod("getName")
                            .invoke(selectedDoctor);
                    String patName = (String) patient.getClass().getMethod("getName")
                            .invoke(patient);

                    patient.getClass().getMethod("setAssignedDoctor", String.class)
                            .invoke(patient, docName);
                    selectedDoctor.getClass().getMethod("setAvailable", boolean.class)
                            .invoke(selectedDoctor, false);

                    JOptionPane.showMessageDialog(this,
                            "Doctor " + docName + " assigned to " + patName);
                    idField.setText("");

                    doctorCombo.removeAllItems();
                    for (Object doctor : doctors) {
                        boolean available = (boolean) doctor.getClass().getMethod("isAvailable")
                                .invoke(doctor);
                        if (available) {
                            String name = (String) doctor.getClass().getMethod("getName")
                                    .invoke(doctor);
                            String spec = (String) doctor.getClass().getMethod("getSpecialization")
                                    .invoke(doctor);
                            doctorCombo.addItem(name + " - " + spec);
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid Patient ID!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridx = 0; gbc.gridy = 3;
        add(assignButton, gbc);
        gbc.gridx = 1;
        add(backButton, gbc);
    }
}