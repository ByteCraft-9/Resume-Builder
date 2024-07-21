package resume.com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Education extends JPanel {
    private JLabel l, l1, l2, l3, l4, l5;
    private List<JTextField> instituteNames, locations, fieldsOfStudy, timePeriods;
    private List<JComboBox<String>> degrees;
    private JButton plusButton, updateButton;
    private JPanel fieldsPanel;
    private JScrollPane scrollPane;
    private JButton backButton, nextButton;
    private ActionListener backListener;
    private ActionListener nextListener;
    private final String[] degreeNames = {"High School Diploma", "Bachelor's Degree", "Master's Degree", "Ph.D.", "Associate Degree"};

    // JDBC URL, username and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/resumebuilder";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    // Variable to store the currently logged-in user's ID
    private int userId;

    public Education(int userId) {
        this.userId = userId; // Store the userId

        setLayout(new BorderLayout());
        setBackground(new Color(0, 102, 102));

        l = new JLabel("Education");
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.BOLD, 30));
        l.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 102, 102));
        topPanel.add(l);

        fieldsPanel = new JPanel();
        fieldsPanel.setLayout(null);
        fieldsPanel.setBackground(new Color(0, 102, 102));

        instituteNames = new ArrayList<>();
        locations = new ArrayList<>();
        fieldsOfStudy = new ArrayList<>();
        timePeriods = new ArrayList<>();
        degrees = new ArrayList<>();

        createFieldsPanel();

        scrollPane = new JScrollPane(fieldsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        plusButton = new JButton("+");
        plusButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        plusButton.setForeground(new Color(0, 102, 102));
        plusButton.setBackground(Color.WHITE);
        plusButton.setFocusPainted(false);
        plusButton.setBorderPainted(false);
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEducationEntry();
            }
        });

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
                saveEducationDetails();
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
                updateEducationDetails(); // Update education details in the database
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

    private void createFieldsPanel() {
        l1 = new JLabel("Institute Name");
        l1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l1.setForeground(Color.WHITE);

        l2 = new JLabel("Location");
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l2.setForeground(Color.WHITE);

        l3 = new JLabel("Field of Study");
        l3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l3.setForeground(Color.WHITE);

        l4 = new JLabel("Time Period");
        l4.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l4.setForeground(Color.WHITE);

        l5 = new JLabel("Degree");
        l5.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l5.setForeground(Color.WHITE);

        JTextField t1 = new JTextField();
        JTextField t2 = new JTextField();
        JTextField t3 = new JTextField();
        JTextField t4 = new JTextField();
        JComboBox<String> degreeComboBox = new JComboBox<>(degreeNames);

        l1.setBounds(50, 20, 150, 40);
        l2.setBounds(50, 80, 100, 40);
        l3.setBounds(50, 140, 100, 40);
        l4.setBounds(50, 200, 100, 40);
        l5.setBounds(50, 260, 100, 40);
        t1.setBounds(255, 20, 200, 40);
        t2.setBounds(255, 80, 200, 40);
        t3.setBounds(255, 140, 200, 40);
        t4.setBounds(255, 200, 200, 40);
        degreeComboBox.setBounds(255, 260, 200, 40);

        fieldsPanel.add(l1);
        fieldsPanel.add(l2);
        fieldsPanel.add(l3);
        fieldsPanel.add(l4);
        fieldsPanel.add(l5);
        fieldsPanel.add(t1);
        fieldsPanel.add(t2);
        fieldsPanel.add(t3);
        fieldsPanel.add(t4);
        fieldsPanel.add(degreeComboBox);

        instituteNames.add(t1);
        locations.add(t2);
        fieldsOfStudy.add(t3);
        timePeriods.add(t4);
        degrees.add(degreeComboBox);
    }

    private void addEducationEntry() {
        Component[] components = fieldsPanel.getComponents();
        int lastEntryY = 0;
        if (components.length > 0) {
            Component lastComponent = components[components.length - 1];
            lastEntryY = lastComponent.getY() + lastComponent.getHeight() + 20;
        }

        JLabel l1New = new JLabel("Institute Name");
        l1New.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l1New.setForeground(Color.WHITE);

        JLabel l2New = new JLabel("Location");
        l2New.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l2New.setForeground(Color.WHITE);

        JLabel l3New = new JLabel("Field of Study");
        l3New.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l3New.setForeground(Color.WHITE);

        JLabel l4New = new JLabel("Time Period");
        l4New.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l4New.setForeground(Color.WHITE);

        JLabel l5New = new JLabel("Degree");
        l5New.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l5New.setForeground(Color.WHITE);

        JTextField t1New = new JTextField();
        t1New.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JTextField t2New = new JTextField();
        t2New.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JTextField t3New = new JTextField();
        t3New.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JTextField t4New = new JTextField();
        t4New.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JComboBox<String> degreeComboBoxNew = new JComboBox<>(degreeNames);
        degreeComboBoxNew.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        l1New.setBounds(50, lastEntryY, 150, 40);
        l2New.setBounds(50, lastEntryY + 40, 100, 40);
        l3New.setBounds(50, lastEntryY + 80, 100, 40);
        l4New.setBounds(50, lastEntryY + 120, 100, 40);
        l5New.setBounds(50, lastEntryY + 160,100, 40);
        t1New.setBounds(255, lastEntryY, 200, 40);
        t2New.setBounds(255, lastEntryY + 40, 200, 40);
        t3New.setBounds(255, lastEntryY + 80, 200, 40);
        t4New.setBounds(255, lastEntryY + 120, 200, 40);
        degreeComboBoxNew.setBounds(255, lastEntryY + 160, 200, 40);
        fieldsPanel.add(l1New);
        fieldsPanel.add(l2New);
        fieldsPanel.add(l3New);
        fieldsPanel.add(l4New);
        fieldsPanel.add(l5New);
        fieldsPanel.add(t1New);
        fieldsPanel.add(t2New);
        fieldsPanel.add(t3New);
        fieldsPanel.add(t4New);
        fieldsPanel.add(degreeComboBoxNew);

        instituteNames.add(t1New);
        locations.add(t2New);
        fieldsOfStudy.add(t3New);
        timePeriods.add(t4New);
        degrees.add(degreeComboBoxNew);

        scrollPane.setViewportView(fieldsPanel);
        fieldsPanel.setPreferredSize(new Dimension(500, lastEntryY + 200));

        revalidate();
        repaint();
    }

    private void saveEducationDetails() {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            String sql = "INSERT INTO education (user_id, institute_name, location, field_of_study, time_period, degree) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (int i = 0; i < instituteNames.size(); i++) {
                String instituteName = instituteNames.get(i).getText();
                String location = locations.get(i).getText();
                String fieldOfStudy = fieldsOfStudy.get(i).getText();
                String timePeriod = timePeriods.get(i).getText();
                String degree = (String) degrees.get(i).getSelectedItem();

                pstmt.setInt(1, userId);
                pstmt.setString(2, instituteName);
                pstmt.setString(3, location);
                pstmt.setString(4, fieldOfStudy);
                pstmt.setString(5, timePeriod);
                pstmt.setString(6, degree);

                pstmt.executeUpdate();
            }

            pstmt.close();
            conn.close();
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    private void updateEducationDetails() {
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            String sql = "UPDATE education SET institute_name = ?, location = ?, field_of_study = ?, time_period = ?, degree = ? " +
                         "WHERE user_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (int i = 0; i < instituteNames.size(); i++) {
                String instituteName = instituteNames.get(i).getText();
                String location = locations.get(i).getText();
                String fieldOfStudy = fieldsOfStudy.get(i).getText();
                String timePeriod = timePeriods.get(i).getText();
                String degree = (String) degrees.get(i).getSelectedItem();

                pstmt.setString(1, instituteName);
                pstmt.setString(2, location);
                pstmt.setString(3, fieldOfStudy);
                pstmt.setString(4, timePeriod);
                pstmt.setString(5, degree);
                pstmt.setInt(6, userId);

                pstmt.executeUpdate();
            }

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
