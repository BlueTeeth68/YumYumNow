package com.example.yumyumnow.dto;

@SuppressWarnings("unused")
public class CartProductDTO {

    private int productId;
    private int quantity;

    public CartProductDTO(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartProductDTO() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
