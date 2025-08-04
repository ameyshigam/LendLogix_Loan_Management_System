package loanmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border; // Import for Border
import java.sql.*;

public class fpass2 extends JFrame implements ActionListener {

    JLabel text, pass3, pass1; // Removed text1, text2 as they are not used
    JPasswordField t1, t2;
    JButton b, c; // 'c' for Reset button
    String acc;

    fpass2(String acc) {
        this.acc = acc;

        // Frame setup
        setTitle("LendLogix - Change Your Password"); // More descriptive title
        setLayout(null); // Keeping null layout as per original
        setSize(700, 380); // Maintained size
        setLocationRelativeTo(null); // Center the window on the screen
        getContentPane().setBackground(Color.decode("#F0F8FF")); // AliceBlue background

        // Define common fonts and borders for consistency
        Font titleFont = new Font("Segoe UI", Font.BOLD, 36); // Larger, modern font for title
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16); // General label font
        Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 15); // Text field input font
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15); // Button text font

        Border fieldBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1); // Subtle border for text fields

        // Title Label
        text = new JLabel("CHANGE YOUR PASSWORD");
        text.setFont(titleFont);
        text.setForeground(Color.decode("#1C395B")); // Darker blue for title
        text.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        text.setBounds(0, 30, 700, 40); // Centered across the width
        add(text);

        // Set New Password Label and Field
        pass3 = new JLabel("Set New Password:");
        pass3.setFont(labelFont);
        pass3.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray
        pass3.setBounds(100, 110, 200, 30); // Adjusted for better spacing
        add(pass3);

        t1 = new JPasswordField();
        t1.setFont(textFieldFont);
        t1.setBorder(fieldBorder);
        t1.setBounds(320, 110, 280, 35); // Adjusted size
        add(t1);

        // Confirm New Password Label and Field
        pass1 = new JLabel("Confirm New Password:");
        pass1.setFont(labelFont);
        pass1.setForeground(Color.decode("#2F4F4F"));
        pass1.setBounds(100, 160, 250, 30); // Adjusted for better spacing
        add(pass1);

        t2 = new JPasswordField();
        t2.setFont(textFieldFont);
        t2.setBorder(fieldBorder);
        t2.setBounds(320, 160, 280, 35); // Adjusted size
        add(t2);

        // Buttons
        b = new JButton("SUBMIT");
        b.setFont(buttonFont);
        b.setBackground(Color.decode("#28A745")); // Vibrant Green
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false); // Remove focus border
        b.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        b.setBounds(380, 250, 120, 40); // Larger and distinct position
        b.addActionListener(this);
        add(b);

        c = new JButton("RESET"); // Changed text to RESET
        c.setFont(buttonFont);
        c.setBackground(Color.decode("#6C757D")); // Gray
        c.setForeground(Color.WHITE);
        c.setFocusPainted(false);
        c.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c.setBounds(240, 250, 120, 40); // Adjusted position
        add(c);
        c.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure graceful exit
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ac) {
        if (ac.getSource() == c) { // 'c' is the Reset button
            t1.setText("");
            t2.setText("");
        } else if (ac.getSource() == b) { // 'b' is the Submit button
            String newPassword = new String(t1.getPassword()).trim();
            String confirmPassword = new String(t2.getPassword()).trim();
            String currentAccount = acc; // Using the 'acc' field passed in constructor

            // Input Validation
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

            // Database Update
            Conn conn = new Conn();
            String query = "UPDATE account SET passwrd = ? WHERE accno = ?";
            try (PreparedStatement pstmt = conn.c.prepareStatement(query)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, currentAccount);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    // Assuming Dashboard is the next screen after changing password from within the app
                    new Dashboard(currentAccount).setVisible(true);
                } else {
                    // This case should ideally not happen if 'acc' is always valid when this frame is opened
                    JOptionPane.showMessageDialog(this, "Failed to update password. Account not found.", "Update Failed", JOptionPane.ERROR_MESSAGE);
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
        SwingUtilities.invokeLater(() -> new fpass2("")); // Pass a dummy account number for testing
    }
}