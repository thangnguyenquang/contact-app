package com.thangnq.contactapp;

import java.io.Serializable;

public class Contact implements Serializable {
    private String Name;
    private String PhoneNumber;

    public Contact(){

    }

    public Contact(String name, String phoneNumber) {
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
