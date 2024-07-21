package resume.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Resume1 extends JFrame {
    private JButton createPDFButton;
    private JTextField textField;
    private int userId; // Assuming userId needs to be passed or retrieved

    public Resume1(int userId) {
        this.userId = userId;
        createPDFButton = new JButton("Create PDF");
        textField = new JTextField(20);

        setLayout(new FlowLayout());
        add(new JLabel("Enter text for PDF:"));
        add(textField);
        add(createPDFButton);

        createPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                createPDF(text);
            }
        });

        setTitle("PDF Creator");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createPDF(String text) {
        String dest = "example.pdf";
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
            document.add(new Paragraph("User ID: " + userId)); // Example of including userId
            document.add(new Paragraph(text));
            document.close();
            JOptionPane.showMessageDialog(this, "PDF created successfully.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating PDF: " + e.getMessage());
        }
    }
}
