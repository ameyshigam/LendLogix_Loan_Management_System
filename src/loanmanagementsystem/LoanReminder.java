package loanmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class LoanReminder {
    public static void main(String[] args) {
        checkLoanReminders();
    }

    public static void checkLoanReminders() {
        LocalDate today = LocalDate.now();
        
        String query = "SELECT ed.loan_no, cd.email " +
                       "FROM emiremain ed " +
                       "JOIN loandetails ld ON ed.loan_no = ld.loan_no " +
                       "JOIN account a ON ld.acc_no = a.accno " +
                       "JOIN cusdetails cd ON a.form1 = cd.frmno " +
                       "WHERE ed.remai_date = ?";
        
        try {
            Conn c = new Conn();
            PreparedStatement statement = c.c.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(today));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int loanId = resultSet.getInt("ed.loan_no");
                String emailId = resultSet.getString("cd.email");

                // Now fetch EMI for the current loan
                String emiQuery = "SELECT emi FROM emidetails WHERE loan_no = ?";
                PreparedStatement emiStatement = c.c.prepareStatement(emiQuery);
                emiStatement.setInt(1, loanId);
                ResultSet emiResultSet = emiStatement.executeQuery();

                double em = 0;
                if (emiResultSet.next()) {
                    em = emiResultSet.getDouble("emi");
                }

                // Send the reminder email
                sendLoanReminderEmail(emailId, loanId, em);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendLoanReminderEmail(String toEmail, int loanId, double em) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lendlogixloans@gmail.com", "*********"); // Replace with your password
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lendlogixloans@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Loan Reminder");
            message.setText("LAST THREE DAYS LEFT TO PAY YOUR LOAN INSTALLMENT OF THE MONTH OF AMOUNT " + em + ".\nYOUR LOAN ID: " + loanId);

            Transport.send(message);
            // System.out.println("Loan reminder email sent to " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
