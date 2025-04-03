package com.fooddonation.controller;

import com.fooddonation.model.User;
import com.fooddonation.utils.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class UserManagementController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, String> roleColumn;
    @FXML private TableColumn<User, String> statusColumn;
    @FXML private ComboBox<String> statusBox;


    @FXML private ComboBox<String> roleBox;
    @FXML private Label messageLabel;

    private final ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cell -> cell.getValue().userIdProperty().asObject());
        nameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        emailColumn.setCellValueFactory(cell -> cell.getValue().emailProperty());
        roleColumn.setCellValueFactory(cell -> cell.getValue().roleProperty());
        statusColumn.setCellValueFactory(cell -> cell.getValue().statusProperty());

        roleBox.getItems().addAll("Donor", "CharityStaff", "Admin");
        statusBox.getItems().addAll("Pending", "Approved", "Rejected");
        loadUsers();
    }

    private void loadUsers() {
        userList.clear();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT user_id, name, email, role, status FROM users";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userList.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("status")
                ));
            }
            userTable.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateRole() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        String newRole = roleBox.getValue();
        if (selectedUser == null || newRole == null) {
            messageLabel.setText("Select a user and role.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE users SET role = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newRole);
            stmt.setInt(2, selectedUser.getUserId());
            stmt.executeUpdate();
            messageLabel.setText("Role updated!");
            loadUsers();
        } catch (SQLException e) {
            messageLabel.setText("Failed to update role.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            messageLabel.setText("Select a user to delete.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selectedUser.getUserId());
            stmt.executeUpdate();
            messageLabel.setText("User deleted.");
            loadUsers();
        } catch (SQLException e) {
            messageLabel.setText("Failed to delete user.");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleUpdateStatus() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        String newStatus = statusBox.getValue();

        if (selectedUser == null || newStatus == null) {
            messageLabel.setText("Select a user and status.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE users SET Status = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newStatus);
            stmt.setInt(2, selectedUser.getUserId());
            stmt.executeUpdate();
            messageLabel.setText("Status updated!");
            loadUsers(); // refresh table
        } catch (SQLException e) {
            messageLabel.setText("Failed to update status.");
            e.printStackTrace();
        }
    }

}
