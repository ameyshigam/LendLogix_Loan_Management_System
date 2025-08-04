package loanmanagementsystem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;


public class password extends JFrame implements ActionListener{
	JPasswordField  t1,t2;
	JLabel accno,num,appl,pass,pass1;
	JButton b, c;
	String first;
	Random ran;
	String formno,aad,sal;
	password(String formno,String aad,String sal ){
	
		this.formno =formno;
		this.aad=aad;
		this.sal=sal;
		appl = new JLabel("          ACCOUNT DETAILS");
        appl.setFont(new Font("Impact", Font.ITALIC, 28));
        appl.setBounds(240,20,600,40);
        add(appl);
		
        accno = new JLabel("YOUR ACCOUNT NUMBER :");
        accno.setFont(new Font("Raleway", Font.BOLD, 18));
        accno.setBounds(100,140,250,30);
        add(accno);
        
         ran = new Random();
        long first4 = (ran.nextLong() % 90000L) + 10000L;
        first = "5650421"+"" + Math.abs(first4);
        
        num = new JLabel(first);
        num.setFont(new Font("Raleway", Font.BOLD, 20));
        num.setBounds(370,140,500,30);
        add(num);
        
        pass = new JLabel("Set Your Password :");
        pass.setFont(new Font("Raleway", Font.BOLD, 18));
        pass.setBounds(100,200,250,30);
        add(pass);
        
        t1 = new JPasswordField ();
        t1.setFont(new Font("Raleway", Font.BOLD, 14));
        t1.setBounds(350,200,300,30);
        add(t1);
        
      
        
        pass1 = new JLabel("Confirm your Password:");
        pass1.setFont(new Font("Raleway", Font.BOLD, 18));
        pass1.setBounds(100,240,250,30);
        add(pass1);
    
        t2 = new JPasswordField ();
        t2.setFont(new Font("Raleway", Font.BOLD, 14));
        t2.setBounds(350,240,300,30);
        add(t2);
		
        b = new JButton("SUBMIT");
        b.setFont(new Font("Raleway", Font.BOLD, 12));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setBounds(420,340,100,30);
        add(b);
        b.addActionListener(this);
        

		 c = new JButton("Reset");
		 c.setFont(new Font("Raleway", Font.BOLD, 12));
		c.setBackground(Color.black);
		c.setForeground(Color.white);
		c.setBounds(300, 340, 100, 30);
		add(c);
		c.addActionListener(this);
	
        
		setLayout(null);
		getContentPane().setBackground(Color.white);
			setSize(800,480);
			setVisible(true);
			setLocation(400,200);
			
		
	}
	
	
	
	
public void actionPerformed(ActionEvent ac) {
	
	
	if(ac.getSource()==b) {
		
		
		
		
		String t3=t1.getText();
		String t4=t2.getText();
	
			 
			 
			 String accno=first;
				String passwrd = t2.getText();
				if(passwrd.length()<8) {
					JOptionPane.showMessageDialog(null,"Password should be of atleast 8 characters or numbers");
				}
				else {
				try {
					if (t3.equals("")){
						JOptionPane.showMessageDialog(null,"Password field cannot be empty");
					}
					else if(t4.equals("")){
						JOptionPane.showMessageDialog(null,"Password field cannot be empty");
					}
					else{
						if(t3.equals(t4) ){
							
						
						Conn c = new Conn();
						String query ="insert into account values('"+accno+"','"+passwrd+"','"+formno+"','"+aad+"','"+sal+"')";
						c.s.executeUpdate(query);
						setVisible(false);
						 new login().setVisible(true);
						}
						else {
							JOptionPane.showMessageDialog(null,"Entered password and confirmed password is not same");
							return;
							
					}
					}
					
				}catch(Exception e) {
					
					System.out.println(e);
				}
			
		
		
			
		}
	}
	
		
	else {
			t2.setText("");
			t1.setText("");
			
		}
}
	 public static void main(String[] args){
	        new password("","","").setVisible(true);
	      
	        
	    }
	}



