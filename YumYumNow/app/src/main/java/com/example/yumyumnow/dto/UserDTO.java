package com.example.yumyumnow.dto;

@SuppressWarnings("unused")
public class UserDTO {

    private int id;
    private String username;
    private String fullName;
    private String password;
    private int avatar;

    public UserDTO(String username, String fullName, String password, int avatar) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
