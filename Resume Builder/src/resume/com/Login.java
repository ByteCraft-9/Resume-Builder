package resume.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    private JPanel jPanel1;
    private JPanel Right;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JPanel Left;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextField jTextField1;
    private JLabel jLabel3;
    private JPasswordField jPasswordField1;
    private JButton jButton1;
    private JLabel jLabel4;
    private JButton jButton2;

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/resumebuilder";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public Login() {
        initComponents();
    }

    private void initComponents() {
        setTitle("LOGIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 500));  // Set preferred size
        setSize(800, 500);  // Set size explicitly

        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(800, 500));  // Update preferred size
        jPanel1.setLayout(null);

        Right = new JPanel();
        Right.setBackground(new Color(0, 102, 102));
        Right.setPreferredSize(new Dimension(400, 500));  // Update dimensions

        jLabel5 = new JLabel(new ImageIcon("logo.png"));
        jLabel6 = new JLabel("Resume Builder");
        jLabel6.setFont(new Font("Showcard Gothic", Font.BOLD, 24));
        jLabel6.setForeground(Color.WHITE);
        jLabel7 = new JLabel("Copyright © Resume Builder All rights reserved");
        jLabel7.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
        jLabel7.setForeground(new Color(204, 204, 204));

        GroupLayout RightLayout = new GroupLayout(Right);
        Right.setLayout(RightLayout);
        RightLayout.setHorizontalGroup(
            RightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, RightLayout.createSequentialGroup()
                .addGap(0, 81, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(40, 40, 40))
            .addGroup(RightLayout.createSequentialGroup()
                .addGroup(RightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel6))
                    .addGroup(RightLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel5)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        RightLayout.setVerticalGroup(
            RightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(RightLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel5)
                .addGap(26, 26, 26)
                .addComponent(jLabel6)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(78, 78, 78))
        );

        jPanel1.add(Right);
        Right.setBounds(0, 0, 400, 500);  // Update bounds

        Left = new JPanel();
        Left.setBackground(new Color(255, 255, 255));
        Left.setMinimumSize(new Dimension(500, 500));  // Update dimensions

        jLabel1 = new JLabel("LOGIN");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36));
        jLabel1.setForeground(new Color(0, 102, 102));

        jLabel2 = new JLabel("Email");
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jTextField1 = new JTextField();
        jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField1.setForeground(new Color(102, 102, 102));

        jLabel3 = new JLabel("Password");
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jPasswordField1 = new JPasswordField();

        jButton1 = new JButton("Login");
        jButton1.setBackground(new Color(0, 102, 102));
        jButton1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jButton1.setForeground(Color.WHITE);
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4 = new JLabel("I don't have an account");
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 12));

        jButton2 = new JButton("Sign Up");
        jButton2.setBackground(new Color(0, 102, 102));
        jButton2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jButton2.setForeground(Color.WHITE);
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        GroupLayout LeftLayout = new GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
            LeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGroup(LeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jLabel1))
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(LeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1)
                            .addComponent(jLabel3)
                            .addComponent(jPasswordField1, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(LeftLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(LeftLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton2))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jPanel1.add(Left);
        Left.setBounds(400, 0, 400, 500);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 800, 500);

        pack();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        String email = jTextField1.getText();
        String password = String.valueOf(jPasswordField1.getPassword());

        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check credentials in database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Open Menu window
                        Menu menu = new Menu(userId);
                        menu.setSize(900, 650);  // Set the size of the Menu window
                        menu.setLocationRelativeTo(null);  // Center the Menu window
                        menu.setVisible(true);

                        // Close the Login window
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        SignUp signUp = new SignUp();
        signUp.setSize(800, 500);
        signUp.setLocationRelativeTo(null);
        signUp.setVisible(true);
        this.dispose();
    }


}
