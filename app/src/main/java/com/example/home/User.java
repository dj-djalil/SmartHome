package com.example.home;

public class User {
    private String firstname ;
    private String lastname;
    private String password;
    private String uname ;
    int age ;

    public User() {
    }

    public User(String firstname, String lastname, String password, String uname, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.uname = uname;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
