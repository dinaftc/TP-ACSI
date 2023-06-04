package auth;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.util.Random;

public class ResetPassword extends JFrame {

    private JPanel contentPane;
    private JTextField txtEmail;
    private JTextField txtCode;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ResetPassword frame = new ResetPassword();
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
    public ResetPassword() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblResetPassword = new JLabel("Reset Password");
        lblResetPassword.setForeground(new Color(30, 144, 255));
        lblResetPassword.setFont(new Font("Tahoma", Font.BOLD, 23));
        lblResetPassword.setBounds(98, 11, 207, 28);
        contentPane.add(lblResetPassword);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEmail.setBounds(41, 76, 46, 14);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(97, 75, 208, 20);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        JButton btnSendCode = new JButton("Send Code");
        btnSendCode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // generate and send code to email
                String code = UUID.randomUUID().toString().substring(0, 6); // generate 6-character random code
                // code to send email with the code
                JOptionPane.showMessageDialog(null, "Code sent to email: " + code); // display code in message box (for testing)
            }
        });
        btnSendCode.setForeground(new Color(255, 255, 255));
        btnSendCode.setBackground(new Color(30, 144, 255));
        btnSendCode.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSendCode.setBounds(98, 106, 107, 23);
        contentPane.add(btnSendCode);

        JLabel lblCode = new JLabel("Code:");
        lblCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCode.setBounds(41, 155, 46, 14);
        contentPane.add(lblCode);

        txtCode = new JTextField();
        txtCode.setColumns(10);
        txtCode.setBounds(97, 154, 208, 20);
        contentPane.add(txtCode);

        JLabel lblNewPassword = new JLabel("New Password:");
        lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewPassword.setBounds(41, 207, 95, 14);
        contentPane.add(lblNewPassword);

        txtNewPassword = new JPasswordField();
        txtNewPassword.setBounds(146, 204, 187, 23);
        contentPane.add(txtNewPassword);
        txtNewPassword.setColumns(10);

        JButton btnResetPassword = new JButton("Reset Password");
        btnResetPassword.setForeground(new Color(255, 255, 255));
        btnResetPassword.setBackground(new Color(30, 144, 255));
        btnResetPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Check if the email is valid and exists in the database
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration", "root", "password");
                    String sql = "SELECT * FROM users WHERE email = ?";
                    PreparedStatement stmt = cnx.prepareStatement(sql);
                    stmt.setString(1, txtEmail.getText());
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        // Generate a random code
                        String code = String.format("%06d", new Random().nextInt(999999));

                        // Save the code in the database
                        String updateSql = "UPDATE users SET reset_code = ? WHERE email = ?";
                        PreparedStatement updateStmt = cnx.prepareStatement(updateSql);
                        updateStmt.setString(1, code);
                        updateStmt.setString(2, txtEmail.getText());
                        updateStmt.executeUpdate();

                        // Send the code to the user's email address
                        String subject = "Password Reset Code";
                        String message = "Your password reset code is: " + code;
                        Email.sendEmail(txtEmail.getText(), subject, message);

                        JOptionPane.showMessageDialog(null, "A password reset code has been sent to your email address.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid email address.");
                    }

                    cnx.close();
                } catch (Exception ex) {
                    System.out.print(ex);
                }
            }
        });
        btnResetPassword.setBounds(146, 255, 128, 23);
        contentPane.add(btnResetPassword);
    }
}
