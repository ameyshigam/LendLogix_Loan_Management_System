package loanmanagementsystem;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Random;
import javax.swing.border.Border; // Import for Border

public class signup extends JFrame implements ActionListener {

    JLabel applfo, persdet, name, fname, dob, aage, gen, email, marsta, add, city, pin, state, aadhar, pan, sala;
    JTextField t1, t3, t4, t5, t6, t7, t8, t9, t10; // t2 removed as fname is not a JTextField
    JRadioButton r1, r2, r3, r4, r5;
    JButton b, c, d;
    JDateChooser dateChooser;
    ButtonGroup groupstatus, groupgender;

    Random ran = new Random();
    long first4 = (ran.nextLong() % 9000L) + 1000L;
    String first = "" + Math.abs(first4);

    signup() {

        setTitle("New Account Application Form"); // Clearer title
        setLayout(null);
        getContentPane().setBackground(Color.decode("#F0F0F0")); // Light grey background for a modern look

        // Define common fonts and borders
        Font titleFont = new Font("Arial", Font.BOLD, 40); // Larger, impactful title
        Font sectionTitleFont = new Font("Segoe UI", Font.BOLD, 26); // For "Personal Details"
        Font labelFont = new Font("Segoe UI", Font.BOLD, 18); // General label font
        Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 16); // Text field input font
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16); // Button text font

        Border fieldBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1); // Subtle border for text fields

        // Application Form No.
        applfo = new JLabel("APPLICATION FORM NO. " + first);
        applfo.setFont(titleFont);
        applfo.setForeground(Color.decode("#1C395B")); // Dark blue
        applfo.setHorizontalAlignment(SwingConstants.CENTER); // Center align
        applfo.setBounds(0, 05, 850, 45); // Centered across the width
        add(applfo);

        // Personal Details Section Title
        persdet = new JLabel("Personal Details");
        persdet.setFont(sectionTitleFont);
        persdet.setForeground(Color.decode("#36454F")); // Charcoal grey
        persdet.setHorizontalAlignment(SwingConstants.CENTER);
        persdet.setBounds(0, 45, 850, 30);
        add(persdet);

        // Full Name
        name = new JLabel("Full Name:");
        name.setFont(labelFont);
        name.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray
        name.setBounds(100, 100, 150, 30);
        add(name);
        t1 = new JTextField();
        t1.setFont(textFieldFont);
        t1.setBorder(fieldBorder);
        t1.setBounds(300, 100, 400, 35); // Increased height
        add(t1);

        // Date of Birth
        dob = new JLabel("Date of Birth:");
        dob.setFont(labelFont);
        dob.setForeground(Color.decode("#2F4F4F"));
        dob.setBounds(100, 150, 200, 30);
        add(dob);
        dateChooser = new JDateChooser();
        dateChooser.setBorder(fieldBorder); // Added border
        dateChooser.setForeground(Color.BLACK);
        dateChooser.setFont(textFieldFont);
        dateChooser.setBounds(300, 150, 400, 35); // Increased height
        add(dateChooser);

        // Age (Calculated)
        fname = new JLabel("Your Age:"); // Changed label to "Your Age"
        fname.setFont(labelFont);
        fname.setForeground(Color.decode("#2F4F4F"));
        fname.setBounds(100, 200, 200, 30);
        add(fname);
        aage = new JLabel("N/A"); // Default text
        aage.setFont(textFieldFont); // Use a consistent font
        aage.setForeground(Color.decode("#006400")); // Dark green for age value
        aage.setBounds(300, 200, 200, 30);
        add(aage);

        dateChooser.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                calculateAge();
            }
        });

        // Gender
        gen = new JLabel("Gender:");
        gen.setFont(labelFont);
        gen.setForeground(Color.decode("#2F4F4F"));
        gen.setBounds(100, 250, 200, 30);
        add(gen);
        r1 = new JRadioButton("Male");
        r1.setFont(textFieldFont);
        r1.setBackground(Color.decode("#F0F0F0")); // Match background
        r2 = new JRadioButton("Female");
        r2.setFont(textFieldFont);
        r2.setBackground(Color.decode("#F0F0F0"));
        r5 = new JRadioButton("Other");
        r5.setFont(textFieldFont);
        r5.setBackground(Color.decode("#F0F0F0"));

        groupgender = new ButtonGroup();
        groupgender.add(r1);
        groupgender.add(r2);
        groupgender.add(r5);

        r1.setBounds(300, 250, 80, 30);
        add(r1);
        r2.setBounds(400, 250, 100, 30);
        add(r2);
        r5.setBounds(520, 250, 100, 30);
        add(r5);

        // Email Address
        email = new JLabel("Email Address:");
        email.setFont(labelFont);
        email.setForeground(Color.decode("#2F4F4F"));
        email.setBounds(100, 300, 200, 30);
        add(email);
        t3 = new JTextField();
        t3.setFont(textFieldFont);
        t3.setBorder(fieldBorder);
        t3.setBounds(300, 300, 400, 35);
        add(t3);

        // Marital Status
        marsta = new JLabel("Marital Status:");
        marsta.setFont(labelFont);
        marsta.setForeground(Color.decode("#2F4F4F"));
        marsta.setBounds(100, 350, 200, 30);
        add(marsta);

        r3 = new JRadioButton("Married");
        r3.setFont(textFieldFont);
        r3.setBackground(Color.decode("#F0F0F0"));
        r4 = new JRadioButton("Unmarried");
        r4.setFont(textFieldFont);
        r4.setBackground(Color.decode("#F0F0F0"));

        groupstatus = new ButtonGroup();
        groupstatus.add(r3);
        groupstatus.add(r4);

        r3.setBounds(300, 350, 100, 30);
        add(r3);
        r4.setBounds(450, 350, 120, 30);
        add(r4);

        // Address
        add = new JLabel("Address:");
        add.setFont(labelFont);
        add.setForeground(Color.decode("#2F4F4F"));
        add.setBounds(100, 400, 200, 30);
        add(add);
        t4 = new JTextField();
        t4.setFont(textFieldFont);
        t4.setBorder(fieldBorder);
        t4.setBounds(300, 400, 400, 35);
        add(t4);

        // City
        city = new JLabel("City:");
        city.setFont(labelFont);
        city.setForeground(Color.decode("#2F4F4F"));
        city.setBounds(100, 450, 200, 30);
        add(city);
        t5 = new JTextField();
        t5.setFont(textFieldFont);
        t5.setBorder(fieldBorder);
        t5.setBounds(300, 450, 400, 35);
        add(t5);

        // Pin Code
        pin = new JLabel("Pin Code:");
        pin.setFont(labelFont);
        pin.setForeground(Color.decode("#2F4F4F"));
        pin.setBounds(100, 500, 200, 30);
        add(pin);
        t6 = new JTextField();
        t6.setFont(textFieldFont);
        t6.setBorder(fieldBorder);
        t6.setBounds(300, 500, 400, 35);
        add(t6);

        // State
        state = new JLabel("State:");
        state.setFont(labelFont);
        state.setForeground(Color.decode("#2F4F4F"));
        state.setBounds(100, 550, 200, 30);
        add(state);
        t7 = new JTextField();
        t7.setFont(textFieldFont);
        t7.setBorder(fieldBorder);
        t7.setBounds(300, 550, 400, 35);
        add(t7);

        // AADHAR NO.
        aadhar = new JLabel("AADHAR NO.:");
        aadhar.setFont(labelFont);
        aadhar.setForeground(Color.decode("#2F4F4F"));
        aadhar.setBounds(100, 600, 200, 30);
        add(aadhar);
        t8 = new JTextField();
        t8.setFont(textFieldFont);
        t8.setBorder(fieldBorder);
        t8.setBounds(300, 600, 400, 35);
        add(t8);

        // PAN CARD NO.
        pan = new JLabel("PAN CARD NO.:");
        pan.setFont(labelFont);
        pan.setForeground(Color.decode("#2F4F4F"));
        pan.setBounds(100, 650, 200, 30);
        add(pan);
        t9 = new JTextField();
        t9.setFont(textFieldFont);
        t9.setBorder(fieldBorder);
        t9.setBounds(300, 650, 400, 35);
        add(t9);

        // SALARY (Per annum)
        sala = new JLabel("SALARY (Per annum):");
        sala.setFont(labelFont);
        sala.setForeground(Color.decode("#2F4F4F"));
        sala.setBounds(100, 700, 200, 30);
        add(sala);
        t10 = new JTextField();
        t10.setFont(textFieldFont);
        t10.setBorder(fieldBorder);
        t10.setBounds(300, 700, 400, 35);
        add(t10);

        // Buttons
        b = new JButton("SUBMIT");
        b.setFont(buttonFont);
        b.setBackground(Color.decode("#228B22")); // Forest Green
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBounds(580, 742, 120, 40); // Larger and distinct position
        b.addActionListener(this);
        add(b);

        c = new JButton("CANCEL");
        c.setFont(buttonFont);
        c.setBackground(Color.decode("#FF8C00")); // Dark Orange
        c.setForeground(Color.WHITE);
        c.setFocusPainted(false);
        c.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c.setBounds(400, 742, 120, 40); // Larger and distinct position
        c.addActionListener(this);
        add(c);

        d = new JButton("PREVIOUS");
        d.setFont(buttonFont);
        d.setBackground(Color.decode("#4682B4")); // SteelBlue
        d.setForeground(Color.WHITE);
        d.setFocusPainted(false);
        d.setCursor(new Cursor(Cursor.HAND_CURSOR));
        d.setBounds(220, 742, 120, 40); // Larger and distinct position
        d.addActionListener(this);
        add(d);

        setSize(850, 900); // Increased height for better spacing
        setLocation(350, 0); // Adjusted location for better centering on larger screens
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure graceful exit
        setVisible(true);
    }

    private void calculateAge() {
        Calendar birthDate = dateChooser.getCalendar();
        if (birthDate != null) {
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                age--; // If today is before the birthday this year
            }
            aage.setText(String.valueOf(age));
        } else {
            aage.setText("N/A"); // Reset if date is cleared
        }
    }

    public void actionPerformed(ActionEvent ac) {

        if (ac.getSource() == c) {
            // "CANCEL" button: just close this window (assuming it returns to previous screen)
            setVisible(false);
        } else if (ac.getSource() == d) {
            // "PREVIOUS" button: go back to login screen
            setVisible(false);
            new login().setVisible(true); // Assuming 'login' is the previous screen
        } else if (ac.getSource() == b) { // "SUBMIT" button
            // --- Input Validation ---
            String name = t1.getText().trim();
            String email = t3.getText().trim();
            String addr = t4.getText().trim();
            String city = t5.getText().trim();
            String pin = t6.getText().trim();
            String sta = t7.getText().trim();
            String aad = t8.getText().trim();
            String pan = t9.getText().trim();
            String sal = t10.getText().trim();

            String gen = "";
            if (r1.isSelected()) {
                gen = "Male";
            } else if (r2.isSelected()) {
                gen = "Female";
            } else if (r5.isSelected()) {
                gen = "Other";
            }

            String marst = "";
            if (r3.isSelected()) {
                marst = "Married";
            } else if (r4.isSelected()) {
                marst = "Unmarried";
            }

            String dob = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText(); // Gets formatted date string

            // Basic Empty Field Validation
            if (name.isEmpty() || email.isEmpty() || addr.isEmpty() || city.isEmpty() || pin.isEmpty() || sta.isEmpty() ||
                aad.isEmpty() || pan.isEmpty() || sal.isEmpty() || gen.isEmpty() || marst.isEmpty() || dob.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all the required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop processing
            }

            // Numerical/Format Validation
            int ag;
            try {
                ag = Integer.parseInt(aage.getText()); // Use the calculated age
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please select your Date of Birth to calculate age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (ag < 21) {
                JOptionPane.showMessageDialog(this, "Sorry, you must be at least 21 years old to apply.", "Eligibility Criteria", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (aad.length() != 12 || !aad.matches("\\d+")) { // Check for exactly 12 digits
                JOptionPane.showMessageDialog(this, "Aadhar Number should be exactly 12 digits long and contain only numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (pan.length() != 10 || !pan.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) { // Basic PAN format check
                 JOptionPane.showMessageDialog(this, "Please enter a valid 10-character PAN Card Number (e.g., ABCDE1234F).", "Input Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            try {
                double salaryValue = Double.parseDouble(sal); // Validate salary is a number
                if (salaryValue <= 0) {
                    JOptionPane.showMessageDialog(this, "Salary must be a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for Salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- OTP Verification and Database Insertion ---
            final String finalGen = gen; // Need final for inner class
            final String finalMar = marst;
            final String finalDob = dob;
            final String finalSal = sal;
            final int finalAge = ag;

            // Show OTP Verification GUI
            OTPVerificationGUI.createAndShowGUI(email, new OTPVerificationCallback() {
                @Override
                public void onOTPVerified(boolean success) {
                    if (success) {
                        try {
                            Conn c = new Conn();
                            String query = "INSERT INTO cusdetails (form_no, name, dob, gen, email, marst, addr, pin, city, sta, aad, pan, sala) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement pstmt = c.c.prepareStatement(query);
                            pstmt.setString(1, first); // frmno
                            pstmt.setString(2, name);
                            pstmt.setString(3, finalDob);
                            pstmt.setString(4, finalGen);
                            pstmt.setString(5, email);
                            pstmt.setString(6, finalMar);
                            pstmt.setString(7, addr);
                            pstmt.setString(8, pin);
                            pstmt.setString(9, city);
                            pstmt.setString(10, sta);
                            pstmt.setString(11, aad);
                            pstmt.setString(12, pan);
                            pstmt.setString(13, finalSal);

                            int rowsAffected = pstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(null, "Account created successfully! Please set your password now.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                setVisible(false);
                                new password(first, aad, finalSal).setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to create account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            pstmt.close();
                            c.c.close(); // Close connection
                        } catch (SQLIntegrityConstraintViolationException e) {
                            JOptionPane.showMessageDialog(null, "Error: Aadhar number or PAN number might already be in use.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                            System.out.println("SQL Integrity Error: " + e.getMessage());
                        } catch (SQLException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "OTP verification failed. Please try again.", "Verification Failed", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new signup());
    }
}