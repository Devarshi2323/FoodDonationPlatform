package com.fooddonation.model;

public class Donation  {
    private int donationId;
    private String donorName;
    private String donorEmail;
    private String itemName;
    private int quantity;
    private String donationDate;

    public Donation(int donationId, String donorName, String itemName, int quantity, String donationDate) {
        this.donationId = donationId;
        this.donorName = donorName;
        this.itemName = itemName;
        this.quantity = quantity;
        this.donationDate = donationDate;
    }

    

    public int getDonationId() { return donationId; }
    public String getDonorName() { return donorName; }
    public String getDonorEmail() { return donorEmail; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public String getDonationDate() { return donationDate; }
}
