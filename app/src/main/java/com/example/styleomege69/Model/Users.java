package com.example.styleomege69.Model;

public class Users {
    private String Name, Password, Phone, Image, Address;

    public Users()
    {

    }

    public Users(String name, String password, String phone, String image, String address) {
        Name = name;
        Password = password;
        Phone = phone;
        Image = image;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}

