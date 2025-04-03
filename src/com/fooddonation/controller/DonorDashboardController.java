package com.fooddonation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class DonorDashboardController {

    private String donorEmail;
    private String donorName;

    public void setDonorInfo(String email, String name) {
        this.donorEmail = email;
        this.donorName = name;
    }

    @FXML
    private void handleMakeDonation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fooddonation/view/DonationForm.fxml"));
            Parent root = loader.load();

            DonationFormController controller = loader.getController();
            controller.setDonorInfo(donorEmail, donorName);  

            Stage stage = new Stage();
            stage.setTitle("Make a Donation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleViewMyDonations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fooddonation/view/DonationList.fxml"));
            Parent root = loader.load();

            DonationListController listController = loader.getController();
            listController.loadDonationsByEmail(donorEmail);

            Stage stage = new Stage();
            stage.setTitle("My Donations - Donor User");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
