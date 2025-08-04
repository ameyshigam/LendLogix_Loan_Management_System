package loanmanagementsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder; // Import for EmptyBorder
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader; // Import for JTableHeader
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;

public class loanhis extends JFrame {
    private JButton fetchButton;
    private JButton generateStatementButton, backButton; // Renamed 'back' to 'backButton' for clarity
    private JTable emiTable;
    private DefaultTableModel tableModel;
    private String acc;
    private JLabel accountNoDisplayLabel; // New JLabel to display account number

    public loanhis(String acc) {
        this.acc = acc;
        setTitle("Loan History - LendLogix"); // More descriptive title
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Added gaps for better spacing
        setSize(950, 550); // Increased size for better table visibility
        setLocationRelativeTo(null); // Center the window on the screen
        getContentPane().setBackground(Color.decode("#F0F8FF")); // AliceBlue background

        // Define common font for improved UI
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Font tableHeaderFont = new Font("Segoe UI", Font.BOLD, 14);
        Font tableCellFont = new Font("Segoe UI", Font.PLAIN, 13);


        // --- Top Panel for Controls ---
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15)); // Increased horizontal/vertical gap
        controlPanel.setBackground(Color.decode("#E0FFFF")); // LightCyan for control panel
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Account Number Display
        controlPanel.add(new JLabel("Account No:"));
        accountNoDisplayLabel = new JLabel(acc);
        accountNoDisplayLabel.setFont(labelFont);
        accountNoDisplayLabel.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray
        controlPanel.add(accountNoDisplayLabel);

        // Fetch Button
        fetchButton = new JButton("Fetch Loan History");
        fetchButton.setFont(buttonFont);
        fetchButton.setBackground(Color.decode("#228B22")); // ForestGreen
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFocusPainted(false);
        fetchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchLoanHistory();
            }
        });
        controlPanel.add(fetchButton);

        // Generate Statement Button
        generateStatementButton = new JButton("Generate Statement (PDF)");
        generateStatementButton.setFont(buttonFont);
        generateStatementButton.setBackground(Color.decode("#4682B4")); // SteelBlue
        generateStatementButton.setForeground(Color.WHITE);
        generateStatementButton.setFocusPainted(false);
        generateStatementButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        generateStatementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateStatement();
            }
        });
        controlPanel.add(generateStatementButton);

        // Back Button
        backButton = new JButton("BACK TO DASHBOARD"); // Renamed
        backButton.setFont(buttonFont);
        backButton.setBackground(Color.decode("#FF8C00")); // DarkOrange
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Dashboard(acc).setVisible(true); // Ensure Dashboard is made visible
            }
        });
        controlPanel.add(backButton);

        add(controlPanel, BorderLayout.NORTH);

        // --- Table for Loan History ---
        String[] columnNames = { "Loan No.", "Amount", "EMI", "Amount Paid", "Amount Left", "Applied Date", "Last EMI Date", "Next Due Date" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        emiTable = new JTable(tableModel);
        emiTable.setFont(tableCellFont);
        emiTable.setRowHeight(25); // Increase row height for readability
        emiTable.setFillsViewportHeight(true); // Make table fill the scroll pane height
        emiTable.setBackground(Color.WHITE);
        emiTable.setSelectionBackground(Color.decode("#ADD8E6")); // LightBlue selection

        // Table Header Styling
        JTableHeader header = emiTable.getTableHeader();
        header.setFont(tableHeaderFont);
        header.setBackground(Color.decode("#B0C4DE")); // LightSteelBlue for header background
        header.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray for header text
        header.setPreferredSize(new Dimension(header.getWidth(), 30)); // Set header height

        // Scroll Pane for the table
        JScrollPane scrollPane = new JScrollPane(emiTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10)); // Padding for scroll pane

        add(scrollPane, BorderLayout.CENTER);

        // Initial fetch when the window opens
        fetchLoanHistory();
        setVisible(true);
    }

    private void fetchLoanHistory() {
        tableModel.setRowCount(0); // Clear previous data
        Conn c = new Conn();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedState = null;
        ResultSet resul = null;
        PreparedStatement preparedSta = null;
        ResultSet resu = null;
        PreparedStatement preparedStatemen = null;
        ResultSet result = null;


        String query = "SELECT loan_no, amount, emi, appl_date, last_emi, totatpay FROM loandetails WHERE acc_no = ?";

        try {
            preparedStatement = c.c.prepareStatement(query);
            preparedStatement.setString(1, acc);
            resultSet = preparedStatement.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            if (!resultSet.isBeforeFirst()) {
                Vector<String> defaultRow = new Vector<>();
                defaultRow.add("N/A");
                defaultRow.add("0.00"); // Use 0.00 for numerical values
                defaultRow.add("0.00");
                defaultRow.add("0.00");
                defaultRow.add("0.00");
                defaultRow.add("N/A");
                defaultRow.add("N/A");
                defaultRow.add("N/A");
                tableModel.addRow(defaultRow);
                JOptionPane.showMessageDialog(this, "No loan details found for account number: " + acc, "No Data", JOptionPane.INFORMATION_MESSAGE);
            } else {
                while (resultSet.next()) {
                    Vector<String> row = new Vector<>();
                    String loanNo = resultSet.getString("loan_no");
                    double loanAmount = resultSet.getDouble("amount");
                    double emiAmount = resultSet.getDouble("emi");
                    double totalPaymentExpected = resultSet.getDouble("totatpay"); // Get total payment from loandetails

                    row.add(loanNo);
                    row.add(String.format("%.2f", loanAmount));
                    row.add(String.format("%.2f", emiAmount));

                    // Calculate Amount Paid for current loanNo
                    String paidEmiQuery = "SELECT SUM(emi) as total_emi_paid FROM emidetails WHERE status='paid' AND loan_no=?";
                    preparedState = c.c.prepareStatement(paidEmiQuery);
                    preparedState.setString(1, loanNo);
                    resul = preparedState.executeQuery();

                    double totalEmiPaid = 0;
                    if (resul.next()) {
                        totalEmiPaid = resul.getDouble("total_emi_paid");
                    }
                    row.add(String.format("%.2f", totalEmiPaid));

                    // Calculate Amount Left
                    double amountLeft = totalPaymentExpected - totalEmiPaid;
                    row.add(String.format("%.2f", amountLeft));

                    // Dates
                    row.add(dateFormat.format(resultSet.getDate("appl_date")));
                    row.add(dateFormat.format(resultSet.getDate("last_emi")));

                    // Get Next Due Date
                    String nextDueDateQuery = "SELECT MIN(due_date) as next_due FROM emidetails WHERE loan_no = ? AND status='unpaid'";
                    preparedStatemen = c.c.prepareStatement(nextDueDateQuery);
                    preparedStatemen.setString(1, loanNo);
                    result = preparedStatemen.executeQuery();

                    String nextDueDate = "N/A";
                    if (result.next() && result.getDate("next_due") != null) {
                        nextDueDate = dateFormat.format(result.getDate("next_due"));
                    }
                    row.add(nextDueDate);

                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching loan history: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Close all resources in a finally block
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (resul != null) resul.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedState != null) preparedState.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (resu != null) resu.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedSta != null) preparedSta.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (result != null) result.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (preparedStatemen != null) preparedStatemen.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (c.c != null) c.c.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    private void generateStatement() {
        if (tableModel.getRowCount() == 0 || (tableModel.getRowCount() == 1 && tableModel.getValueAt(0, 0).equals("N/A"))) {
            JOptionPane.showMessageDialog(this, "No loan details to generate a statement.", "No Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Loan History Statement");
        fileChooser.setSelectedFile(new File("Loan_History_Statement_" + acc + ".pdf"));

        // Show save dialog
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; // User cancelled
        }

        File fileToSave = fileChooser.getSelectedFile();

        Document document = new Document();
        Conn c = null; // Declare Conn outside try for finally block access
        ResultSet rs = null;
        try {
            // Fetch account holder's personal details
            String name = null, dob = null, panNo = null;
            String aadharNo = null; // Changed to String as it's often treated as such due to leading zeros/length

            c = new Conn(); // Initialize Conn here
            String personalDetailsQuery = "SELECT cd.name, cd.dob, cd.aad, cd.pan " +
                    "FROM cusdetails cd " +
                    "JOIN account a ON cd.form_no = a.form1 " +
                    "WHERE a.accno = ?";
            PreparedStatement pstmtPersonal = c.c.prepareStatement(personalDetailsQuery);
            pstmtPersonal.setString(1, acc);
            rs = pstmtPersonal.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
                dob = rs.getString("dob");
                aadharNo = rs.getString("aad");
                panNo = rs.getString("pan");
            }
            rs.close();
            pstmtPersonal.close();

            PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
            document.open();

            // Add Header
            Paragraph title = new Paragraph("LENDLOGIX - Loan History Statement");
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            // Add Personal Details
            document.add(new Paragraph("Account No: " + acc));
            document.add(new Paragraph("Account Holder Name: " + (name != null ? name : "N/A")));
            document.add(new Paragraph("Date of Birth: " + (dob != null ? dob : "N/A")));
            document.add(new Paragraph("Aadhar No: " + (aadharNo != null ? aadharNo : "N/A")));
            document.add(new Paragraph("PAN No: " + (panNo != null ? panNo : "N/A")));
            document.add(new Paragraph(" ")); // Spacer

            // Create Table for Loan History
            PdfPTable table = new PdfPTable(tableModel.getColumnCount()); // Use actual column count
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Add Table Headers
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                table.addCell(tableModel.getColumnName(i));
            }

            // Add Table Data
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                // Skip the "N/A" row if it's the only one
                if (tableModel.getValueAt(i, 0).equals("N/A") && tableModel.getRowCount() == 1) {
                    continue;
                }
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    table.addCell(tableModel.getValueAt(i, j).toString());
                }
            }
            document.add(table);
            document.add(new Paragraph(" ")); // Spacer

            // Add Footer Notes
            document.add(new Paragraph("This statement is valid for filing Income Tax."));
            document.add(new Paragraph("This is a computer generated receipt. No signature required."));
            document.close();
            JOptionPane.showMessageDialog(this, "Loan History Statement generated successfully!\nPath: " + fileToSave.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(this, "Error generating statement: " + e.getMessage(), "PDF Generation Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error while fetching personal details: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (c != null && c.c != null) c.c.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public static void main(String args[]) {
        // Example usage: Pass a valid account number
        SwingUtilities.invokeLater(() -> new loanhis("565042147785"));
    }
}