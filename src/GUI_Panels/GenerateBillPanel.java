package GUI_Panels;

import models.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GenerateBillPanel extends JPanel {

    public GenerateBillPanel(Object parent, CardLayout cardLayout, JPanel mainPanel,
                             List<Patient> patients) {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Generate Bill");
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

        JTextArea billArea = new JTextArea(10, 30);
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(billArea);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(scrollPane, gbc);

        gbc.gridwidth = 1;
        JButton generateButton = new JButton("Generate Bill");
        generateButton.setBackground(new Color(34, 139, 34));
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(e -> {
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

                double roomCharge = 100.0;
                double medicineCharge = 50.0;
                double consultationCharge = 75.0;
                double total = roomCharge + medicineCharge + consultationCharge;

                patient.getClass().getMethod("setBill", double.class).invoke(patient, total);

                String name = (String) patient.getClass().getMethod("getName").invoke(patient);
                int patId = (int) patient.getClass().getMethod("getId").invoke(patient);
                int age = (int) patient.getClass().getMethod("getAge").invoke(patient);
                String disease = (String) patient.getClass().getMethod("getDisease").invoke(patient);

                StringBuilder bill = new StringBuilder();
                bill.append("===== HOSPITAL BILL =====\n\n");
                bill.append("models.Patient: ").append(name).append("\n");
                bill.append("ID: ").append(patId).append("\n");
                bill.append("Age: ").append(age).append("\n");
                bill.append("Disease: ").append(disease).append("\n\n");
                bill.append("Room Charge:         $").append(String.format("%.2f", roomCharge))
                        .append("\n");
                bill.append("Medicine Charge:     $").append(String.format("%.2f", medicineCharge)).
                        append("\n");
                bill.append("Consultation Charge: $").append(String.format("%.2f", consultationCharge))
                        .append("\n");
                bill.append("-------------------------\n");
                bill.append("Total Amount:        $").append(String.format("%.2f", total))
                        .append("\n");

                billArea.setText(bill.toString());
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
        add(generateButton, gbc);
        gbc.gridx = 1;
        add(backButton, gbc);
    }
}