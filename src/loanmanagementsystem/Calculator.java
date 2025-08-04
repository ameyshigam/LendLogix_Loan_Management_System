package loanmanagementsystem;

import javax.swing.*;
import javax.swing.border.Border; // Import for Border
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    JLabel titleLabel, loanAmountLabel, loanTermLabel, numberOfMonthsLabel, resultLabel, totalPaymentLabel;
    JTextField loanAmountField, loanTermField, numberOfMonthsField;
    JButton calculateButton, resetButton, backButton; // Renamed back to backButton for clarity
    String acc;
    JComboBox<String> interestRateComboBox;

    public Calculator(String acc) {
        this.acc = acc;
        setTitle("EMI Calculator - LendLogix"); // More descriptive title
        setSize(750, 550); // Increased size for better spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Keeping null layout as per original
        setLocationRelativeTo(null); // Center the window on the screen
        getContentPane().setBackground(Color.decode("#F0F8FF")); // AliceBlue background

        // Define common fonts and borders for consistency
        Font titleFont = new Font("Segoe UI", Font.BOLD, 36); // Larger, modern font for title
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16); // General label font
        Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 15); // Text field input font
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15); // Button text font
        Font resultFont = new Font("Segoe UI", Font.BOLD, 18); // For results

        Border fieldBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1); // Subtle border for text fields

        // Title Label
        titleLabel = new JLabel("EMI CALCULATOR");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.decode("#1C395B")); // Darker blue for title
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        titleLabel.setBounds(0, 30, 750, 40); // Centered across the width
        add(titleLabel);

        // Loan Amount
        loanAmountLabel = new JLabel("Enter Loan Amount (₹):");
        loanAmountLabel.setFont(labelFont);
        loanAmountLabel.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray
        loanAmountLabel.setBounds(80, 100, 250, 30);
        add(loanAmountLabel);

        loanAmountField = new JTextField();
        loanAmountField.setFont(textFieldFont);
        loanAmountField.setBorder(fieldBorder);
        loanAmountField.setBounds(350, 100, 250, 35); // Increased height
        add(loanAmountField);

        // Interest Rate (Bank Selection)
        JLabel annualInterestRateLabel = new JLabel("Select Bank for Interest Rate:");
        annualInterestRateLabel.setFont(labelFont);
        annualInterestRateLabel.setForeground(Color.decode("#2F4F4F"));
        annualInterestRateLabel.setBounds(80, 160, 250, 30);
        add(annualInterestRateLabel);

        String[] banks = {
                "STATE BANK OF INDIA - 10.5%",
                "ICICI BANK - 8.0%",
                "HDFC BANK - 7.5%",
                "BANK OF VADAVLI - 15.9%"
        };
        interestRateComboBox = new JComboBox<>(banks);
        interestRateComboBox.setFont(textFieldFont);
        interestRateComboBox.setBackground(Color.WHITE);
        interestRateComboBox.setBounds(350, 160, 250, 35); // Increased height
        add(interestRateComboBox);

        // Loan Term (Years)
        loanTermLabel = new JLabel("Enter Loan Term (in Years):");
        loanTermLabel.setFont(labelFont);
        loanTermLabel.setForeground(Color.decode("#2F4F4F"));
        loanTermLabel.setBounds(80, 220, 250, 30);
        add(loanTermLabel);

        loanTermField = new JTextField();
        loanTermField.setFont(textFieldFont);
        loanTermField.setBorder(fieldBorder);
        loanTermField.setBounds(350, 220, 250, 35); // Increased height
        add(loanTermField);

        // Number of Months for Installment Plan
        numberOfMonthsLabel = new JLabel("Choose Installment Plan (Months):");
        numberOfMonthsLabel.setFont(labelFont);
        numberOfMonthsLabel.setForeground(Color.decode("#2F4F4F"));
        numberOfMonthsLabel.setBounds(80, 280, 280, 30);
        add(numberOfMonthsLabel);

        numberOfMonthsField = new JTextField();
        numberOfMonthsField.setFont(textFieldFont);
        numberOfMonthsField.setBorder(fieldBorder);
        numberOfMonthsField.setBounds(350, 280, 250, 35); // Increased height
        add(numberOfMonthsField);

        // Buttons
        calculateButton = new JButton("Calculate EMI");
        calculateButton.setFont(buttonFont);
        calculateButton.setBackground(Color.decode("#28A745")); // Vibrant Green
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false); // Remove focus border
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        calculateButton.setBounds(100, 360, 150, 40); // Larger buttons
        calculateButton.addActionListener(this);
        add(calculateButton);

        resetButton = new JButton("Reset");
        resetButton.setFont(buttonFont);
        resetButton.setBackground(Color.decode("#6C757D")); // Gray
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.setBounds(300, 360, 150, 40);
        resetButton.addActionListener(this);
        add(resetButton);

        backButton = new JButton("BACK");
        backButton.setFont(buttonFont);
        backButton.setBackground(Color.decode("#FF8C00")); // Dark Orange
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBounds(500, 360, 150, 40);
        backButton.addActionListener(this);
        add(backButton);

        // Result Labels
        resultLabel = new JLabel("");
        resultLabel.setFont(resultFont);
        resultLabel.setForeground(Color.decode("#006400")); // Dark Green for results
        resultLabel.setBounds(80, 420, 600, 30); // Increased width
        add(resultLabel);

        totalPaymentLabel = new JLabel("");
        totalPaymentLabel.setFont(resultFont);
        totalPaymentLabel.setForeground(Color.decode("#006400"));
        totalPaymentLabel.setBounds(80, 460, 600, 30); // Increased width
        add(totalPaymentLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            try {
                double loanAmount = Double.parseDouble(loanAmountField.getText());
                int loanYears = Integer.parseInt(loanTermField.getText()); // Read loan term in years
                int numberOfMonths = Integer.parseInt(numberOfMonthsField.getText());

                // Basic input validation
                if (loanAmount <= 0 || loanYears <= 0 || numberOfMonths <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter positive numbers for all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    resultLabel.setText("");
                    totalPaymentLabel.setText("");
                    return;
                }
                if (numberOfMonths > (loanYears * 12)) {
                    JOptionPane.showMessageDialog(this, "Installment months cannot exceed total months in loan term (years * 12).", "Input Error", JOptionPane.ERROR_MESSAGE);
                    resultLabel.setText("");
                    totalPaymentLabel.setText("");
                    return;
                }

                // Get the selected bank and extract the interest rate
                String selectedBank = (String) interestRateComboBox.getSelectedItem();
                double annualInterestRate = Double.parseDouble(selectedBank.split("- ")[1].replace("%", "")) / 100;

                double monthlyInterestRate = annualInterestRate / 12;
                double monthlyPayment;

                // Handle zero interest rate to avoid division by zero or NaN issues
                if (monthlyInterestRate == 0) {
                    monthlyPayment = loanAmount / numberOfMonths;
                } else {
                    monthlyPayment = (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfMonths));
                }
                
                double totalPayment = monthlyPayment * numberOfMonths;

                resultLabel.setText("Monthly Installment will be: ₹ " + String.format("%.2f", monthlyPayment));
                totalPaymentLabel.setText("Total Payment over the period: ₹ " + String.format("%.2f", totalPayment));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numerical values for Loan Amount, Loan Term, and Installment Months.", "Input Error", JOptionPane.ERROR_MESSAGE);
                resultLabel.setText("");
                totalPaymentLabel.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // For debugging
            }
        } else if (e.getSource() == resetButton) {
            loanAmountField.setText("");
            loanTermField.setText(""); // Reset loan term field
            numberOfMonthsField.setText("");
            interestRateComboBox.setSelectedIndex(0); // Reset dropdown to first item
            resultLabel.setText("");
            totalPaymentLabel.setText("");
        } else if (e.getSource() == backButton) { // Using the renamed backButton
            setVisible(false);
            new Dashboard(acc).setVisible(true); // Assuming Dashboard is the class to return to
        }
    }

    public static void main(String[] args) {
        // Ensure Swing UI updates are done on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> new Calculator("")); // Pass an empty string or dummy acc for testing
    }
}