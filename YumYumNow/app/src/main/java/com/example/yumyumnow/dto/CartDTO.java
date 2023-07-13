package com.example.yumyumnow.dto;

@SuppressWarnings("unused")
public class CartDTO {

    private int userId;
    private ProductDTO product;
    private int quantity;

    public CartDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
