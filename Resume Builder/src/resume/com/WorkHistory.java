package resume.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkHistory extends JPanel {
    private JLabel l, l1, l2, l3, l4, l5, l6 ;
    private JTextField t1, t2, t3, t4, t5, t6;
    private JCheckBox currentCheckBox;
    private JButton backButton, nextButton, updateButton;
    private Font f, font;
    private ActionListener backListener, nextListener;

    // JDBC URL, username and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/resumebuilder";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    // User ID field to store current user's ID
    private int userId; // Store the user ID here

    public WorkHistory(int userId) {
        this.userId = userId; // Assign the passed user ID to the class field

        setLayout(null);
        setBackground(new Color(0, 102, 102));

        f = new Font("Segoe UI", Font.BOLD, 30);
        font = new Font("Segoe UI", Font.PLAIN, 16);

        l = new JLabel("Work History");
        l.setForeground(Color.WHITE);
        l.setFont(f);

        l1 = new JLabel("Job Title");
        l1.setFont(font);
        l1.setForeground(Color.WHITE);

        l2 = new JLabel("Company");
        l2.setFont(font);
        l2.setForeground(Color.WHITE);

        l3 = new JLabel("Position");
        l3.setFont(font);
        l3.setForeground(Color.WHITE);

        l4 = new JLabel("Start Date");
        l4.setFont(font);
        l4.setForeground(Color.WHITE);

        l5 = new JLabel("End Date");
        l5.setFont(font);
        l5.setForeground(Color.WHITE);

        l6 = new JLabel("Location");
        l6.setFont(font);
        l6.setForeground(Color.WHITE);

        t1 = new JTextField();
        t1.setFont(font);

        t2 = new JTextField();
        t2.setFont(font);

        t3 = new JTextField();
        t3.setFont(font);

        t4 = new JTextField();
        t4.setFont(font);

        t5 = new JTextField();
        t5.setFont(font);

        t6 = new JTextField();
        t6.setFont(font);

        currentCheckBox = new JCheckBox("Currently worked here");
        currentCheckBox.setFont(font);
        currentCheckBox.setForeground(Color.WHITE);
        currentCheckBox.setBackground(new Color(0, 102, 102));

        backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backButton.setForeground(new Color(0, 102, 102));
        backButton.setBackground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (backListener != null) {
                    backListener.actionPerformed(e);
                }
            }
        });

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nextButton.setForeground(new Color(0, 102, 102));
        nextButton.setBackground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWorkHistory();
                if (nextListener != null) {
                    nextListener.actionPerformed(e);
                }
            }
        });

        updateButton = new JButton("Update");
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        updateButton.setForeground(new Color(0, 102, 102));
        updateButton.setBackground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setBorderPainted(false);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateWorkHistory();
            }
        });

        // set bounds
        l.setBounds(150, 10, 250, 50);
        l1.setBounds(50, 91, 100, 40);
        l2.setBounds(50, 151, 100, 40);
        l3.setBounds(50, 211, 100, 40);
        l4.setBounds(50, 271, 100, 40);
        l5.setBounds(50, 331, 100, 40);
        l6.setBounds(50, 391, 100, 40);
        t1.setBounds(255, 91, 200, 40);
        t2.setBounds(255, 151, 200, 40);
        t3.setBounds(255, 211, 200, 40);
        t4.setBounds(255, 271, 200, 40);
        t5.setBounds(255, 331, 200, 40);
        t6.setBounds(255, 391, 200, 40);
        currentCheckBox.setBounds(50, 451, 200, 40);
        backButton.setBounds(50, 500, 100, 50);
        nextButton.setBounds(300, 500, 100, 50);
        updateButton.setBounds(175, 500, 100, 50);

        add(l);
        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);
        add(l6);
        add(t1);
        add(t2);
        add(t3);
        add(t4);
        add(t5);
        add(t6);
        add(currentCheckBox);
        add(backButton);
        add(nextButton);
        add(updateButton);
    }

    private void saveWorkHistory() {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String sql = "INSERT INTO work_history (user_id, job_title, company, position, start_date, end_date, location, current_job) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Use the stored userId field
            pstmt.setInt(1, userId);
            pstmt.setString(2, t1.getText());
            pstmt.setString(3, t2.getText());
            pstmt.setString(4, t3.getText());
            pstmt.setString(5, t4.getText());
            pstmt.setString(6, t5.getText());
            pstmt.setString(7, t6.getText());
            pstmt.setBoolean(8, currentCheckBox.isSelected());

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    private void updateWorkHistory() {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            String sql = "UPDATE work_history SET job_title=?, company=?, position=?, start_date=?, end_date=?, location=?, current_job=? " +
                         "WHERE user_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Use the stored userId field
            pstmt.setString(1, t1.getText());
            pstmt.setString(2, t2.getText());
            pstmt.setString(3, t3.getText());
            pstmt.setString(4, t4.getText());
            pstmt.setString(5, t5.getText());
            pstmt.setString(6, t6.getText());
            pstmt.setBoolean(7, currentCheckBox.isSelected());
            pstmt.setInt(8, userId);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            JOptionPane.showMessageDialog(this, "Data updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage());
        }
    }

    public void setBackListener(ActionListener backListener) {
        this.backListener = backListener;
    }

    public void setNextListener(ActionListener nextListener) {
        this.nextListener = nextListener;
    }
}
