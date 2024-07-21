package resume.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

public class Finalize extends JPanel {
    private JButton downloadButton;
    private PDFExporter pdfExporter;
    private int userId; // Add userId field

    public Finalize(int userId) { // Add userId parameter to constructor
        this.userId = userId; // Initialize userId
        setLayout(new BorderLayout());
        pdfExporter = new PDFExporter();

        // Create a title label
        JLabel titleLabel = new JLabel("Finalize Your Resume");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Create a content panel with instructions and a button
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel instructionsLabel = new JLabel("<html><p style='text-align:center;'>Review your information and click 'Download Resume' to generate your resume as a PDF.</p></html>");
        instructionsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(instructionsLabel);

        // Add some vertical space
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        downloadButton = new JButton("Download Resume");
        downloadButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        downloadButton.setBackground(new Color(0, 102, 102));
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setFocusPainted(false);
        downloadButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        downloadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateResumePDF();
            }
        });

        contentPanel.add(downloadButton);
        add(contentPanel, BorderLayout.CENTER);

        // Create a footer panel with additional information
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footerPanel.setBackground(new Color(240, 240, 240));

        JLabel footerLabel = new JLabel("Ensure all your information is correct before downloading.");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        footerLabel.setHorizontalAlignment(JLabel.CENTER);
        footerPanel.add(footerLabel, BorderLayout.CENTER);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void generateResumePDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("resume.pdf"));
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                // Establish database connection
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resumebuilder", "root", "");
                pdfExporter.setConnection(conn);

                // Generate PDF using the userId
                pdfExporter.generatePDF(userId, file.getAbsolutePath());

                JOptionPane.showMessageDialog(this, "Resume PDF generated successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error generating PDF: " + ex.getMessage());
            }
        }
    }
}
