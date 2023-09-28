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

public class UserDetailsSearchApp {
	private JTextField usernameField;
    private JTextArea resultArea;

    public UserDetailsSearchApp() {
        JFrame frame = new JFrame("User Details Search App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        usernameField = new JTextField();
        resultArea = new JTextArea();
        JButton searchButton = new JButton("Search");

        panel.add(usernameField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                if (!username.isEmpty()) {
                    searchByUsername(username);
                } else {
                    resultArea.setText("Please enter a username.");
                }
            }
        });

        frame.setVisible(true);
    }

    private void searchByUsername(String username) {
        try {
            String url = "jdbc:mysql://localhost:3306/login-db";
            String user = "root";
            String password = "Sree27@532";

            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT a.street, a.city, a.pincode, a.road_no, a.building, a.apartment, " +
                           "u.user_job " +
                           "FROM address a " +
                           "INNER JOIN user u ON a.user_id = u.user_id " +
                           "WHERE u.username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultArea.setText("");
            while (resultSet.next()) {
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                String pincode = resultSet.getString("pincode");
                String roadNo = resultSet.getString("road_no");
                String building = resultSet.getString("building");
                String apartment = resultSet.getString("apartment");
                String userJob = resultSet.getString("user_job");

                resultArea.append("Street: " + street + "\n");
                resultArea.append("City: " + city + "\n");
                resultArea.append("Pincode: " + pincode + "\n");
                resultArea.append("Road Number: " + roadNo + "\n");
                resultArea.append("Building: " + building + "\n");
                resultArea.append("Apartment: " + apartment + "\n");
                resultArea.append("User Job: " + userJob + "\n\n");
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserDetailsSearchApp());
    }
}