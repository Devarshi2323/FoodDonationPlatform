package com.fooddonation.model;

import javafx.beans.property.*;

public class CharityRequest  {

    private final IntegerProperty requestId;
    private final StringProperty staffEmail;
    private final StringProperty staffName;
    private final StringProperty itemName;
    private final IntegerProperty quantity;
    private final StringProperty requestDate;
    private final StringProperty status;

    public CharityRequest(int requestId, String staffEmail, String staffName,
                          String itemName, int quantity, String requestDate, String status) {
        this.requestId = new SimpleIntegerProperty(requestId);
        this.staffEmail = new SimpleStringProperty(staffEmail);
        this.staffName = new SimpleStringProperty(staffName);
        this.itemName = new SimpleStringProperty(itemName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.requestDate = new SimpleStringProperty(requestDate);
        this.status = new SimpleStringProperty(status);
    }

    public int getRequestId() {
        return requestId.get();
    }

    public IntegerProperty requestIdProperty() {
        return requestId;
    }

    public String getStaffEmail() {
        return staffEmail.get();
    }

    public StringProperty staffEmailProperty() {
        return staffEmail;
    }

    public String getStaffName() {
        return staffName.get();
    }

    public StringProperty staffNameProperty() {
        return staffName;
    }

    public String getItemName() {
        return itemName.get();
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public String getRequestDate() {
        return requestDate.get();
    }

    public StringProperty requestDateProperty() {
        return requestDate;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }
}
