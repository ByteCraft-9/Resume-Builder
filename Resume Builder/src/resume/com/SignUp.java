package resume.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp extends JFrame {

    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField jTextField1;
    private JLabel jLabel6;
    private JTextField jTextField2;
    private JLabel jLabel7;
    private JPasswordField jPasswordField1;
    private JLabel jLabel8;
    private JButton jButton1;
    private JButton jButton2;

    // JDBC URL, username and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/resumebuilder";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public SignUp() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));

        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel2 = new JPanel();
        jPanel2.setBackground(new Color(0, 102, 102));

        jLabel1 = new JLabel(new ImageIcon("logo.png"));
        jLabel2 = new JLabel("Resume Builder");
        jLabel2.setFont(new Font("Showcard Gothic", Font.PLAIN, 24));
        jLabel2.setForeground(Color.WHITE);
        jLabel3 = new JLabel("copyright © Resume Builder All rights reserved");
        jLabel3.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
        jLabel3.setForeground(new Color(204, 204, 204));

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel2)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(64, 64, 64))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 400, 500);

        jPanel3 = new JPanel();
        jPanel3.setBackground(new Color(255, 255, 255));

        jLabel4 = new JLabel("SIGN UP");
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 24));

        jLabel5 = new JLabel("Full name");
        jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jTextField1 = new JTextField();
        jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField1.setForeground(new Color(102, 102, 102));

        jLabel6 = new JLabel("Email");
        jLabel6.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jTextField2 = new JTextField();
        jTextField2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField2.setForeground(new Color(102, 102, 102));

        jLabel7 = new JLabel("Password");
        jLabel7.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jPasswordField1 = new JPasswordField();

        jLabel8 = new JLabel("I've an account");
        jLabel8.setFont(new Font("Segoe UI", Font.BOLD, 13));

        jButton1 = new JButton("Sign Up");
        jButton1.setBackground(new Color(0, 102, 102));
        jButton1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setForeground(Color.WHITE);
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2 = new JButton("Login");
        jButton2.setBackground(new Color(0, 102, 102));
        jButton2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jButton2.setForeground(Color.WHITE);
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jTextField1)
                            .addComponent(jLabel6)
                            .addComponent(jTextField2, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addComponent(jPasswordField1)
                            .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel7)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(400, 0, 400, 500);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 800, 500);

        pack();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        String fullName = jTextField1.getText();
        String email = jTextField2.getText();
        String password = String.valueOf(jPasswordField1.getPassword());

        // Validate input
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert into database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fullName);
                stmt.setString(2, email);
                stmt.setString(3, password);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Sign up successful. Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    Login loginFrame = new Login();
                    loginFrame.setVisible(true);
                    loginFrame.pack();
                    loginFrame.setLocationRelativeTo(null);
                    dispose(); // Close sign up frame
                } else {
                    JOptionPane.showMessageDialog(this, "Sign up failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        Login loginFrame = new Login();
        loginFrame.setVisible(true);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        dispose(); // Close sign up frame
    }
}