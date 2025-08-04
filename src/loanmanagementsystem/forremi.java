package loanmanagementsystem;

// import java.sql.Connection;
// import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
// import javax.swing.JOptionPane;


public class forremi {
	
	String acc,first;
	double loanAmount,monthlyPayment;
	int numberOfMonths,loanYears,inter,after;
	
	forremi(String acc, String first, double loanAmount, double monthlyPayment, int numberOfMonths, int loanYears){
		
		this.acc=acc;
	this.first=first;
	this.loanAmount=loanAmount;
	this.monthlyPayment=monthlyPayment;
	this.numberOfMonths=numberOfMonths;
	this.loanYears=loanYears;
	
	
	inter=365*loanYears;
	after=inter/numberOfMonths;
	after=after-3;
	 
	        
	       
	        Calendar ca = Calendar.getInstance();
	       
	        ca.add(Calendar.DAY_OF_MONTH, after); 
	        
	        
	try{
		Conn c= new Conn();
        String insertQuery = "INSERT INTO emiremain (loan_no,install_no,emi,remai_date) VALUES (?, ?, ?, ? )";
        try (PreparedStatement stmt = c.c.prepareStatement(insertQuery)) {
            for (int i = 0; i < numberOfMonths; i++) {
                stmt.setString(1, first); 
                stmt.setInt(2, (i+1));
                stmt.setDouble(3, monthlyPayment);
                stmt.setDate(4, new java.sql.Date(ca.getTimeInMillis()));
               
                
        
                stmt.executeUpdate();
                ca.add(Calendar.DAY_OF_MONTH, after); 
                
            }
        
      
}
    catch(Exception e) {
    	System.out.println(e);
    	
    }
      
        
	}
	catch(Exception e) {
    	System.out.println(e);
    	}
    
	}
	
public static void main(String []args) {
	
	new forremi("", "", 0, 0, 0, 0);
}
}
