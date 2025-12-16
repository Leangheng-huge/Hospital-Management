package GUI_Panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddPatientPanel extends JPanel {

    public AddPatientPanel(Object parent, CardLayout cardLayout, JPanel mainPanel, List patients) {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Add New Patient");
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

        JLabel nameLabel = new JLabel("Patient Name:");
        JTextField nameField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 2;
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 3;
        add(ageLabel, gbc);
        gbc.gridx = 1;
        add(ageField, gbc);

        JLabel diseaseLabel = new JLabel("Disease:");
        JTextField diseaseField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 4;
        add(diseaseLabel, gbc);
        gbc.gridx = 1;
        add(diseaseField, gbc);

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

                // Check if patient exists using reflection to call getId()
                boolean exists = false;
                for (Object obj : patients) {
                    try {
                        int patientId = (int) obj.getClass().getMethod("getId").invoke(obj);
                        if (patientId == id) {
                            exists = true;
                            break;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (exists) {
                    JOptionPane.showMessageDialog(this, "models.Patient with ID " + id + " already exists!");
                    return;
                }

                // Create models.Patient using reflection
                Class<?> patientClass = Class.forName("models.Patient");
                Object patient = patientClass.getConstructor(int.class, String.class, int.class, String.class)
                        .newInstance(id, name, age, disease);
                patients.add(patient);

                JOptionPane.showMessageDialog(this, "models.Patient added successfully!");

                idField.setText("");
                nameField.setText("");
                ageField.setText("");
                diseaseField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID and Age!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error creating patient: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        gbc.gridx = 0; gbc.gridy = 5;
        add(addButton, gbc);
        gbc.gridx = 1;
        add(backButton, gbc);
    }
}
