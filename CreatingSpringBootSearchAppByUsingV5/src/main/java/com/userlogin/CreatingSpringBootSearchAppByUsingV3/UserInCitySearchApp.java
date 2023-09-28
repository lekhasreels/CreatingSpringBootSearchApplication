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

public class UserInCitySearchApp {
	private JTextField cityField;
    private JTextArea resultArea;
    
    public UserInCitySearchApp() {
        JFrame frame = new JFrame("Users in City Search App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        cityField = new JTextField();
        resultArea = new JTextArea();
        JButton searchButton = new JButton("Search");

        panel.add(cityField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText().trim();
                if (!city.isEmpty()) {
                    searchByCity(city);
                } else {
                    resultArea.setText("Please enter a city name.");
                }
            }
        });

        frame.setVisible(true);
    }

    private void searchByCity(String city) {
        try {
            String url = "jdbc:mysql://localhost:3306/login-db";
            String user = "root";
            String password = "Sree27@532";

            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT username FROM user " +
                           "WHERE user_id IN (SELECT user_id FROM address WHERE city = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultArea.setText("Usernames in " + city + ":\n");
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                resultArea.append(username + "\n");
            }

            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            resultArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserInCitySearchApp());
    }
}
