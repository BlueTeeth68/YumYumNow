package com.example.yumyumnow.dto;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class BillDTO {

    private int id;
    private int userId;
    private double totalPrice;
    private String createDate;
    private List<BillDetailDTO> billDetails = new ArrayList<>();

    public BillDTO(int userId, int totalPrice, String createDate) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
    }

    public BillDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<BillDetailDTO> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetailDTO> billDetails) {
        this.billDetails = billDetails;
    }
}
