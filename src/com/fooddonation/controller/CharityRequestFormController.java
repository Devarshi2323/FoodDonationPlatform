package com.fooddonation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.fooddonation.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class CharityRequestFormController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField itemField;

    @FXML
    private TextField quantityField;

    @FXML
    private Label messageLabel;

    private String staffEmail;
    private String staffName;

    
    public void setStaffInfo(String email, String name) {
        this.staffEmail = email;
        this.staffName = name;

        if (emailField != null && nameField != null) {
            emailField.setText(email);
            emailField.setEditable(false);

            nameField.setText(name);
            nameField.setEditable(false);
        }
    }


    @FXML
    private void handleSubmitRequest() {
        String item = itemField.getText();
        String quantityStr = quantityField.getText();

        if (item.isEmpty() || quantityStr.isEmpty()) {
            messageLabel.setText("All fields are required.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            try (Connection conn = DBUtil.getConnection()) {
                String sql = "INSERT INTO charity_requests (staff_email, item_name, quantity, request_date, status) VALUES (?, ?, ?, ?, 'Pending')";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, staffEmail);
                stmt.setString(2, item);
                stmt.setInt(3, quantity);
                stmt.setString(4, LocalDate.now().toString());

                stmt.executeUpdate();
                messageLabel.setText("Request submitted!");
                itemField.clear();
                quantityField.clear();
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Quantity must be a number.");
        } catch (Exception e) {
            messageLabel.setText("Error submitting request.");
            e.printStackTrace();
        }
    }
}
