package com.example.salondeniz.Model;

public class User {

    public User(String telNo, String userID, String nameSurname, String password, String role) {
        this.telNo = telNo;
        this.userID = userID;
        this.nameSurname = nameSurname;
        this.password = password;
        this.role = role;
    }

    private String telNo;
    private String userID;
    private String nameSurname;
    private String password;
    private String role;

    public static String uID;
    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
