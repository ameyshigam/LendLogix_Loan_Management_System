package loanmanagementsystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border; // Import for Border

public class install extends JFrame implements ActionListener {

    JLabel loanno, titleLabel, loanAmountLabel, annualInterestRateLabel, loanTermLabel, numberOfMonthsLabel, resultLabel, totalPaymentLabel, limit, limit1, limit2, limit3;
    JTextField loanAmountField, annualInterestRateField, loanTermField, numberOfMonthsField;
    JButton calculateButton, resetButton, apply;
    float sa, loanl = 0;
    JComboBox<String> interestRateComboBox;
    String acc;
    String salar = null;
    String first;
    double loanAmount, annualInterestRate, monthlyInterestRate, monthlyPayment, totalPayment;
    int numberOfMonths, loanYears, loantaken = 0;

    public install(String acc) {
        this.acc = acc;

        // Frame Setup
        setLayout(null); // Keep null layout as per original
        setTitle("Loan Application - Loan Management System");
        setSize(700, 650); // Increased height slightly for better spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        getContentPane().setBackground(Color.decode("#F0F8FF")); // Light Blue background

        // Define a common font for labels and text fields
        Font labelFont = new Font("Segoe UI", Font.BOLD, 15); // Modern font, bold
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14); // Modern font, plain
        Font titleFont = new Font("Arial", Font.BOLD, 32); // Slightly larger for title
        Font loanNoFont = new Font("Raleway", Font.BOLD, 20); // Slightly larger for loan number
        Font resultFont = new Font("Segoe UI", Font.BOLD, 16); // For results

        // Border for text fields
        Border fieldBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);

        // Fetch salary and existing loan details
        try {
            Conn c = new Conn();
            String query = "SELECT salar FROM account WHERE accno = '" + acc + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                salar = rs.getString("salar");
            } else {
                JOptionPane.showMessageDialog(this, "No record found for account number: " + acc, "Data Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            rs.close();

            String que = "SELECT SUM(amount) FROM loandetails WHERE acc_no = '" + acc + "'";
            ResultSet rb = c.s.executeQuery(que);
            if (rb.next()) {
                loantaken = rb.getInt(1);
            }
            rb.close();
            c.c.close(); // Close connection after use
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while fetching account details: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Random ran = new Random();
        long first4 = (ran.nextLong() % 9000L) + 1000L;
        first = "2310" + Math.abs(first4);

        // Loan Number Label
        loanno = new JLabel("Loan NO. " + first);
        loanno.setFont(loanNoFont);
        loanno.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray
        loanno.setBounds(20, 15, 300, 30); // Adjusted position
        add(loanno);

        // Title Label
        titleLabel = new JLabel("APPLY FOR NEW LOAN");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.decode("#1C395B")); // Darker blue for title
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        titleLabel.setBounds(0, 50, 700, 40); // Centered across the width
        add(titleLabel);

        // Loan Limit Labels
        limit = new JLabel("Available Loan Limit (Annual):");
        limit.setFont(labelFont);
        limit.setForeground(Color.decode("#2F4F4F"));
        limit.setBounds(50, 110, 250, 30);
        add(limit);

        sa = Float.parseFloat(salar);
        loanl = (43.0f / 100) * sa - loantaken;
        limit1 = new JLabel(String.format("₹ %.2f", loanl)); // Format as currency
        limit1.setFont(labelFont);
        limit1.setForeground(Color.decode("#006400")); // Dark green for positive limit
        limit1.setBounds(300, 110, 200, 30);
        add(limit1);

        limit2 = new JLabel("Max. Monthly EMI Allowed:");
        limit2.setFont(labelFont);
        limit2.setForeground(Color.decode("#2F4F4F"));
        limit2.setBounds(50, 150, 250, 30);
        add(limit2);

        limit3 = new JLabel(String.format("₹ %.2f + Interest", (loanl / 12))); // Format as currency
        limit3.setFont(labelFont);
        limit3.setForeground(Color.decode("#006400"));
        limit3.setBounds(300, 150, 250, 30);
        add(limit3);

        // Loan Amount
        loanAmountLabel = new JLabel("Enter Loan Amount:");
        loanAmountLabel.setFont(labelFont);
        loanAmountLabel.setForeground(Color.decode("#2F4F4F"));
        loanAmountLabel.setBounds(50, 210, 200, 30);
        add(loanAmountLabel);

        loanAmountField = new JTextField();
        loanAmountField.setFont(fieldFont);
        loanAmountField.setBorder(fieldBorder);
        loanAmountField.setBounds(300, 210, 180, 30);
        add(loanAmountField);

        // Interest Rate (Bank Selection)
        JLabel annualInterestRateLabel = new JLabel("Select Bank for Interest Rate:");
        annualInterestRateLabel.setFont(labelFont);
        annualInterestRateLabel.setForeground(Color.decode("#2F4F4F"));
        annualInterestRateLabel.setBounds(50, 260, 230, 30);
        add(annualInterestRateLabel);

        String[] banks = {
                "STATE BANK OF INDIA - 10.5%",
                "ICICI BANK - 8.0%",
                "HDFC BANK - 7.5%",
                "BANK OF VADAVLI - 15.9%"
        };
        interestRateComboBox = new JComboBox<>(banks);
        interestRateComboBox.setFont(fieldFont);
        interestRateComboBox.setBackground(Color.WHITE);
        interestRateComboBox.setBounds(300, 260, 250, 30);
        add(interestRateComboBox);

        // Loan Term (Years)
        loanTermLabel = new JLabel("Enter Loan Term (in Years):");
        loanTermLabel.setFont(labelFont);
        loanTermLabel.setForeground(Color.decode("#2F4F4F"));
        loanTermLabel.setBounds(50, 310, 200, 30);
        add(loanTermLabel);

        loanTermField = new JTextField();
        loanTermField.setFont(fieldFont);
        loanTermField.setBorder(fieldBorder);
        loanTermField.setBounds(300, 310, 180, 30);
        add(loanTermField);

        // Number of Months for Installment Plan
        numberOfMonthsLabel = new JLabel("Choose Installment Plan (Months):");
        numberOfMonthsLabel.setFont(labelFont);
        numberOfMonthsLabel.setForeground(Color.decode("#2F4F4F"));
        numberOfMonthsLabel.setBounds(50, 360, 280, 30); // Adjusted position and width
        add(numberOfMonthsLabel);

        numberOfMonthsField = new JTextField();
        numberOfMonthsField.setFont(fieldFont);
        numberOfMonthsField.setBorder(fieldBorder);
        numberOfMonthsField.setBounds(300, 360, 180, 30);
        add(numberOfMonthsField);

        // Buttons
        calculateButton = new JButton("Calculate EMI");
        calculateButton.setFont(labelFont);
        calculateButton.setBackground(Color.decode("#4682B4")); // SteelBlue
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false); // Remove focus border
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        calculateButton.setBounds(100, 420, 150, 35); // Adjusted position and size
        calculateButton.addActionListener(this);
        add(calculateButton);

        resetButton = new JButton("Reset");
        resetButton.setFont(labelFont);
        resetButton.setBackground(Color.decode("#8B0000")); // Dark Red
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.setBounds(300, 420, 150, 35); // Adjusted position and size
        resetButton.addActionListener(this);
        add(resetButton);

        // Result Labels
        resultLabel = new JLabel("");
        resultLabel.setFont(resultFont);
        resultLabel.setForeground(Color.decode("#005C00")); // Dark green for result
        resultLabel.setBounds(50, 480, 600, 30); // Increased width
        add(resultLabel);

        totalPaymentLabel = new JLabel("");
        totalPaymentLabel.setFont(resultFont);
        totalPaymentLabel.setForeground(Color.decode("#005C00"));
        totalPaymentLabel.setBounds(50, 515, 600, 30); // Increased width
        add(totalPaymentLabel);

        // Apply Button
        apply = new JButton("Apply For Loan");
        apply.setFont(labelFont);
        apply.setBackground(Color.decode("#228B22")); // Forest Green
        apply.setForeground(Color.WHITE);
        apply.setFocusPainted(false);
        apply.setCursor(new Cursor(Cursor.HAND_CURSOR));
        apply.setBounds(200, 560, 180, 40); // Larger and centered
        add(apply);
        apply.addActionListener(this);
        apply.setVisible(false); // Remains hidden until calculation

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            // Validate inputs first
            try {
                loanAmount = Double.parseDouble(loanAmountField.getText());
                loanYears = Integer.parseInt(loanTermField.getText());
                numberOfMonths = Integer.parseInt(numberOfMonthsField.getText());

                if (loanAmount <= 0 || loanYears <= 0 || numberOfMonths <= 0) {
                    JOptionPane.showMessageDialog(this, "Loan amount, term, and months must be positive numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (numberOfMonths > (loanYears * 12)) {
                    JOptionPane.showMessageDialog(this, "Installment months cannot exceed total months in loan term.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (numberOfMonths == 0) { // Avoid division by zero
                    JOptionPane.showMessageDialog(this, "Number of months cannot be zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                String selectedBank = (String) interestRateComboBox.getSelectedItem();
                // Extract interest rate from the selected string
                annualInterestRate = Double.parseDouble(selectedBank.split("- ")[1].replace("%", "")) / 100;

                double totalLoanLimit = loanl * loanYears;

                if (loanAmount > totalLoanLimit) {
                    JOptionPane.showMessageDialog(this, String.format("Your loan amount (₹%.2f) exceeds the loan limit (₹%.2f) for the specified term.", loanAmount, totalLoanLimit), "Loan Limit Exceeded", JOptionPane.WARNING_MESSAGE);
                    apply.setVisible(false); // Hide apply button if limits are exceeded
                    return;
                }

                monthlyInterestRate = annualInterestRate / 12;

                if (monthlyInterestRate == 0) { // Handle 0 interest rate case
                     monthlyPayment = loanAmount / numberOfMonths;
                } else {
                    monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfMonths));
                }

                totalPayment = monthlyPayment * numberOfMonths;

                double maximumEMI = loanl / 12;
                if (monthlyPayment > maximumEMI) { // Changed condition to use calculated monthlyPayment
                    JOptionPane.showMessageDialog(this, String.format("Your calculated EMI (₹%.2f) exceeds the maximum allowed monthly EMI (₹%.2f).", monthlyPayment, maximumEMI), "EMI Limit Exceeded", JOptionPane.WARNING_MESSAGE);
                    apply.setVisible(false); // Hide apply button if limits are exceeded
                    return;
                }

                resultLabel.setText("Monthly Installment will be: " + String.format("₹ %.2f", monthlyPayment));
                totalPaymentLabel.setText("Total Payment over the period: " + String.format("₹ %.2f", totalPayment));
                apply.setVisible(true); // Show apply button only if calculations are successful and within limits

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numerical values for Loan Amount, Loan Term, and Installment Months.", "Input Error", JOptionPane.ERROR_MESSAGE);
                resultLabel.setText("");
                totalPaymentLabel.setText("");
                apply.setVisible(false); // Hide if there's an input error
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An unexpected error occurred during calculation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                resultLabel.setText("");
                totalPaymentLabel.setText("");
                apply.setVisible(false);
            }
        } else if (e.getSource() == resetButton) {
            loanAmountField.setText("");
            // annualInterestRateField.setText(""); // This field is no longer present as it's a ComboBox
            loanTermField.setText("");
            numberOfMonthsField.setText("");
            resultLabel.setText("");
            totalPaymentLabel.setText("");
            interestRateComboBox.setSelectedIndex(0); // Reset dropdown
            apply.setVisible(false); // Hide apply button on reset
        } else if (e.getSource() == apply) {
            // Re-validate inputs before applying, in case values were changed after calculate
            try {
                loanAmount = Double.parseDouble(loanAmountField.getText());
                loanYears = Integer.parseInt(loanTermField.getText());
                numberOfMonths = Integer.parseInt(numberOfMonthsField.getText());

                if (loanAmount <= 0 || loanYears <= 0 || numberOfMonths <= 0) {
                     JOptionPane.showMessageDialog(this, "Loan amount, term, and months must be positive numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String selectedBank = (String) interestRateComboBox.getSelectedItem();
                annualInterestRate = Double.parseDouble(selectedBank.split("- ")[1].replace("%", "")) / 100;
                monthlyInterestRate = annualInterestRate / 12;

                 if (monthlyInterestRate == 0) { // Handle 0 interest rate case
                     monthlyPayment = loanAmount / numberOfMonths;
                } else {
                    monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfMonths));
                }
                totalPayment = monthlyPayment * numberOfMonths;

                double totalLoanLimit = loanl * loanYears;
                 if (loanAmount > totalLoanLimit) {
                    JOptionPane.showMessageDialog(this, String.format("Loan amount (₹%.2f) exceeds available limit (₹%.2f). Please re-calculate.", loanAmount, totalLoanLimit), "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double maximumEMI = loanl / 12;
                if (monthlyPayment > maximumEMI) {
                    JOptionPane.showMessageDialog(this, String.format("Calculated EMI (₹%.2f) exceeds maximum allowed (₹%.2f). Please re-calculate.", monthlyPayment, maximumEMI), "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                // If all validations pass, proceed with applying the loan
                new applyforloan2(acc, first, loanAmount, monthlyPayment, totalPayment, numberOfMonths, loanYears);
                // Assuming forremi is also a backend process, it runs here.
                new forremi(acc, first, loanAmount, monthlyPayment, numberOfMonths, loanYears);

                JOptionPane.showMessageDialog(this, "Loan application successfully submitted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new Dashboard(acc).setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please calculate EMI first and ensure all fields are valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred during loan application submission: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        // Ensure Swing UI updates are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new install("565042191800"));
    }
}