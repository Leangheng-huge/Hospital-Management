package GUI_Panels;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(JFrame parent, CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

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
                    int confirm = JOptionPane.showConfirmDialog(parent,
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
            add(button, gbc);
        }
    }
}
