package resume.com;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PDFExporter {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void generatePDF(int userId, String filePath) throws Exception {
        if (connection == null) {
            throw new Exception("Connection is null. Set the connection first using setConnection method.");
        }

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        writer.setPageEvent(new Footer()); // Set custom footer
        writer.setPageEvent(new Background()); // Set custom background

        document.open();

        // Define columns for the document
        float[] columnWidths = {30, 70}; // 30% for left section, 70% for right section
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        // Left section (gray background with picture and personal info)
        PdfPCell leftCell = new PdfPCell();
        leftCell.setPadding(10);
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        // Add photo
        Image photo = fetchPhoto(userId);
        if (photo != null) {
            photo.scaleToFit(150, 150);
            leftCell.addElement(photo);
        }

        // Fetch personal info
        Paragraph personalInfoParagraph = fetchPersonalInfo(userId);
        if (personalInfoParagraph != null) {
            leftCell.addElement(personalInfoParagraph);
        }

        table.addCell(leftCell);

        // Right section (education, skills, work history)
        PdfPCell rightCell = new PdfPCell();
        rightCell.setPadding(10);
        rightCell.setBorder(Rectangle.NO_BORDER);

        // Add Education section
        Paragraph educationParagraph = fetchEducation(userId);
        if (educationParagraph != null) {
            rightCell.addElement(educationParagraph);
        }

        // Add Skills section
        Paragraph skillsParagraph = fetchSkills(userId);
        if (skillsParagraph != null) {
            rightCell.addElement(skillsParagraph);
        }

        // Add Work History section
        Paragraph workHistoryParagraph = fetchWorkHistory(userId);
        if (workHistoryParagraph != null) {
            rightCell.addElement(workHistoryParagraph);
        }

        table.addCell(rightCell);

        // Add the table to the document
        document.add(table);

        document.close();
    }

    private Paragraph fetchPersonalInfo(int userId) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM personalinfo WHERE user_id = " + userId);
            if (rs.next()) {
                Paragraph paragraph = new Paragraph();
                paragraph.add(new Paragraph("Personal Information", new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD)));
                paragraph.add(new Paragraph("Name: " + rs.getString("name")));
                paragraph.add(new Paragraph("Date of Birth: " + rs.getString("date_of_birth")));
                paragraph.add(new Paragraph("Father's Name: " + rs.getString("father_name")));
                paragraph.add(new Paragraph("Nationality: " + rs.getString("nationality")));
                paragraph.add(new Paragraph("Phone Number: " + rs.getString("phone_number")));
                paragraph.add(new Paragraph("Email: " + rs.getString("email_id")));

                return paragraph;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Image fetchPhoto(int userId) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT photo FROM personalinfo WHERE user_id = " + userId);
            if (rs.next()) {
                byte[] photoBytes = rs.getBytes("photo");
                if (photoBytes != null && photoBytes.length > 0) {
                    return Image.getInstance(photoBytes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Paragraph fetchEducation(int userId) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM education WHERE user_id = " + userId);

            Paragraph paragraph = new Paragraph("Education", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
            while (rs.next()) {
                paragraph.add(new Paragraph(rs.getString("degree") + " in " + rs.getString("field_of_study") +
                        " from " + rs.getString("institute_name") + " (" + rs.getString("time_period") + ")"));
            }
            return paragraph;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Paragraph fetchSkills(int userId) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM skills WHERE user_id = " + userId);

            Paragraph paragraph = new Paragraph("Skills", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
            while (rs.next()) {
                paragraph.add(new Paragraph("\u2022 " + rs.getString("skill_name") + " - " + rs.getString("description")));
            }
            return paragraph;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Paragraph fetchWorkHistory(int userId) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM work_history WHERE user_id = " + userId);

            Paragraph paragraph = new Paragraph("Work History", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
            while (rs.next()) {
                paragraph.add(new Paragraph(rs.getString("job_title") + " at " + rs.getString("company") +
                        " (" + formatDate(rs.getString("start_date")) + " to " + formatDate(rs.getString("end_date")) + ")"));
            }
            return paragraph;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String formatDate(String dateStr) {
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("MMM yyyy");
            return outputFormat.format(inputFormat.parse(dateStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // Custom footer
    class Footer extends PdfPageEventHelper {
        Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase footer = new Phrase("Page " + writer.getPageNumber(), footerFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }
    }

    // Custom background
    class Background extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            // Calculate width of left section
            float leftWidth = (document.right() - document.left()) * 30 / 100;
            Rectangle rect = new Rectangle(document.left(), document.bottom(), document.left() + leftWidth, document.top());
            rect.setBackgroundColor(BaseColor.LIGHT_GRAY);
            canvas.rectangle(rect);
        }
    }
}
