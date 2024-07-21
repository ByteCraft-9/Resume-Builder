package resume.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Experience extends JPanel {
    private JLabel l, l1, l2, l5;
    private JTextField t1, t2;
    private JComboBox<String> degreeComboBox;
    private JButton plusButton, updateButton, backButton, nextButton;
    private JPanel fieldsPanel;
    private JScrollPane scrollPane;
    private ActionListener backListener;
    private ActionListener nextListener;
    private final String[] level = {"Beginner", "Intermediate", "Expert"};

    // JDBC URL, username and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/resumebuilder";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    private int userId; // Add userId as an instance variable

    public Experience(int userId) {
        this.userId = userId; // Initialize userId
        setLayout(new BorderLayout());
        setBackground(new Color(0, 102, 102));

        l = new JLabel("Skills");
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.BOLD, 30));
        l.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 102));
        topPanel.add(l);

        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(null);
        fieldsPanel.setBackground(new Color(0, 102, 102));
        createFieldsPanel();

        scrollPane = new JScrollPane(fieldsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        plusButton = new JButton("+");
        initializeButton(plusButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSkillEntry();
            }
        });

        backButton = new JButton("Back");
        initializeButton(backButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (backListener != null) {
                    backListener.actionPerformed(e);
                }
            }
        });

        nextButton = new JButton("Next");
        initializeButton(nextButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSkillEntries();
                if (nextListener != null) {
                    nextListener.actionPerformed(e);
                }
            }
        });

        updateButton = new JButton("Update");
        initializeButton(updateButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSkillEntries();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 102, 102));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(plusButton);
        buttonPanel.add(updateButton);

        add(buttonPanel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(900, 600));
    }

    private void initializeButton(JButton button, ActionListener actionListener) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(new Color(0, 102, 102));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(actionListener);
    }

    private void createFieldsPanel() {
        l1 = new JLabel("Skill");
        l1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l1.setForeground(Color.WHITE);

        l2 = new JLabel("Description");
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l2.setForeground(Color.WHITE);

        l5 = new JLabel("Level");
        l5.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l5.setForeground(Color.WHITE);

        t1 = new JTextField();
        t1.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        t2 = new JTextField();
        t2.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        degreeComboBox = new JComboBox<>(level);
        degreeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // set bounds
        l1.setBounds(50, 20, 150, 40);
        l2.setBounds(50, 80, 100, 40);
        l5.setBounds(50, 140, 100, 40);

        t1.setBounds(255, 20, 200, 40);
        t2.setBounds(255, 80, 200, 40);
        degreeComboBox.setBounds(255, 140, 200, 40);

        // add components to fields panel
        fieldsPanel.add(l1);
        fieldsPanel.add(l2);
        fieldsPanel.add(l5);
        fieldsPanel.add(t1);
        fieldsPanel.add(t2);
        fieldsPanel.add(degreeComboBox);
    }

    private void addSkillEntry() {
        Component[] components = fieldsPanel.getComponents();
        int lastEntryY = 0;
        if (components.length > 0) {
            Component lastComponent = components[components.length - 1];
            lastEntryY = lastComponent.getY() + lastComponent.getHeight() + 20; // 20 pixels spacing
        }

        JLabel l1New = new JLabel("Skill");
        l1New.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l1New.setForeground(Color.WHITE);

        JLabel l2New = new JLabel("Description");
        l2New.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l2New.setForeground(Color.WHITE);

        JLabel l3New = new JLabel("Level");
        l3New.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l3New.setForeground(Color.WHITE);

        JTextField t1New = new JTextField();
        t1New.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JTextField t2New = new JTextField();
        t2New.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JComboBox<String> degreeComboBoxNew = new JComboBox<>(level);
        degreeComboBoxNew.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        l1New.setBounds(50, lastEntryY, 150, 40);
        l2New.setBounds(50, lastEntryY + 40, 100, 40);
        l3New.setBounds(50, lastEntryY + 80, 100, 40);
        t1New.setBounds(255, lastEntryY, 200, 40);
        t2New.setBounds(255, lastEntryY + 40, 200, 40);
        degreeComboBoxNew.setBounds(255, lastEntryY + 80, 200, 40);

        fieldsPanel.add(l1New);
        fieldsPanel.add(l2New);
        fieldsPanel.add(l3New);
        fieldsPanel.add(t1New);
        fieldsPanel.add(t2New);
        fieldsPanel.add(degreeComboBoxNew);

        scrollPane.setViewportView(fieldsPanel);
        fieldsPanel.setPreferredSize(new Dimension(500, lastEntryY + 200));
        revalidate();
        repaint();
    }

    private void saveSkillEntries() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO skills (user_id, skill_name, description, level) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            Component[] components = fieldsPanel.getComponents();
            for (int i = 0; i < components.length; i += 6) {
                JTextField skillNameField = (JTextField) components[i + 3];
                JTextField descriptionField = (JTextField) components[i + 4];
                JComboBox<String> levelComboBox = (JComboBox<String>) components[i + 5];

                pstmt.setInt(1, userId);
                pstmt.setString(2, skillNameField.getText());
                pstmt.setString(3, descriptionField.getText());
                pstmt.setString(4, (String) levelComboBox.getSelectedItem());

                pstmt.executeUpdate();
            }
            pstmt.close();
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Data saving: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateSkillEntries() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE skills SET skill_name = ?, description = ?, level = ? WHERE user_id = ? AND skill_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            Component[] components = fieldsPanel.getComponents();
            for (int i = 0; i < components.length; i += 6) {
                JTextField skillNameField = (JTextField) components[i + 3];
                JTextField descriptionField = (JTextField) components[i + 4];
                JComboBox<String> levelComboBox = (JComboBox<String>) components[i + 5];

                pstmt.setString(1, skillNameField.getText());
                pstmt.setString(2, descriptionField.getText());
                pstmt.setString(3, (String) levelComboBox.getSelectedItem());
                pstmt.setInt(4, userId);
                pstmt.setString(5, skillNameField.getText());

                pstmt.executeUpdate();
            }
            pstmt.close();
            JOptionPane.showMessageDialog(this, "Data updated successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void setBackListener(ActionListener backListener) {
        this.backListener = backListener;
    }

    public void setNextListener(ActionListener nextListener) {
        this.nextListener = nextListener;
    }
}
