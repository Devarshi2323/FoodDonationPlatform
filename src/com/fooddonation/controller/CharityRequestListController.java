package com.fooddonation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.fooddonation.model.CharityRequest;
import com.fooddonation.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CharityRequestListController {

    @FXML private TableView<CharityRequest> requestTable;
    @FXML private TableColumn<CharityRequest, Integer> idColumn;
    @FXML private TableColumn<CharityRequest, String> emailColumn;
    @FXML private TableColumn<CharityRequest, String> nameColumn;
    @FXML private TableColumn<CharityRequest, String> itemColumn;
    @FXML private TableColumn<CharityRequest, Integer> quantityColumn;
    @FXML private TableColumn<CharityRequest, String> dateColumn;
    @FXML private TableColumn<CharityRequest, String> statusColumn;

    @FXML private ComboBox<String> statusBox;
    @FXML private Label messageLabel;

    private ObservableList<CharityRequest> requestList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cell -> cell.getValue().requestIdProperty().asObject());
        emailColumn.setCellValueFactory(cell -> cell.getValue().staffEmailProperty());
        nameColumn.setCellValueFactory(cell -> cell.getValue().staffNameProperty());
        itemColumn.setCellValueFactory(cell -> cell.getValue().itemNameProperty());
        quantityColumn.setCellValueFactory(cell -> cell.getValue().quantityProperty().asObject());
        dateColumn.setCellValueFactory(cell -> cell.getValue().requestDateProperty());
        statusColumn.setCellValueFactory(cell -> cell.getValue().statusProperty());

        statusBox.getItems().addAll("Pending", "Approved", "Rejected");
        loadCharityRequests();
    }

    public void loadCharityRequests() {
        requestList.clear();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT cr.request_id, cr.staff_email, u.name AS staff_name,
                       cr.item_name, cr.quantity, cr.request_date, cr.status
                FROM charity_requests cr
                JOIN users u ON cr.staff_email = u.email
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                requestList.add(new CharityRequest(
                    rs.getInt("request_id"),
                    rs.getString("staff_email"),
                    rs.getString("staff_name"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getString("request_date"),
                    rs.getString("status")
                ));
            }

            requestTable.setItems(requestList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateStatus() {
        CharityRequest selected = requestTable.getSelectionModel().getSelectedItem();
        String newStatus = statusBox.getValue();

        if (selected == null || newStatus == null) {
            messageLabel.setText("Please select a request and status.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE charity_requests SET status = ? WHERE request_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, selected.getRequestId());

            stmt.executeUpdate();
            messageLabel.setText("Status updated!");

            loadCharityRequests(); 

        } catch (Exception e) {
            messageLabel.setText("Update failed.");
            e.printStackTrace();
        }
    }
}
