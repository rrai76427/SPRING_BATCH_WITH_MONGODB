package com.mongo.mongo.model;


import wiremock.com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

    private String itemName;
    private int itemQuantity;

    // Default constructor (required for Jackson to deserialize the object)
    public Item() {
    }

    // Constructor with parameters (optional)
    public Item(String itemName, int itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    // Getters and setters
    @JsonProperty("item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @JsonProperty("item_quantity")
    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemQuantity=" + itemQuantity +
                '}';
    }
}
