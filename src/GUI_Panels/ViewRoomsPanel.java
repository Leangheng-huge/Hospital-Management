package GUI_Panels;

import javax.swing.*;
import java.awt.*;

public class ViewRoomsPanel extends JPanel {

    public ViewRoomsPanel(Object parent, CardLayout cardLayout, JPanel mainPanel, boolean[] rooms) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 248, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Room Status", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JTextArea roomArea = new JTextArea(15, 40);
        roomArea.setEditable(false);
        roomArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(roomArea);
        add(scrollPane, BorderLayout.CENTER);

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
        add(buttonPanel, BorderLayout.SOUTH);
    }
}