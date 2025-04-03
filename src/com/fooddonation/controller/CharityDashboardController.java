package com.fooddonation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CharityDashboardController {

	private String currentStaffEmail;
	private String currentStaffName;

	public void setStaffInfo(String email, String name) {
	    this.currentStaffEmail = email;
	    this.currentStaffName = name;
	}


    @FXML
    private void handleViewDonations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fooddonation/view/DonationList.fxml"));
            Parent root = loader.load();

            DonationListController controller = loader.getController();
            controller.loadAllDonations();

            Stage stage = new Stage();
            stage.setTitle("All Donations - Charity Staff");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMakeRequest() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fooddonation/view/CharityRequestForm.fxml"));
            Parent root = loader.load();

            CharityRequestFormController controller = loader.getController();
            controller.setStaffInfo(currentStaffEmail, currentStaffName);

            Stage stage = new Stage();
            stage.setTitle("Request Donation Item");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
