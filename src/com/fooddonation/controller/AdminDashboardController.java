package com.fooddonation.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    private void handleViewDonations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fooddonation/view/DonationList.fxml"));
            Parent root = loader.load();

            
            DonationListController controller = loader.getController();
            controller.loadAllDonations(); 

            Stage stage = new Stage();
            stage.setTitle("All Donations");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewUsers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fooddonation/view/UserList.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("All Registered Users");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleViewRequests() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fooddonation/view/CharityRequestList.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("All Charity Requests");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleManageUsers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fooddonation/view/UserManagement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Users - Admin");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    


}
