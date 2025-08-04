package loanmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border; // Import for Border

public class forgotpass extends JFrame implements ActionListener {

    JLabel text, text1, text2, pass3, pass1;
    JTextField accno, aad;
    JPasswordField t1, t2;
    JButton b, c; // 'c' for Reset button

    forgotpass() {

        // Frame setup
        setTitle("LendLogix - Reset Your Password"); // More descriptive title
        setLayout(null); // Keeping null layout as per original
        setSize(700, 450); // Adjusted size for better layout
        setLocationRelativeTo(null); // Center the window on the screen
        getContentPane().setBackground(Color.decode("#F0F8FF")); // AliceBlue background

        // Define common fonts and borders for consistency
        Font titleFont = new Font("Segoe UI", Font.BOLD, 36); // Larger, modern font for title
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16); // General label font
        Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 15); // Text field input font
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15); // Button text font

        Border fieldBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1); // Subtle border for text fields


        // Title Label
        text = new JLabel("RESET YOUR PASSWORD");
        text.setFont(titleFont);
        text.setForeground(Color.decode("#1C395B")); // Darker blue for title
        text.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        text.setBounds(0, 30, 700, 40); // Centered across the width
        add(text);

        // Account Number Label and Field
        text1 = new JLabel("Account Number:");
        text1.setFont(labelFont);
        text1.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray
        text1.setBounds(100, 100, 200, 30);
        add(text1);

        accno = new JTextField();
        accno.setFont(textFieldFont);
        accno.setBorder(fieldBorder);
        accno.setBounds(320, 100, 280, 35); // Adjusted size
        add(accno);

        // Aadhar Card Number Label and Field
        text2 = new JLabel("Aadhar Card Number:");
        text2.setFont(labelFont);
        text2.setForeground(Color.decode("#2F4F4F"));
        text2.setBounds(100, 150, 200, 30);
        add(text2);

        aad = new JTextField();
        aad.setFont(textFieldFont);
        aad.setBorder(fieldBorder);
        aad.setBounds(320, 150, 280, 35); // Adjusted size
        add(aad);

        // Set Your Password Label and Field
        pass3 = new JLabel("Set New Password:"); // Clearer label
        pass3.setFont(labelFont);
        pass3.setForeground(Color.decode("#2F4F4F"));
        pass3.setBounds(100, 210, 250, 30);
        add(pass3);

        t1 = new JPasswordField();
        t1.setFont(textFieldFont);
        t1.setBorder(fieldBorder);
        t1.setBounds(320, 210, 280, 35); // Adjusted size
        add(t1);

        // Confirm New Password Label and Field
        pass1 = new JLabel("Confirm Password:"); // Clearer label
        pass1.setFont(labelFont);
        pass1.setForeground(Color.decode("#2F4F4F"));
        pass1.setBounds(100, 260, 250, 30);
        add(pass1);

        t2 = new JPasswordField();
        t2.setFont(textFieldFont);
        t2.setBorder(fieldBorder);
        t2.setBounds(320, 260, 280, 35); // Adjusted size
        add(t2);


        // Buttons
        b = new JButton("SUBMIT");
        b.setFont(buttonFont);
        b.setBackground(Color.decode("#28A745")); // Vibrant Green
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false); // Remove focus border
        b.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        b.setBounds(380, 340, 120, 40); // Larger and distinct position
        b.addActionListener(this);
        add(b);

        c = new JButton("RESET"); // Changed text to RESET
        c.setFont(buttonFont);
        c.setBackground(Color.decode("#6C757D")); // Gray
        c.setForeground(Color.WHITE);
        c.setFocusPainted(false);
        c.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c.setBounds(240, 340, 120, 40); // Adjusted position
        add(c);
        c.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure graceful exit
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ac) {
        if (ac.getSource() == c) { // 'c' is the Reset button
            accno.setText("");
            t1.setText("");
            t2.setText("");
            aad.setText("");
        } else if (ac.getSource() == b) { // 'b' is the Submit button
            String accountNum = accno.getText().trim();
            String aadharNum = aad.getText().trim();
            String newPassword = new String(t1.getPassword()).trim(); // Use getPassword()
            String confirmPassword = new String(t2.getPassword()).trim(); // Use getPassword()

            // Input Validation
            if (accountNum.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Account Number cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (aadharNum.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aadhar Card Number cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "New Password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Confirm Password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (newPassword.length() < 8) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.", "Password Policy", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "New Password and Confirm Password do not match.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (aadharNum.length() != 12 || !aadharNum.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Aadhar Number should be 12 digits and contain only numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Database Update
            Conn conn = new Conn();
            String query = "UPDATE account SET passwrd = ? WHERE accno = ? AND aad = ?";
            try (PreparedStatement pstmt = conn.c.prepareStatement(query)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, accountNum);
                pstmt.setString(3, aadharNum);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    new login().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Account Number or Aadhaar Card Number is incorrect. Please check your details.", "Update Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Print stack trace for debugging
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (conn.c != null) {
                        conn.c.close(); // Close the connection
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // Ensure UI updates are done on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> new forgotpass());
    }
}