package loanmanagementsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder; // Import for EmptyBorder
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat; // Import SimpleDateFormat for consistent date display
import java.util.Vector;

public class loanhistory extends JFrame {
    private JButton fetchButton;
    private JTable emiTable;
    private DefaultTableModel tableModel;
    private String acc;
    private JLabel accountNoDisplayLabel; // Used to display the account number prominently

    public loanhistory(String acc) {
        this.acc = acc;

        setTitle("Loan History - LendLogix"); // More descriptive title
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(20, 20)); // Increased gaps for better spacing
        setSize(950, 500); // Adjusted size for better content visibility
        setLocationRelativeTo(null); // Center the window on the screen
        getContentPane().setBackground(Color.decode("#F0F8FF")); // AliceBlue background

        // --- Define common fonts for consistent UI ---
        Font titleFont = new Font("Segoe UI", Font.BOLD, 22); // For main title
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16); // For account number label
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 15); // For buttons
        Font tableHeaderFont = new Font("Segoe UI", Font.BOLD, 15); // For table headers
        Font tableCellFont = new Font("Segoe UI", Font.PLAIN, 14); // For table cell data

        // --- Top Input Panel ---
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15)); // Centered flow layout with more gaps
        inputPanel.setBackground(Color.decode("#E0FFFF")); // LightCyan background for the panel
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Add padding

        JLabel titleLabel = new JLabel("Loan History for Account:"); // Renamed to avoid conflict with JFrame.title
        titleLabel.setFont(labelFont);
        titleLabel.setForeground(Color.decode("#2F4F4F")); // DarkSlateGray color for text
        inputPanel.add(titleLabel);

        accountNoDisplayLabel = new JLabel(acc); // Using the class member JLabel
        accountNoDisplayLabel.setFont(labelFont.deriveFont(Font.ITALIC)); // Italicize for distinction
        accountNoDisplayLabel.setForeground(Color.decode("#005C00")); // Dark green for the account number
        inputPanel.add(accountNoDisplayLabel);

        fetchButton = new JButton("Fetch Loan History");
        fetchButton.setFont(buttonFont);
        fetchButton.setBackground(Color.decode("#28A745")); // A vibrant green
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFocusPainted(false); // Remove border when focused
        fetchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        fetchButton.addActionListener(e -> fetchLoanHistory()); // Use lambda for brevity
        inputPanel.add(fetchButton);

        add(inputPanel, BorderLayout.NORTH); // Add the control panel to the NORTH of the frame

        // --- Table Setup ---
        String[] columnNames = {"Loan No.", "Amount (₹)", "Monthly EMI (₹)", "Applied Date", "Last EMI Date", "Next Due Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        emiTable = new JTable(tableModel);
        emiTable.setRowHeight(30); // Increased row height for better readability
        emiTable.setFont(tableCellFont);
        emiTable.setGridColor(Color.decode("#E0E0E0")); // Lighter grid lines
        emiTable.setFillsViewportHeight(true); // Ensures table fills the scroll pane

        // Table Header Styling
        JTableHeader header = emiTable.getTableHeader();
        header.setFont(tableHeaderFont);
        header.setBackground(Color.decode("#D3D3D3")); // Light grey background for header
        header.setForeground(Color.BLACK); // Black text for header
        header.setPreferredSize(new Dimension(header.getWidth(), 35)); // Set header height

        // Table Selection Styling
        emiTable.setSelectionBackground(Color.decode("#ADD8E6")); // LightBlue selection color
        emiTable.setSelectionForeground(Color.BLACK); // Black text on selection

        JScrollPane tableScrollPane = new JScrollPane(emiTable);
        // Add padding around the table scroll pane within the BorderLayout.CENTER area
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        add(tableScrollPane, BorderLayout.CENTER); // Add the scrollable table to the CENTER

        // --- Initial Data Fetch ---
        fetchLoanHistory(); // Load data immediately when the window opens
    }

    private void fetchLoanHistory() {
        tableModel.setRowCount(0); // Clear previous data
        Conn c = null; // Declare Conn outside try-with-resources for explicit closing
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement duePs = null;
        ResultSet dueRs = null;

        String query = "SELECT loan_no, amount, emi, appl_date, last_emi FROM loandetails WHERE acc_no = ?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Define date format

        try {
            c = new Conn(); // Initialize connection
            ps = c.c.prepareStatement(query);
            ps.setString(1, acc);
            rs = ps.executeQuery();

            boolean dataFound = false;
            while (rs.next()) {
                dataFound = true;
                Vector<String> row = new Vector<>();
                String loanNo = rs.getString("loan_no");
                row.add(loanNo);
                row.add(String.format("%.2f", rs.getDouble("amount"))); // Format amount to 2 decimal places
                row.add(String.format("%.2f", rs.getDouble("emi"))); // Format EMI to 2 decimal places
                row.add(dateFormat.format(rs.getDate("appl_date"))); // Format date
                row.add(dateFormat.format(rs.getDate("last_emi"))); // Format date

                // Fetch first unpaid EMI due date
                String queryDue = "SELECT due_date FROM emidetails WHERE loan_no = ? AND status = 'unpaid' ORDER BY due_date ASC LIMIT 1"; // Order by due_date to get the *next* due date
                duePs = c.c.prepareStatement(queryDue);
                duePs.setString(1, loanNo);
                dueRs = duePs.executeQuery();
                if (dueRs.next()) {
                    row.add(dateFormat.format(dueRs.getDate("due_date"))); // Format date
                } else {
                    row.add("N/A"); // If no unpaid EMIs
                }
                dueRs.close(); // Close ResultSet for duePs
                duePs.close(); // Close PreparedStatement for duePs

                tableModel.addRow(row);
            }

            if (!dataFound) {
                JOptionPane.showMessageDialog(this, "No loan details found for account number: " + acc, "No Data", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error occurred:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Print stack trace for debugging
        } finally {
            // Ensure all JDBC resources are closed
            try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (dueRs != null) dueRs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (duePs != null) duePs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            try { if (c != null && c.c != null) c.c.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        // Ensure UI updates are on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new loanhistory("565042147785")); // demo account
    }
}