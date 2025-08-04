package loanmanagementsystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface OTPVerificationCallback {
    void onOTPVerified(boolean success);
}

public class OTPVerificationGUI {
    static String emai;

    public static void createAndShowGUI(String email, OTPVerificationCallback callback) {
        emai = email;

        // --- Frame Setup ---
        JFrame frame = new JFrame("Email Verification - LendLogix"); // More descriptive title
        frame.setSize(450, 250); // Adjusted size for better layout
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setResizable(false); // Prevent resizing
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame

        // --- Define Common Styles ---
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Border fieldBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        Color backgroundColor = Color.decode("#F0F8FF"); // AliceBlue
        Color textColor = Color.decode("#2F4F4F"); // DarkSlateGray

        frame.getContentPane().setBackground(backgroundColor); // Set background for content pane

        // --- Panel for Email Display ---
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        emailPanel.setBackground(backgroundColor);
        emailPanel.setBorder(new EmptyBorder(10, 10, 0, 10)); // Padding

        JLabel emailLabel = new JLabel("Sending OTP to:");
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(textColor);
        emailPanel.add(emailLabel);

        JLabel emailField = new JLabel(emai);
        emailField.setFont(textFieldFont.deriveFont(Font.ITALIC)); // Italic and slightly different color
        emailField.setForeground(Color.decode("#005C00")); // Dark green
        emailPanel.add(emailField);

        // --- Panel for OTP Actions (Send/Verify Buttons) ---
        JPanel otpActionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Centered buttons
        otpActionPanel.setBackground(backgroundColor);
        otpActionPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        JButton sendOTPButton = new JButton("Send OTP");
        sendOTPButton.setFont(buttonFont);
        sendOTPButton.setBackground(Color.decode("#28A745")); // Vibrant Green
        sendOTPButton.setForeground(Color.WHITE);
        sendOTPButton.setFocusPainted(false);
        sendOTPButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        otpActionPanel.add(sendOTPButton);

        JButton verifyOTPButton = new JButton("Verify OTP");
        verifyOTPButton.setFont(buttonFont);
        verifyOTPButton.setBackground(Color.decode("#4682B4")); // SteelBlue
        verifyOTPButton.setForeground(Color.WHITE);
        verifyOTPButton.setFocusPainted(false);
        verifyOTPButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        otpActionPanel.add(verifyOTPButton);

        // --- Panel for OTP Input ---
        JPanel otpInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        otpInputPanel.setBackground(backgroundColor);
        otpInputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel otpLabel = new JLabel("Enter OTP:");
        otpLabel.setFont(labelFont);
        otpLabel.setForeground(textColor);
        otpInputPanel.add(otpLabel);

        JTextField otpField = new JTextField(20); // Set preferred column width
        otpField.setFont(textFieldFont);
        otpField.setBorder(fieldBorder);
        otpField.setPreferredSize(new Dimension(300, 30)); // Set fixed size for consistency
        otpInputPanel.add(otpField);

        // --- Add Panels to Frame using BorderLayout ---
        frame.add(emailPanel, BorderLayout.NORTH);
        frame.add(otpActionPanel, BorderLayout.CENTER); // Buttons in the center area
        frame.add(otpInputPanel, BorderLayout.SOUTH); // OTP input at the bottom

        // --- Action Listeners ---
        sendOTPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Run in a new thread to prevent UI freeze during email sending
                new Thread(() -> {
                    sendOTPButton.setEnabled(false); // Disable button to prevent multiple clicks
                    verifyOTPButton.setEnabled(false);
                    otpField.setText(""); // Clear OTP field
                    JOptionPane.showMessageDialog(frame, "Sending OTP. Please wait...", "Sending", JOptionPane.INFORMATION_MESSAGE);
                    
                    String userEmail = emai;
                    String otp = OTP.generateOTP(); // Generate the OTP
                    OTP.sendOTPEmail(userEmail, otp); // Send the OTP to the user's email

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(frame, "OTP has been sent to " + userEmail, "OTP Sent", JOptionPane.INFORMATION_MESSAGE);
                        sendOTPButton.setEnabled(true); // Re-enable button
                        verifyOTPButton.setEnabled(true);
                    });
                }).start();
            }
        });

        verifyOTPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userEnteredOTP = otpField.getText().trim(); // Trim whitespace
                if (userEnteredOTP.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter the OTP.", "Input Required", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (OTP.verifyOTP(userEnteredOTP)) {
                    JOptionPane.showMessageDialog(frame, "OTP verified successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    callback.onOTPVerified(true);  // Trigger the callback on success
                    frame.dispose(); // Close the OTP frame
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid OTP. Please try again.", "Verification Failed", JOptionPane.ERROR_MESSAGE);
                    callback.onOTPVerified(false); // Trigger the callback on failure
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}