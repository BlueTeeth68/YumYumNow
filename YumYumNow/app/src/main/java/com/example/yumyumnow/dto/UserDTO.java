package com.example.yumyumnow.dto;

@SuppressWarnings("unused")
public class UserDTO {

    private int id;
    private String username;
    private String fullName;
    private int avatar;

    public UserDTO(String username, String fullName, int avatar) {
        this.username = username;
        this.fullName = fullName;
        this.avatar = avatar;
    }

    public UserDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
