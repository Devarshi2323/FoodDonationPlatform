package com.fooddonation.controller;

import com.fooddonation.utils.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChangePasswordController {

    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;

    private String userEmail;

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    @FXML
    private void handleChangePassword() {
        String oldPass = oldPasswordField.getText();
        String newPass = newPasswordField.getText();
        String confirmPass = confirmPasswordField.getText();

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            messageLabel.setText("All fields required.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            messageLabel.setText("New passwords do not match.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String checkSql = "SELECT password_hash FROM users WHERE email=?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, userEmail);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String currentHash = rs.getString("password_hash");
                String oldHash = DBUtil.hashPassword(oldPass);

                if (!currentHash.equals(oldHash)) {
                    messageLabel.setText("Current password incorrect.");
                    return;
                }
            }

            String updateSql = "UPDATE users SET password_hash=? WHERE email=?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, DBUtil.hashPassword(newPass));
            updateStmt.setString(2, userEmail);
            updateStmt.executeUpdate();

            messageLabel.setText("Password updated successfully.");

        } catch (Exception e) {
            messageLabel.setText("Error updating password.");
            e.printStackTrace();
        }
    }
}
