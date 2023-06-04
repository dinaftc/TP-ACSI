package auth;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
public class Home extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextArea txtMaladies;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Home frame = new Home();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Home() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 602, 401);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 144, 255));
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblHome = new JLabel("Home");
        panel.add(lblHome);
        lblHome.setFont(new Font("Tahoma", Font.BOLD, 23));
        lblHome.setForeground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);

        JLabel lblLesMaladies = new JLabel("Les maladies");
        lblLesMaladies.setForeground(new Color(30, 144, 255));
        lblLesMaladies.setFont(new Font("Tahoma", Font.BOLD, 18));
        mainPanel.add(lblLesMaladies, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        txtMaladies = new JTextArea();
        txtMaladies.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtMaladies.setEditable(false);
        scrollPane.setViewportView(txtMaladies);

        JButton btnAjouter = new JButton("Ajouter une maladie");
        btnAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomMaladie = JOptionPane.showInputDialog(Home.this, "Entrez le nom de la maladie:");
                String descriptionMaladie = JOptionPane.showInputDialog(Home.this, "Entrez la description de la maladie:");
                String symptomesMaladie = JOptionPane.showInputDialog(Home.this, "Entrez les symptômes de la maladie:");

                try {
                    Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration", "root", "password");
                    Statement stmt = cnx.createStatement();

                    String sqlInsert = "INSERT INTO maladies (nom, description) VALUES ('" + nomMaladie + "', '" + descriptionMaladie + "')";
                    int rowsAffected = stmt.executeUpdate(sqlInsert);

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(Home.this, "La maladie a été ajoutée avec succès.");
                    } else {
                        JOptionPane.showMessageDialog(Home.this, "Erreur lors de l'ajout de la maladie.");
                    }

                    cnx.close();
                    updateMaladiesList();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(btnAjouter, BorderLayout.SOUTH);

        JButton btnSupprimer = new JButton("Supprimer une maladie");
     
        	btnSupprimer.addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent e) {
        	        String nomMaladie = JOptionPane.showInputDialog(Home.this, "Entrez le nom de la maladie à supprimer:");

        	        try {
        	            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration", "root", "password");
        	            Statement stmt = cnx.createStatement();

        	            // Delete symptoms of the disease
        	            String sqlDeleteSymptomes = "DELETE FROM symptomes WHERE maladie_id IN "
        	                    + "(SELECT id FROM maladies WHERE nom = '" + nomMaladie + "')";
        	            int symptomesRowsAffected = stmt.executeUpdate(sqlDeleteSymptomes);

        	            // Delete the disease
        	            String sqlDeleteMaladie = "DELETE FROM maladies WHERE nom = '" + nomMaladie + "'";
        	            int maladieRowsAffected = stmt.executeUpdate(sqlDeleteMaladie);

        	            if (symptomesRowsAffected > 0 && maladieRowsAffected > 0) {
        	                JOptionPane.showMessageDialog(Home.this, "La maladie et ses symptômes ont été supprimés avec succès.");
        	            } else {
        	                JOptionPane.showMessageDialog(Home.this, "Erreur lors de la suppression de la maladie et de ses symptômes.");
        	            }

        	            cnx.close();
        	            updateMaladiesList();
        	        } catch (Exception ex) {
        	            ex.printStackTrace();
        	        }
        	    }
        	});

    

        mainPanel.add(btnSupprimer, BorderLayout.EAST);

        updateMaladiesList();
    }

    public void updateMaladiesList() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration", "root", "password");
            Statement stmt = cnx.createStatement();

            String sql = "SELECT maladies.nom, maladies.description, GROUP_CONCAT(symptomes.symptome SEPARATOR ', ') AS symptomes "
                    + "FROM maladies "
                    + "LEFT JOIN symptomes ON maladies.id = symptomes.maladie_id "
                    + "GROUP BY maladies.nom, maladies.description";

            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder maladiesText = new StringBuilder();
            while (rs.next()) {
                String nomMaladie = rs.getString("nom");
                String descriptionMaladie = rs.getString("description");
                String symptomesMaladie = rs.getString("symptomes");

                maladiesText.append(nomMaladie).append(":\n");
                maladiesText.append(descriptionMaladie).append("\n");
                maladiesText.append("Symptômes:\n").append(symptomesMaladie).append("\n\n");
            }
            txtMaladies.setText(maladiesText.toString());

            cnx.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


