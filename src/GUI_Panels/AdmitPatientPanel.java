package GUI_Panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdmitPatientPanel extends JPanel {

    public AdmitPatientPanel(Object parent, CardLayout cardLayout, JPanel mainPanel,
                             List patients, boolean[] rooms) {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Admit Patient to Room");
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

        JLabel roomLabel = new JLabel("Room Number (101-110):");
        JTextField roomField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 2;
        add(roomLabel, gbc);
        gbc.gridx = 1;
        add(roomField, gbc);

        JButton admitButton = new JButton("Admit Patient");
        admitButton.setBackground(new Color(34, 139, 34));
        admitButton.setForeground(Color.WHITE);
        admitButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                int roomNum = Integer.parseInt(roomField.getText().trim());

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

                boolean admitted = (boolean) patient.getClass().getMethod("isAdmitted")
                        .invoke(patient);
                if (admitted) {
                    int room = (int) patient.getClass().getMethod("getRoomNumber").invoke(patient);
                    JOptionPane.showMessageDialog(this,
                            "Patient is already admitted to room " + room);
                    return;
                }

                if (roomNum < 101 || roomNum > 110) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid room number! Must be between 101-110.");
                    return;
                }

                int roomIndex = roomNum - 101;
                if (rooms[roomIndex]) {
                    JOptionPane.showMessageDialog(this,
                            "Room " + roomNum + " is already occupied!");
                    return;
                }

                rooms[roomIndex] = true;
                patient.getClass().getMethod("setRoomNumber", int.class)
                        .invoke(patient, roomNum);
                patient.getClass().getMethod("setAdmitted", boolean.class)
                        .invoke(patient, true);

                String patName = (String) patient.getClass().getMethod("getName").invoke(patient);
                JOptionPane.showMessageDialog(this,
                        "Patient " + patName + " admitted to room " + roomNum);

                idField.setText("");
                roomField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid numbers!");
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
        add(admitButton, gbc);
        gbc.gridx = 1;
        add(backButton, gbc);
    }
}
