package auth;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Authentification extends JFrame {

    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtpasswrd;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Authentification frame = new Authentification();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Authentification() {
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 370, 404);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 144, 255));
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.gridwidth = GridBagConstraints.REMAINDER;
        gbc_panel.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel.insets = new Insets(0, 0, 10, 0);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        contentPane.add(panel, gbc_panel);

        JLabel lblLogin = new JLabel("LOGIN");
        panel.add(lblLogin);
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 23));
        lblLogin.setForeground(Color.WHITE);

        txtUsername = new JTextField();//email
        GridBagConstraints gbc_txtUsername = new GridBagConstraints();
        gbc_txtUsername.insets = new Insets(0, 10, 10, 10);
        gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUsername.gridx = 0;
        gbc_txtUsername.gridy = 3;
        gbc_txtUsername.weightx = 1.0;
        contentPane.add(txtUsername, gbc_txtUsername);
        txtUsername.setColumns(10);

        txtpasswrd = new JPasswordField();//password
        GridBagConstraints gbc_txtpasswrd = new GridBagConstraints();
        gbc_txtpasswrd.insets = new Insets(0, 10, 10, 10);
        gbc_txtpasswrd.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtpasswrd.gridx = 0;
        gbc_txtpasswrd.gridy = 5;
        gbc_txtpasswrd.weightx = 1.0;
        contentPane.add(txtpasswrd, gbc_txtpasswrd);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 15));
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.anchor = GridBagConstraints.WEST;
        gbc_lblUsername.insets = new Insets(0, 10, 10, 10);
        gbc_lblUsername.gridx =0;
        gbc_lblUsername.gridy = 2;
        contentPane.add(lblUsername, gbc_lblUsername);
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
        GridBagConstraints gbc_lblPassword = new GridBagConstraints();
        gbc_lblPassword.anchor = GridBagConstraints.WEST;
        gbc_lblPassword.insets = new Insets(0, 10, 10, 10);
        gbc_lblPassword.gridx = 0;
        gbc_lblPassword.gridy = 4;
        contentPane.add(lblPassword, gbc_lblPassword);

        JLabel lblFillYourInformations = new JLabel("Fill in your information");
        lblFillYourInformations.setForeground(new Color(30, 144, 255));
        lblFillYourInformations.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_lblFillYourInformations = new GridBagConstraints();
        gbc_lblFillYourInformations.anchor = GridBagConstraints.WEST;
        gbc_lblFillYourInformations.insets = new Insets(0, 10, 10, 10);
        gbc_lblFillYourInformations.gridx = 0;
        gbc_lblFillYourInformations.gridy = 1;
        contentPane.add(lblFillYourInformations, gbc_lblFillYourInformations);

        JLabel lblForgotPwd = new JLabel("Forgot password");
        lblForgotPwd.setForeground(new Color(30, 144, 255));
        lblForgotPwd.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblForgotPwd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // create and show the new window or dialog
                ResetPassword newClass = new ResetPassword();
                newClass.setVisible(true);
            }
        });
        GridBagConstraints gbc_lblForgotPwd = new GridBagConstraints();
        gbc_lblForgotPwd.anchor = GridBagConstraints.WEST;
        gbc_lblForgotPwd.insets = new Insets(0, 10, 10, 10);
        gbc_lblForgotPwd.gridx = 0;
        gbc_lblForgotPwd.gridy = 6;
        contentPane.add(lblForgotPwd, gbc_lblForgotPwd);

        JButton btnLogin = new JButton("Login");
        btnLogin.setForeground(new Color(255, 255, 255));
        btnLogin.setBackground(new Color(30, 144, 255));
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration", "root",
                            "password");
                    Statement stmt = cnx.createStatement();
                    String sql = "SELECT * FROM users WHERE email='" + txtUsername.getText() + "' AND password='"
                            + txtpasswrd.getText().toString() + "'";
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login Successful...");

                        // Close the authentication frame
                        dispose();

                        // Redirect to the home page
                        Home home = new Home();
                        home.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Username or password");
                    }
                    cnx.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        GridBagConstraints gbc_btnLogin = new GridBagConstraints();
        gbc_btnLogin.gridx = 0;
        gbc_btnLogin.gridy = 7;
        gbc_btnLogin.weightx = 1.0;
        gbc_btnLogin.insets = new Insets(0, 10, 10, 10);
        contentPane.add(btnLogin, gbc_btnLogin);

        JButton btnSignin = new JButton("Sign Up");
        btnSignin.setForeground(new Color(30,
        		144, 255));
        btnSignin.setBackground(new Color(255, 255, 255));
        btnSignin.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnSignin.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
        register obj = new register();
        obj.setVisible(true);
        }
        });
        GridBagConstraints gbc_btnSignin = new GridBagConstraints();
        gbc_btnSignin.gridx = 0;
        gbc_btnSignin.gridy = 8;
        gbc_btnSignin.weightx = 1.0;
        gbc_btnSignin.insets = new Insets(0, 10, 10, 10);
        contentPane.add(btnSignin, gbc_btnSignin);
        }
        }



