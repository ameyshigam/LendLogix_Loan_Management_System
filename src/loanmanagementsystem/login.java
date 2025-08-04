package loanmanagementsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border; // Import for Border

public class login extends JFrame implements ActionListener {
    JButton login, clear, signup, fpassword;
    JTextField accno;
    JPasswordField pass;

    login() {
        // Frame setup
        setTitle("LENDLOGIX - Loan Management System"); // More descriptive title
        setLayout(null);
        setSize(700, 500); // Adjusted size for better layout
        setLocationRelativeTo(null); // Center the window
        getContentPane().setBackground(Color.decode("#F0F8FF")); // AliceBlue background

        // Define common fonts and borders for consistency
        Font titleFont = new Font("Impact", Font.ITALIC, 42); // Larger, more impactful font for main title
        Font sloganFont = new Font("Arial", Font.ITALIC, 24); // Slightly smaller for slogan
        Font labelFont = new Font("Segoe UI", Font.BOLD, 18); // Modern, bold font for labels
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16); // Modern, plain font for text fields
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15); // Modern, bold font for buttons

        Border fieldBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1); // Subtle border for fields

        // Logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/LL.jpg"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); // Smoother scaling
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(50, 10, 120, 120); // Adjusted position
        add(label);

        // Main Title
        JLabel text = new JLabel("LENDLOGIX");
        text.setFont(titleFont);
        text.setForeground(Color.decode("#1C395B")); // Dark blue color for title
        text.setBounds(200, 20, 500, 45); // Adjusted position
        add(text);

        // Slogan
        JLabel tex = new JLabel("A Loan Management System");
        tex.setFont(sloganFont);
        tex.setForeground(Color.decode("#36454F")); // Charcoal grey
        tex.setBounds(200, 70, 400, 30); // Adjusted position
        add(tex);

        // Account Number Label and Field
        JLabel text1 = new JLabel("ACCOUNT NUMBER:");
        text1.setFont(labelFont);
        text1.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray
        text1.setBounds(100, 160, 200, 30); // Adjusted position
        add(text1);

        accno = new JTextField();
        accno.setFont(fieldFont);
        accno.setBorder(fieldBorder);
        accno.setBounds(300, 160, 280, 35); // Adjusted size
        add(accno);

        // Password Label and Field
        JLabel text2 = new JLabel("PASSWORD:");
        text2.setFont(labelFont);
        text2.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray
        text2.setBounds(100, 210, 200, 30); // Adjusted position
        add(text2);

        pass = new JPasswordField();
        pass.setFont(fieldFont);
        pass.setBorder(fieldBorder);
        pass.setBounds(300, 210, 280, 35); // Adjusted size
        add(pass);

        // Buttons
        login = new JButton("SIGN IN");
        login.setFont(buttonFont);
        login.setBackground(Color.decode("#4682B4")); // SteelBlue
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false); // Removes the focus border
        // Hand cursor on hover
        login.setBounds(180, 290, 120, 38); // Adjusted size and position
        login.addActionListener(this);
        add(login);

        clear = new JButton("CLEAR");
        clear.setFont(buttonFont);
        clear.setBackground(Color.decode("#696969")); // DimGray
        clear.setForeground(Color.WHITE);
        clear.setFocusPainted(false);

        clear.setBounds(320, 290, 120, 38); // Adjusted size and position
        clear.addActionListener(this);
        add(clear);

        signup = new JButton("SIGN UP");
        signup.setFont(buttonFont);
        signup.setBackground(Color.decode("#228B22")); // ForestGreen
        signup.setForeground(Color.WHITE);
        signup.setFocusPainted(false);

        signup.setBounds(460, 290, 120, 38); // Adjusted size and position
        signup.addActionListener(this);
        add(signup);

        fpassword = new JButton("FORGOT PASSWORD?"); // Added question mark for clarity
        fpassword.setFont(buttonFont);
        fpassword.setBackground(Color.decode("#1E90FF")); // DodgerBlue
        fpassword.setForeground(Color.WHITE);
        fpassword.setFocusPainted(false);

        fpassword.setBounds(280, 350, 200, 38); // Adjusted size and position
        fpassword.addActionListener(this);
        add(fpassword);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure application closes properly on exit
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ac) {
        if (ac.getSource() == clear) {
            accno.setText("");
            pass.setText("");
        } else if (ac.getSource() == signup) {
            setVisible(false);
            new signup().setVisible(true);
        } else if (ac.getSource() == fpassword) {
            setVisible(false);
            new forgotpass().setVisible(true);
        } else if (ac.getSource() == login) {
            String acc = accno.getText();
            String pwrd = new String(pass.getPassword()); // Use getPassword() for JPasswordField
            Conn c = new Conn();
            String query = "select * from account where accno ='" + acc + "' and passwrd ='" + pwrd + "'";
            try {
                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    new Dashboard(acc).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect Account Number or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
                rs.close();
                c.c.close(); // Close connection
            } catch (SQLException e) {
                e.printStackTrace(); // Print full stack trace for debugging
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        // Run the UI on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> new login());
    }
}