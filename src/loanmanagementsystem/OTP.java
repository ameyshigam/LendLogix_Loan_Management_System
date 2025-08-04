package loanmanagementsystem;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class OTP {

    private static String generatedOTP;
    private static boolean verified = false;

        public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); 
        generatedOTP = String.valueOf(otp);
        return generatedOTP;
    }
    
    
    public static void sendOTPEmail(String recipientEmail, String otp) {
    	
    	
    	
        final String senderEmail = "lendlogixloans@gmail.com"; 
        final String senderPassword = "kxwa rezx jkvj wgod"; 

        // Setup properties for SMTP (using Gmail as an example)
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

       
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
          
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your OTP for Email Verification");
            message.setText("Your One-Time Password (OTP) is: " + otp);

            // Send email
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    
    public static void generateAndSendOTP(String recipientEmail) {
        generatedOTP = generateOTP(); // Generate the OTP
        sendOTPEmail(recipientEmail, generatedOTP); // Send OTP via email
    }

   
    public static boolean verifyOTP(String userEnteredOTP) {
        verified = generatedOTP.equals(userEnteredOTP); 
        return verified;
    }

    // Method to check if OTP has been successfully verified
    public static boolean isVerified() {
        return verified; // Return verification status
    }
}