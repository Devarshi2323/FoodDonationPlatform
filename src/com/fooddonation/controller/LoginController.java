package com.fooddonation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

import com.fooddonation.utils.DBUtil;

import java.sql.*;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        roleBox.getItems().addAll("Admin", "Donor", "CharityStaff");
    }

    @FXML
    private void handleRegisterRedirect() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fooddonation/view/RegistrationForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User Registration");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = roleBox.getValue();

        if (email.isEmpty() || password.isEmpty() || role == null) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            // ✅ Hash the entered password
            String hashedPassword = DBUtil.hashPassword(password);
            
            // ✅ Debug line to print the hashed password
            //System.out.println("Hashed (entered) password: " + hashedPassword);

            // ✅ Compare hashed password
            String sql = "SELECT * FROM users WHERE email=? AND password_hash=? AND role=? AND status='Approved'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, role);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Stage stage = (Stage) emailField.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                Parent root;

                String fullName = rs.getString("name"); // ✅ Get name directly from query result

                switch (role) {
                    case "Admin":
                        loader.setLocation(getClass().getResource("/com/fooddonation/view/AdminDashboard.fxml"));
                        root = loader.load();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Dashboard - Admin");
                        break;

                    case "Donor":
                        loader.setLocation(getClass().getResource("/com/fooddonation/view/DonorDashboard.fxml"));
                        Parent donorRoot = loader.load();
                        DonorDashboardController donorController = loader.getController();
                        donorController.setDonorInfo(email, fullName);

                        stage.setScene(new Scene(donorRoot));
                        stage.setTitle("Dashboard - Donor");
                        break;

                    case "CharityStaff":
                        loader.setLocation(getClass().getResource("/com/fooddonation/view/CharityDashboard.fxml"));
                        Parent charityRoot = loader.load();
                        CharityDashboardController charityController = loader.getController();
                        charityController.setStaffInfo(email, fullName);

                        stage.setScene(new Scene(charityRoot));
                        stage.setTitle("Dashboard - Charity Staff");
                        break;

                    default:
                        messageLabel.setText("Unknown role.");
                        return;
                }

            } else {
                messageLabel.setText("Invalid credentials or account not approved.");
            }

        } catch (Exception e) {
            messageLabel.setText("Login error.");
            e.printStackTrace();
        }
    }
}
