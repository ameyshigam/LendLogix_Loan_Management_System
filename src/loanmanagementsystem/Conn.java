package loanmanagementsystem;
import java.sql.*;

public class Conn {
Connection c;
Statement s;
public Conn() {
try {
	
	c= DriverManager.getConnection("jdbc:mysql://localhost:3306/***","root","***");//enter your user mostly root and its password
	s =c.createStatement();
	
	
}	catch(Exception e) {
	System.out.println(e);
	
	
}

}
}
