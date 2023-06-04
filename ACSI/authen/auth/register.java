package auth;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;

public class register extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPswrd;

	/**
	 * Launch the application.
	 * 
	 */
	   
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					register frame = new register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public register() {
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		panel.setBounds(0, 0, 386, 45);
		contentPane.add(panel);
		
		JLabel lblLogin = new JLabel("REGISTERATION");
		panel.add(lblLogin);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 23));
		lblLogin.setForeground(Color.WHITE);
		
		JTextField txtUsername = new JTextField();
		txtUsername.setBounds(101, 138, 190, 35);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUsername.setBounds(101, 109, 88, 24);
		contentPane.add(lblUsername);
		
		JLabel lblFillYourInformations = new JLabel("Enter your informations");
		lblFillYourInformations.setForeground(new Color(30, 144, 255));
		lblFillYourInformations.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFillYourInformations.setBounds(89, 74, 221, 24);
		contentPane.add(lblFillYourInformations);
		
		txtPswrd = new JPasswordField();
		txtPswrd.setBounds(101, 208, 190, 35);
		contentPane.add(txtPswrd);
		
		JLabel label = new JLabel("Password");
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setBounds(101, 184, 91, 14);
		contentPane.add(label);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection cnx=DriverManager.getConnection("jdbc:mysql://localhost:3306/registration","root","password");
				    Statement stmt= cnx.createStatement();
				    
				    String query = "SELECT * FROM `users` WHERE `email` =?";
			       				    
				    PreparedStatement ps = cnx.prepareStatement(query);

				    ps.setString(1,txtUsername.getText() );
		            
				    ResultSet rslt = ps.executeQuery();
		            
		            if(rslt.next())
		            {
		            	JOptionPane.showMessageDialog(null, "This Username Already Exist");
		            }
		            else {
				    
				    String sql="Insert ignore into users(email,password) values("+"'"+txtUsername.getText()+"'"+",'"+txtPswrd.getText().toString()+"')";   
				    				
				    int rs=stmt.executeUpdate(sql);
				    if (rs==0) {
				    	JOptionPane.showMessageDialog(btnNewButton,"User already exists.");}
				    else {JOptionPane.showMessageDialog(btnNewButton,"Registration succesful !");}
		            }
				    cnx.close(); 
				   
				}
			    catch(Exception ex) {System.out.print(ex);}	
			}
		});
		btnNewButton.setForeground(new Color(30, 144, 255));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setBounds(142, 283, 101, 23);
		contentPane.add(btnNewButton);
		
	}

}
