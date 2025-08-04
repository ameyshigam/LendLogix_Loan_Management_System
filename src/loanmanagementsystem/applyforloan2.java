package loanmanagementsystem;

// import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class applyforloan2 {
	
	String acc,first;
	double loanAmount,monthlyPayment,totalPayment;
	int numberOfMonths,loanYears,inter,after;
	
	applyforloan2(String acc, String first, double loanAmount, double monthlyPayment, double totalPayment,int numberOfMonths, int loanYears){
	//	 System.out.println("Applying for loan...");
		this.acc=acc;
	this.first=first;
	this.loanAmount=loanAmount;
	this.monthlyPayment=monthlyPayment;
	this.totalPayment=totalPayment;
	this.numberOfMonths=numberOfMonths;
	this.loanYears=loanYears;
	
	
	inter=365*loanYears;
	after=inter/numberOfMonths;
	 
	        
	        Calendar calendar = Calendar.getInstance();
	        Calendar ca = Calendar.getInstance();
	       // calendar.add(Calendar.DAY_OF_MONTH, after); 
	        ca.add(Calendar.DAY_OF_MONTH, after); 
	        Conn c =new Conn();
	        try{
	            String insertQuery = "INSERT INTO loandetails (acc_no,loan_no,amount,emi,appl_date,last_emi,totatpay) VALUES (?, ?, ?,?, ?, ? ,?)";
	            try (PreparedStatement stmt = c.c.prepareStatement(insertQuery)) {
	               
	                    stmt.setString(1, acc); 
	                    stmt.setString(2, first);
	                    stmt.setDouble(3, loanAmount);
	                    stmt.setDouble(4, monthlyPayment);
	                    stmt.setDate(5, new java.sql.Date(calendar.getTimeInMillis()));
	                    for (int i = 0; i < numberOfMonths; i++) {
	                    	calendar.add(Calendar.DAY_OF_MONTH, after); 
	                    }
	                    stmt.setDate(6, new java.sql.Date(calendar.getTimeInMillis()));
	                    stmt.setDouble(7, totalPayment);
	                    stmt.executeUpdate();
	                    
	                }
	            
	           
	    }
	        catch(Exception e) {
	        	System.out.println(e);
	        	}
	        
	try{
        String insertQuery = "INSERT INTO emidetails (loan_no,insta_no,amount,emi,due_date,status) VALUES (?, ?, ?, ?, ? ,?)";
        try (PreparedStatement stmt = c.c.prepareStatement(insertQuery)) {
            for (int i = 0; i < numberOfMonths; i++) {
                stmt.setString(1, first); 
                stmt.setInt(2, (i+1));
                stmt.setDouble(3, loanAmount);
                stmt.setDouble(4, monthlyPayment);
                stmt.setDate(5, new java.sql.Date(ca.getTimeInMillis()));
                stmt.setString(6, "unpaid"); 
                
        
                stmt.executeUpdate();
                ca.add(Calendar.DAY_OF_MONTH, after); 
                
            }
        
      
}
    catch(Exception e) {
    	System.out.println(e);
    	
    }
        JOptionPane.showMessageDialog(null, "EMI Dates stored successfully!");
        
	}
	catch(Exception e) {
    	System.out.println(e);
    	}
    
	}
	
public static void main(String []args) {
	
	new applyforloan2("", "",0,0,0,0, 0);
}
}
