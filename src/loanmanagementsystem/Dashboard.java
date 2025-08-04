package loanmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Dashboard extends JFrame implements ActionListener {
    JButton calcBtn, logoutBtn, changePassBtn, pastLoansBtn, applyLoanBtn;
    String acc;
    String username = "";

    public Dashboard(String acc) {
        this.acc = acc;
        fetchUserName();

        setTitle("LENDLOGIX - Dashboard");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // ==== HEADER ====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(33, 37, 41));

        JLabel userLabel = new JLabel("Welcome, " + username + " | Acc No: " + acc);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.DARK_GRAY);
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userLabel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // ==== CENTER BUTTONS ====
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        calcBtn = createStyledButton("Calculator", "images/calc.png");
        applyLoanBtn = createStyledButton("Apply for Loan", "images/loan.png");
        pastLoansBtn = createStyledButton("Loan History", "images/history.png");
        changePassBtn = createStyledButton("Change Password", "images/password.png");

        buttonPanel.add(calcBtn);
        buttonPanel.add(applyLoanBtn);
        buttonPanel.add(pastLoansBtn);
        buttonPanel.add(changePassBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // ==== FOOTER ====
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);

        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        logoutBtn.setBackground(new Color(220, 53, 69)); // Bootstrap red
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setIcon(resizeIcon("images/logout.png", 20, 20));

        footerPanel.add(logoutBtn);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Add listeners
        calcBtn.addActionListener(this);
        applyLoanBtn.addActionListener(this);
        pastLoansBtn.addActionListener(this);
        changePassBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 123, 255)); // Bootstrap blue
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setIcon(resizeIcon(iconPath, 20, 20));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        return button;
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.out.println("Icon not found: " + path);
            return null;
        }
    }

    private void fetchUserName() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(
                    "SELECT c.name FROM account a JOIN cusdetails c ON a.form1 = c.form_no WHERE a.accno = '" + acc + "'"
            );
            if (rs.next()) {
                username = rs.getString("name");
            } else {
                username = "User";
            }
        } catch (Exception e) {
            username = "User";
            e.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);

        if (e.getSource() == calcBtn) {
            new Calculator(acc).setVisible(true);
        } else if (e.getSource() == applyLoanBtn) {
            int count = 0;
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("SELECT COUNT(*) FROM loandetails WHERE acc_no = '" + acc + "'");
                if (rs.next()) count = rs.getInt(1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (count > 0) {
                new install(acc).setVisible(true);
            } else {
                new applyForLoan(acc).setVisible(true);
            }

        } else if (e.getSource() == pastLoansBtn) {
            new loanhis(acc).setVisible(true);
        } else if (e.getSource() == changePassBtn) {
            new fpass2(acc).setVisible(true);
        } else if (e.getSource() == logoutBtn) {
            new login().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Dashboard("565042147785");
    }
}
