package com.harryPotterAPI.pojos;
//{
//        "_id": "5a0fa4daae5bc100213c232e",
//        "name": "Hannah Abbott",
//        "role": "student",
//        "house": "Hufflepuff",
//        "school": "Hogwarts School of Witchcraft and Wizardry",
//        "__v": 0,
//        "ministryOfMagic": false,
//        "orderOfThePhoenix": false,
//        "dumbledoresArmy": true,
//        "deathEater": false,
//        "bloodStatus": "half-blood",
//        "species": "human"
//    },

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Character {

    @SerializedName("_id")
    private String id;
    private String name;
    private String role;
    private String house; //not used all the time (?)
    private String school;
    private String alias; //not used all the time
    private String wand;  //not used all the time
    private String boggart; //not used al the time
    private String patronus; //not used all the time
    @SerializedName("__v")
    private int v;
    private boolean ministryOfMagic;
    private boolean orderOfThePhoenix;
    private boolean dumbledoresArmy;
    private boolean deathEater;
    private String bloodStatus;
    private String species;

    public Character() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getWand() {
        return wand;
    }

    public void setWand(String wand) {
        this.wand = wand;
    }

    public String getBoggart() {
        return boggart;
    }

    public void setBoggart(String boggart) {
        this.boggart = boggart;
    }

    public String getPatronus() {
        return patronus;
    }

    public void setPatronus(String patronus) {
        this.patronus = patronus;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public boolean isMinistryOfMagic() {
        return ministryOfMagic;
    }

    public void setMinistryOfMagic(boolean ministryOfMagic) {
        this.ministryOfMagic = ministryOfMagic;
    }

    public boolean isOrderOfThePhoenix() {
        return orderOfThePhoenix;
    }

    public void setOrderOfThePhoenix(boolean orderOfThePhoenix) {
        this.orderOfThePhoenix = orderOfThePhoenix;
    }

    public boolean isDumbledoresArmy() {
        return dumbledoresArmy;
    }

    public void setDumbledoresArmy(boolean dumbledoresArmy) {
        this.dumbledoresArmy = dumbledoresArmy;
    }

    public boolean isDeathEater() {
        return deathEater;
    }

    public void setDeathEater(boolean deathEater) {
        this.deathEater = deathEater;
    }

    public String getBloodStatus() {
        return bloodStatus;
    }

    public void setBloodStatus(String bloodStatus) {
        this.bloodStatus = bloodStatus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return getV() == character.getV() &&
                isMinistryOfMagic() == character.isMinistryOfMagic() &&
                isOrderOfThePhoenix() == character.isOrderOfThePhoenix() &&
                isDumbledoresArmy() == character.isDumbledoresArmy() &&
                isDeathEater() == character.isDeathEater() &&
                Objects.equals(getId(), character.getId()) &&
                Objects.equals(getName(), character.getName()) &&
                Objects.equals(getRole(), character.getRole()) &&
                Objects.equals(getHouse(), character.getHouse()) &&
                Objects.equals(getSchool(), character.getSchool()) &&
                Objects.equals(getAlias(), character.getAlias()) &&
                Objects.equals(getWand(), character.getWand()) &&
                Objects.equals(getBoggart(), character.getBoggart()) &&
                Objects.equals(getPatronus(), character.getPatronus()) &&
                Objects.equals(getBloodStatus(), character.getBloodStatus()) &&
                Objects.equals(getSpecies(), character.getSpecies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRole(), getHouse(), getSchool(), getAlias(), getWand(), getBoggart(), getPatronus(), getV(), isMinistryOfMagic(), isOrderOfThePhoenix(), isDumbledoresArmy(), isDeathEater(), getBloodStatus(), getSpecies());
    }

    @Override
    public String toString() {
        return "Character{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", house='" + house + '\'' +
                ", school='" + school + '\'' +
                ", alias='" + alias + '\'' +
                ", wand='" + wand + '\'' +
                ", boggart='" + boggart + '\'' +
                ", patronus='" + patronus + '\'' +
                ", v=" + v +
                ", ministryOfMagic=" + ministryOfMagic +
                ", orderOfThePhoenix=" + orderOfThePhoenix +
                ", dumbledoresArmy=" + dumbledoresArmy +
                ", deathEater=" + deathEater +
                ", bloodStatus='" + bloodStatus + '\'' +
                ", species='" + species + '\'' +
                '}';
    }
}
