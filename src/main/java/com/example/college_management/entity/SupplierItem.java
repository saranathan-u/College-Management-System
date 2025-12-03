package com.example.college_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "supplier_items")
public class SupplierItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private String description;
    private double price;

    // Add this field to link item to a supplier
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public SupplierItem() {}

    public SupplierItem(String itemName, String description, double price) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //  Supplier getter and setter
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "SupplierItem{id=" + id + ", itemName='" + itemName + "'}";
    }
}
