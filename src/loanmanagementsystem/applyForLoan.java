package loanmanagementsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import javax.swing.*;

public class applyForLoan extends JFrame implements ActionListener {
    JButton attachButton, attachButt, attach, submitButton, submitButt, submit, b;
    File selectedFile, selectedFile1, selectedFile2;
    final String saveDirectory = "C:\\Users\\Pritesh\\OneDrive\\Documents\\loanappli"; // Change this to your desired save directory
    String acc;
    
    
    boolean isSalarySlipUploaded = false;
    boolean isIncomeCertificateUploaded = false;
    boolean isForm16Uploaded = false;

    applyForLoan(String acc) {
        this.acc = acc;

        JLabel text = new JLabel("      Apply For Loan");
        text.setFont(new Font("Impact", Font.ITALIC, 40));
        text.setBounds(150, 20, 800, 40);
        add(text);

        JLabel tex = new JLabel("Upload the required documents");
        tex.setFont(new Font("arial", Font.BOLD, 20));
        tex.setBounds(150, 70, 800, 40);
        add(tex);

        attachButton = new JButton("Salary Slip");
        attachButton.setBounds(100, 130, 150, 40);
        attachButton.setFont(new Font("Oswald", Font.BOLD, 15));
        attachButton.setBackground(Color.black);
        attachButton.setForeground(Color.white);
        add(attachButton);
        attachButton.addActionListener(this);

        submitButton = new JButton("Upload");
        submitButton.setBounds(270, 130, 100, 40);
        submitButton.setBackground(Color.black);
        submitButton.setForeground(Color.white);
        add(submitButton);
        submitButton.addActionListener(this);

        attachButt = new JButton("Income Certificate");
        attachButt.setBounds(100, 220, 150, 40);
        attachButt.setFont(new Font("Oswald", Font.BOLD, 13));
        attachButt.setBackground(Color.black);
        attachButt.setForeground(Color.white);
        add(attachButt);
        attachButt.addActionListener(this);

        submitButt = new JButton("Upload");
        submitButt.setBounds(270, 220, 100, 40);
        submitButt.setBackground(Color.black);
        submitButt.setForeground(Color.white);
        add(submitButt);
        submitButt.addActionListener(this);

        attach = new JButton("Form no. 16");
        attach.setBounds(100, 320, 150, 40);
        attach.setFont(new Font("Oswald", Font.BOLD, 15));
        attach.setBackground(Color.black);
        attach.setForeground(Color.white);
        add(attach);
        attach.addActionListener(this);

        submit = new JButton("Upload");
        submit.setBounds(270, 320, 100, 40);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        add(submit);
        submit.addActionListener(this);

        b = new JButton("SUBMIT");
        b.setFont(new Font("Raleway", Font.BOLD, 12));
        b.setBackground(Color.white);
        b.setForeground(Color.black);
        b.setBounds(200, 400, 100, 30);
        add(b);
        b.addActionListener(this);

        setLayout(null);
        getContentPane().setBackground(Color.white);
        setSize(600, 500);
        setVisible(true);
        setLocation(400, 200);
    }

    public void actionPerformed(ActionEvent ac) {
        if (ac.getSource() == attachButton) {
            selectedFile = selectFile("Select Salary Slip");
        } else if (ac.getSource() == attachButt) {
            selectedFile1 = selectFile("Select Income Certificate");
        } else if (ac.getSource() == attach) {
            selectedFile2 = selectFile("Select Form no. 16");
        } else if (ac.getSource() == submitButton) {
            isSalarySlipUploaded = uploadFile(selectedFile, "Salary Slip");
        } else if (ac.getSource() == submitButt) {
            isIncomeCertificateUploaded = uploadFile(selectedFile1, "Income Certificate");
        } else if (ac.getSource() == submit) {
            isForm16Uploaded = uploadFile(selectedFile2, "Form no. 16");
        } else if (ac.getSource() == b) {
            if (isSalarySlipUploaded && isIncomeCertificateUploaded && isForm16Uploaded) {
                
                setVisible(false);
                new install(acc).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please upload all required documents before submission.");
            }
        }
    }

    private File selectFile(String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(null, "File attached: " + file.getName());
            return file;
        }
        return null;
    }

    private boolean uploadFile(File file, String documentName) {
        if (file == null) {
            JOptionPane.showMessageDialog(null, "Attach " + documentName, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
        	Conn c =new Conn();
        
             PreparedStatement stmt = c.c.prepareStatement("INSERT INTO loan_documents (account_number, document_name, document_data) VALUES (?, ?, ?)");

            stmt.setString(1, acc);
            stmt.setString(2, documentName);
            try (FileInputStream fis = new FileInputStream(file)) {
                stmt.setBinaryStream(3, fis, (int) file.length());
                stmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, documentName + " uploaded successfully to the database.");
            return true; // Indicate success
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error uploading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Indicate failure
        }
    
    }
    public static void main(String args[]) {
        new applyForLoan(""); 
    }
}
