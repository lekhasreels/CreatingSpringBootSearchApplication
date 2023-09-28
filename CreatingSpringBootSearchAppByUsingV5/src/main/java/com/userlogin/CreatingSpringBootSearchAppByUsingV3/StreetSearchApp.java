package com.userlogin.CreatingSpringBootSearchAppByUsingV3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class StreetSearchApp {

	private JTextField searchField;
    private JTextArea resultArea;
	
    public StreetSearchApp() {
        JFrame frame = new JFrame("Street Search App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        searchField = new JTextField();
        resultArea = new JTextArea();

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                if (!searchTerm.isEmpty()) {
                    searchByStreet(searchTerm);
                } else {
                    resultArea.setText("Please enter a street name.");
                }
            }
        });
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }
    private void searchByStreet(String streetName) {
    try {
        String url = "jdbc:mysql://localhost:3306/login-db";
        String user = "root";
        String password = "Sree27@532";

        Connection connection = DriverManager.getConnection(url, user, password);

        String query = "SELECT COUNT(DISTINCT a.building) AS num_buildings, " +
                       "COUNT(DISTINCT a.apartment) AS num_apartments, " +
                       "GROUP_CONCAT(DISTINCT u.username) AS usernames " +
                       "FROM address a " +
                       "INNER JOIN user u ON a.user_id = u.user_id " +
                       "WHERE a.street = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, streetName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int numBuildings = resultSet.getInt("num_buildings");
            int numApartments = resultSet.getInt("num_apartments");
            String usernames = resultSet.getString("usernames");

            resultArea.setText("Number of Buildings: " + numBuildings + "\n");
            resultArea.append("Number of Apartments: " + numApartments + "\n");
            resultArea.append("Usernames: " + usernames);
        } else {
            resultArea.setText("No results found.");
        }
        connection.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        resultArea.setText("Error: " + ex.getMessage());
    }
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StreetSearchApp());
    }
}
