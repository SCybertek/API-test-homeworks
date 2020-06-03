package com.UInamesAPI.pojo;

//{
//  "name":"John",
//  "surname":"Doe",
//  "gender":"male",
//  "region":"United States"
//}

import java.util.Objects;

public class RandomUser {

    public RandomUser(){

    }

    private String name;
    private String surname;
    private String gender;
    private String region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RandomUser)) return false;
        RandomUser that = (RandomUser) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSurname(), that.getSurname()) &&
                Objects.equals(getGender(), that.getGender()) &&
                Objects.equals(getRegion(), that.getRegion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getGender(), getRegion());
    }

    @Override
    public String toString() {
        return "RandomUser{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
