package resume.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PersonalInfo extends JPanel {
    private JLabel l, l1, l2, l3, l4, l5, l6, l8;
    private JTextField t1, t2, t3, t4, t5, t6;
    private JButton saveButton, updateButton, uploadButton;
    private Font f, font;
    private String imagePath;
    private ActionListener nextListener;
    private int userId;

    public PersonalInfo(int userId) {
        this.userId = userId;
        setLayout(null);
        setBackground(new Color(0, 102, 102));

        f = new Font("Segoe UI", Font.BOLD, 30);
        font = new Font("Segoe UI", Font.PLAIN, 16);

        l = new JLabel("Personal Details");
        l.setForeground(new Color(255, 255, 255));
        l.setFont(f);

        l8 = new JLabel("Upload Photo");
        l8.setFont(font);
        l8.setForeground(Color.WHITE);

        l1 = new JLabel("Name");
        l1.setFont(font);
        l1.setForeground(Color.WHITE);

        l2 = new JLabel("Date Of Birth");
        l2.setFont(font);
        l2.setForeground(Color.WHITE);

        l3 = new JLabel("Father Name");
        l3.setFont(font);
        l3.setForeground(Color.WHITE);

        l4 = new JLabel("Nationality");
        l4.setFont(font);
        l4.setForeground(Color.WHITE);

        l5 = new JLabel("Phone Number");
        l5.setFont(font);
        l5.setForeground(Color.WHITE);

        l6 = new JLabel("Email ID");
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

        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        saveButton.setForeground(new Color(0, 102, 102));
        saveButton.setBackground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveInfo();
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
                updateInfo();
            }
        });

        uploadButton = new JButton("Upload Photo");
        uploadButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        uploadButton.setForeground(new Color(0, 102, 102));
        uploadButton.setBackground(Color.WHITE);
        uploadButton.setFocusPainted(false);
        uploadButton.setBorderPainted(false);
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadPhoto();
            }
        });

        l.setBounds(150, 10, 250, 50);
        l1.setBounds(50, 91, 100, 40);
        l2.setBounds(50, 151, 100, 40);
        l3.setBounds(50, 211, 100, 40);
        l4.setBounds(50, 271, 100, 40);
        l5.setBounds(50, 331, 134, 40);
        l6.setBounds(50, 391, 100, 40);
        l8.setBounds(50, 451, 134, 40);
        t1.setBounds(255, 91, 200, 40);
        t2.setBounds(255, 151, 200, 40);
        t3.setBounds(255, 211, 200, 40);
        t4.setBounds(255, 271, 200, 40);
        t5.setBounds(255, 331, 200, 40);
        t6.setBounds(255, 391, 200, 40);
        saveButton.setBounds(350, 510, 120, 50);
        updateButton.setBounds(150, 510, 120, 50);
        uploadButton.setBounds(255, 451, 200, 40);

        add(l);
        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(l5);
        add(l6);
        add(l8);
        add(t1);
        add(t2);
        add(t3);
        add(t4);
        add(t5);
        add(t6);
        add(saveButton);
        add(updateButton);
        add(uploadButton);
    }

    public void setNextListener(ActionListener nextListener) {
        this.nextListener = nextListener;
    }

    private void uploadPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            imagePath = selectedFile.getAbsolutePath();
            l8.setText(selectedFile.getName());
        }
    }

    private void saveInfo() {
        String name = t1.getText();
        String dob = t2.getText();
        String fatherName = t3.getText();
        String nationality = t4.getText();
        String phoneNumber = t5.getText();
        String email = t6.getText();

        if (!isValidDate(dob)) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/resumebuilder";
        String username = "root";
        String password = "";

        String query = "INSERT INTO personalinfo (user_id, name, date_of_birth, father_name, nationality, phone_number, email_id, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, name);
            pstmt.setString(3, dob);
            pstmt.setString(4, fatherName);
            pstmt.setString(5, nationality);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, email);

            if (imagePath != null) {
                try (FileInputStream fis = new FileInputStream(imagePath)) {
                    pstmt.setBinaryStream(8, fis, (int) new File(imagePath).length());
                    pstmt.executeUpdate();
                }
            } else {
                pstmt.setNull(8, java.sql.Types.BLOB);
                pstmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Data saved successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "File not found: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error handling file: " + e.getMessage());
        }
    }

        private void updateInfo() {
        String name = t1.getText();
        String dob = t2.getText();
        String fatherName = t3.getText();
        String nationality = t4.getText();
        String phoneNumber = t5.getText();
        String email = t6.getText();

        if (!isValidDate(dob)) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/resumebuilder";
        String username = "root";
        String password = "";

        String query = "UPDATE personalinfo SET name=?, date_of_birth=?, father_name=?, nationality=?, phone_number=?, email_id=?, photo=? WHERE user_id=?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, dob);
            pstmt.setString(3, fatherName);
            pstmt.setString(4, nationality);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, email);

            if (imagePath != null) {
                try (FileInputStream fis = new FileInputStream(imagePath)) {
                    pstmt.setBinaryStream(7, fis, (int) new File(imagePath).length());
                    pstmt.setInt(8, userId);
                    int updated = pstmt.executeUpdate(); // Execute SQL statement here after setting parameters

                    if (updated > 0) {
                        JOptionPane.showMessageDialog(this, "Data updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "No record found to update.");
                    }
                }
            } else {
                pstmt.setNull(7, java.sql.Types.BLOB);
                pstmt.setInt(8, userId);
                int updated = pstmt.executeUpdate(); // Execute SQL statement here after setting parameters

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Data updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No record found to update.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "File not found: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error handling file: " + e.getMessage());
        }
    }

private boolean isValidDate(String date) {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    df.setLenient(false);
    try {
        df.parse(date);
        return true;
    } catch (ParseException e) {
        return false;
    }
}
}

