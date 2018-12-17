
package com.ahmednmahran.egoshopping.model;


import com.google.gson.annotations.SerializedName;

public class App {

    @SerializedName("Category")
    private String category;
    @SerializedName("Id")
    private Long id;
    @SerializedName("ImageName")
    private String imageName;
    @SerializedName("Name")
    private String name;
    @SerializedName("Price")
    private double price;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
