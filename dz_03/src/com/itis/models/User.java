package com.itis.models;

public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;

    private String email;
    private String group;
    private String city;

    public User(Long id, String firstName, String lastName, Integer age, String email, String group, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.group = group;
        this.city = city;
    }

    public Long getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getGroup() {
        return group;
    }

    public String getEmail() {
        return email;
    }
}