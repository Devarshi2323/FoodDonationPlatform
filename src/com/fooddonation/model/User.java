package com.fooddonation.model;

import javafx.beans.property.*;

public class User  {
    private final IntegerProperty userId;
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty role;
    private final StringProperty status;

    public User(int userId, String name, String email, String role, String status) {
        this.userId = new SimpleIntegerProperty(userId);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty(role);
        this.status = new SimpleStringProperty(status);
    }

    public int getUserId() { return userId.get(); }
    public String getName() { return name.get(); }
    public String getEmail() { return email.get(); }
    public String getRole() { return role.get(); }
    public String getStatus() { return status.get(); }

    public IntegerProperty userIdProperty() { return userId; }
    public StringProperty nameProperty() { return name; }
    public StringProperty emailProperty() { return email; }
    public StringProperty roleProperty() { return role; }
    public StringProperty statusProperty() { return status; }
}
