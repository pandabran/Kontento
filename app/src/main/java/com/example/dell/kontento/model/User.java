package com.example.dell.kontento.model;

public class User {
    int user_id;
    String firstname;
    String lastname;
    String email;
    String password;
    int age;
    String sex;

    //constructors
    public User(){
    }

    public User(int user_id, String firstname, String lastname,
                String email, String password, int age, String sex){
        this.user_id = user_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.sex = sex;
    }

    public User(String firstname, String lastname, String email,
                String password, int age, String sex){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.age = age;
        this.sex = sex;
    }

    //setters
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setSex(String sex){
        this.sex = sex;
    }

    //getters
    public int getUser_id(){
        return this.user_id;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public int getAge(){
        return this.age;
    }

    public String getSex(){
        return this.sex;
    }

}
