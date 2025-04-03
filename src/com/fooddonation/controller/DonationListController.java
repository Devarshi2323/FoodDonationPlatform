package com.fooddonation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.fooddonation.model.Donation;
import com.fooddonation.utils.DBUtil;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DonationListController implements Initializable  {

    @FXML private TableView<Donation> donationTable;
    @FXML private TableColumn<Donation, Integer> idColumn;
    @FXML private TableColumn<Donation, String> donorColumn;
    @FXML private TableColumn<Donation, String> itemColumn;
    @FXML private TableColumn<Donation, Integer> quantityColumn;
    @FXML private TableColumn<Donation, String> dateColumn;

    @FXML private TextField searchItemField;
    @FXML private DatePicker searchDatePicker;

    private ObservableList<Donation> donationList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("donationId"));
        donorColumn.setCellValueFactory(new PropertyValueFactory<>("donorName"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("donationDate"));
        loadAllDonations();
    }
    
    public void loadDonationsByEmail(String email) {
        donationList.clear();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM donations WHERE donor_email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                donationList.add(new Donation(
                    rs.getInt("donation_id"),
                    rs.getString("donor_name"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getString("donation_date")
                ));
            }

            donationTable.setItems(donationList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadAllDonations() {
        donationList.clear();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM donations";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                donationList.add(new Donation(
                    rs.getInt("donation_id"),
                    rs.getString("donor_name"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getString("donation_date")
                ));
            }

            donationTable.setItems(donationList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String itemSearch = searchItemField.getText().trim();
        LocalDate date = searchDatePicker.getValue();

        donationList.clear();
        try (Connection conn = DBUtil.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM donations WHERE 1=1");
            if (!itemSearch.isEmpty()) {
                sql.append(" AND item_name LIKE ?");
            }
            if (date != null) {
                sql.append(" AND donation_date = ?");
            }

            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            int index = 1;
            if (!itemSearch.isEmpty()) {
                stmt.setString(index++, "%" + itemSearch + "%");
            }
            if (date != null) {
                stmt.setString(index, date.toString());
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                donationList.add(new Donation(
                    rs.getInt("donation_id"),
                    rs.getString("donor_name"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getString("donation_date")
                ));
            }

            donationTable.setItems(donationList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleDeleteDonation() {
        Donation selected = donationTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a donation to delete.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Are you sure you want to delete this donation?");
        confirmAlert.setContentText("Donation ID: " + selected.getDonationId());

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try (Connection conn = DBUtil.getConnection()) {
                    String sql = "DELETE FROM donations WHERE donation_id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, selected.getDonationId());
                    stmt.executeUpdate();

                    showAlert(Alert.AlertType.INFORMATION, "Success", "Donation deleted successfully.");
                    loadAllDonations();

                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete donation.");
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
