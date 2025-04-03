package com.fooddonation.controller;

import com.fooddonation.utils.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistrationController  {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleBox;
    @FXML private Label messageLabel;
    
    

    @FXML
    public void initialize() {
        roleBox.getItems().addAll("Donor", "CharityStaff");
    }

    @FXML
    private void handleRegister() {
        String name = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = roleBox.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            messageLabel.setText("Please fill all fields.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (name, email, password_hash, role, status)\r\n"
            		+ " VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, DBUtil.hashPassword(password));
            stmt.setString(4, role);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                messageLabel.setText("Registration successful! Awaiting admin approval.");
            } else {
                messageLabel.setText("Registration failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error during registration.");
        }
    }
}
