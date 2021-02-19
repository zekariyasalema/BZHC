package com.zack.ok.bzhc;


public class User {
    private int id;
    private String fullName, address, city, gender, email; ;

    public User(int id, String sharedPreferencesString, String string, String fullName, String address, String city) {
        this.id = id;
        this.fullName =fullName;
        this.address =address;
        this.city =city;
        this.gender = gender;
        this.email = email;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

