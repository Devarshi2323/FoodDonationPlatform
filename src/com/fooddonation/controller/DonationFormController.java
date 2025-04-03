package com.fooddonation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.fooddonation.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;


public class DonationFormController {

    @FXML private TextField donorNameField;
    @FXML private TextField donorEmailField;
    @FXML private TextField itemField;
    @FXML private TextField quantityField;
    @FXML private Label messageLabel;

    private String donorEmail;
    private String donorName;

    public void setDonorInfo(String email, String name) {
        this.donorEmail = email;
        this.donorName = name;
        donorEmailField.setText(email);
        donorNameField.setText(name);
    }

    @FXML
    private void handleSubmit() {
        String item = itemField.getText().trim();
        String quantityText = quantityField.getText().trim();

        if (item.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Item name cannot be empty.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Quantity must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Quantity must be a number.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO donations (donor_name, donor_email, item_name, quantity, donation_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, donorName);
            stmt.setString(2, donorEmail);
            stmt.setString(3, item);
            stmt.setInt(4, quantity);
            stmt.setString(5, LocalDate.now().toString());

            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Donation submitted!");
            itemField.clear();
            quantityField.clear();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error submitting donation.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
