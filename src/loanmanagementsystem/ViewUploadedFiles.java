package loanmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

public class ViewUploadedFiles extends JFrame implements ActionListener {
    String acc;
    JTable table;
    JButton downloadButton;
    
    public ViewUploadedFiles(String acc) {
        this.acc = acc;
        setTitle("Uploaded Files");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Table to display files
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Download button
        downloadButton = new JButton("Download Selected File");
        downloadButton.addActionListener(this);
        add(downloadButton, BorderLayout.SOUTH);
        
        loadFiles();
        
        setVisible(true);
    }
    
    private void loadFiles() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Document Name"}, 0);
        try {
        	Conn c=new Conn();
             PreparedStatement ps = c.c.prepareStatement("SELECT id, document_name FROM loan_documents WHERE account_number = ?");
             
            ps.setString(1, acc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("document_name")});
            }
            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading files: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a file to download.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int fileId = (Integer) table.getValueAt(selectedRow, 0);
        downloadFile(fileId);
    }
    
    private void downloadFile(int fileId) {
    	
        try {
        	Conn c = new Conn();
             PreparedStatement ps = c.c.prepareStatement("SELECT document_name, document_data FROM loan_documents WHERE id = ?");
             
            ps.setInt(1, fileId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String documentName = rs.getString("document_name");
                InputStream is = rs.getBinaryStream("document_data");
                FileOutputStream fos = new FileOutputStream("C:\\Users\\Pritesh\\OneDrive\\Documents\\loanappli" + documentName); // Adjust the path as needed
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.close();
                JOptionPane.showMessageDialog(this, "File downloaded successfully: " + documentName);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error downloading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String args[]) {
        new ViewUploadedFiles("565042191800"); // Replace with the actual account number
    }
}
