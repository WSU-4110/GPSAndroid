package com.example.wsugooglemaps;

public class User {
    // User class to store user data for firebase

    // String variables for user data
    public String name, age, email;

    // Empty public constructor to access variables
    public User(){

    }

    // Constructor for arguments
    public User(String Name, String Age, String EmailAddress){
        // initialize variables
        this.name=Name;
        this.age=Age;
        this.email=EmailAddress;
    }

}
