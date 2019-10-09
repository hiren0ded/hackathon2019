package com.example.demo;

public class SellerData {
    String ImageUrl;
    String Product;
    String Credit;
    String Email;
    String MRP;
    String tableid;
    String customerId;

    public SellerData(String imageUrl, String product, String credit, String email, String mrp, String id,
                      String customerId) {
        this.ImageUrl = imageUrl;
        this.Product = product;
        this.Credit = credit;
        this.Email = email;
        this.MRP = mrp;
        this.tableid = id;
        this.customerId = customerId;
    }
}
