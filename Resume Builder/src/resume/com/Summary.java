package resume.com;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Summary extends JPanel {

    private JLabel titleLabel;
    private JTextArea summaryTextArea;
    private JScrollPane scrollPane;
    private JButton backButton, nextButton;
    private ResultSet resultSet;
    private JLabel photoLabel;
    private int userId; // To store the user id

    public Summary(int userId) {
        this.userId = userId; // Initialize user id

        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light gray background

        titleLabel = new JLabel("Summary");
        titleLabel.setForeground(new Color(0, 102, 102)); // Dark teal color
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0)); // Add padding

        summaryTextArea = new JTextArea();
        summaryTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        summaryTextArea.setEditable(false);
        summaryTextArea.setMargin(new Insets(10, 10, 10, 10));

        scrollPane = new JScrollPane(summaryTextArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102))); // Dark teal border
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102))); // Dark teal border
        photoLabel.setPreferredSize(new Dimension(150, 150));

        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        photoPanel.add(photoLabel, BorderLayout.CENTER);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        textPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        infoPanel.add(photoPanel, BorderLayout.WEST);
        infoPanel.add(textPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Add spacing between buttons

        backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backButton.setForeground(new Color(0, 102, 102)); // Dark teal color
        backButton.setBackground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> showPreviousEntry());

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nextButton.setForeground(new Color(0, 102, 102)); // Dark teal color
        nextButton.setBackground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(e -> showNextEntry());

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        add(titleLabel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        fetchDataFromDatabase(); // Fetch data from the database when the panel is initialized
    }

    private void fetchDataFromDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resumebuilder", "root", "");

            String sql = "SELECT * FROM personalinfo " +
                         "JOIN education ON personalinfo.user_id = education.user_id " +
                         "JOIN skills ON personalinfo.user_id = skills.user_id " +
                         "JOIN work_history ON personalinfo.user_id = work_history.user_id " +
                         "WHERE personalinfo.user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstmt.setInt(1, userId); // Use the userId parameter instead of hardcoding 1

            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                displayCurrentEntry();
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayCurrentEntry() {
        try {
            // Display photo
            byte[] photoBytes = resultSet.getBytes("photo");
            if (photoBytes != null && photoBytes.length > 0) {
                ImageIcon imageIcon = new ImageIcon(photoBytes);
                Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                photoLabel.setIcon(new ImageIcon(image));
            }

            // Display text summary
            StringBuilder summary = new StringBuilder();

            summary.append("Personal Information\n");
            summary.append("Name: ").append(resultSet.getString("name")).append("\n");
            summary.append("Date of Birth: ").append(resultSet.getString("date_of_birth")).append("\n");
            summary.append("Nationality: ").append(resultSet.getString("nationality")).append("\n");
            summary.append("Phone Number: ").append(resultSet.getString("phone_number")).append("\n");
            summary.append("Email: ").append(resultSet.getString("email_id")).append("\n");
            summary.append("\n");

            summary.append("Education\n");
            do {
                summary.append("Degree: ").append(resultSet.getString("degree")).append("\n");
                summary.append("Field of Study: ").append(resultSet.getString("field_of_study")).append("\n");
                summary.append("Institute Name: ").append(resultSet.getString("institute_name")).append("\n");
                summary.append("Location: ").append(resultSet.getString("location")).append("\n");
                summary.append("Time Period: ").append(resultSet.getString("time_period")).append("\n");
                summary.append("\n");
            } while (resultSet.next() && resultSet.getInt("user_id") == userId);
            resultSet.previous();

            summary.append("Skills\n");
            do {
                summary.append("Skill: ").append(resultSet.getString("skill_name")).append("\n");
                summary.append("Description: ").append(resultSet.getString("description")).append("\n");
                summary.append("Level: ").append(resultSet.getString("level")).append("\n");
                summary.append("\n");
            } while (resultSet.next() && resultSet.getInt("user_id") == userId);
            resultSet.previous();

            summary.append("Work History\n");
            do {
                summary.append("Job Title: ").append(resultSet.getString("job_title")).append("\n");
                summary.append("Company: ").append(resultSet.getString("company")).append("\n");
                summary.append("Position: ").append(resultSet.getString("position")).append("\n");
                summary.append("Start Date: ").append(resultSet.getString("start_date")).append("\n");
                summary.append("End Date: ").append(resultSet.getString("end_date")).append("\n");
                summary.append("Location: ").append(resultSet.getString("location")).append("\n");
                summary.append("\n");
            } while (resultSet.next() && resultSet.getInt("user_id") == userId);

            summaryTextArea.setText(summary.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setBackListener(ActionListener backListener) {
        this.backButton.addActionListener(backListener);
    }

    public void setNextListener(ActionListener nextListener) {
        this.nextButton.addActionListener(nextListener);
    }

    private void showNextEntry() {
        try {
            if (resultSet.next()) {
                displayCurrentEntry();
                backButton.setEnabled(true);
            } else {
                nextButton.setEnabled(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching next data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showPreviousEntry() {
        try {
            if (resultSet.previous()) {
                displayCurrentEntry();
                nextButton.setEnabled(true);
            } else {
                backButton.setEnabled(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching previous data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   
 
}
