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
import org.junit.jupiter.api.BeforeAll;

import java.util.Objects;



public class Character {

   // @JsonIgnoreProperties(ignoreUnknown = true)
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
    private Boolean ministryOfMagic;
    private Boolean orderOfThePhoenix;
    private Boolean dumbledoresArmy;
    private Boolean deathEater;
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

    public Boolean getMinistryOfMagic() {
        return ministryOfMagic;
    }

    public void setMinistryOfMagic(Boolean ministryOfMagic) {
        this.ministryOfMagic = ministryOfMagic;
    }

    public Boolean getOrderOfThePhoenix() {
        return orderOfThePhoenix;
    }

    public void setOrderOfThePhoenix(Boolean orderOfThePhoenix) {
        this.orderOfThePhoenix = orderOfThePhoenix;
    }

    public Boolean getDumbledoresArmy() {
        return dumbledoresArmy;
    }

    public void setDumbledoresArmy(Boolean dumbledoresArmy) {
        this.dumbledoresArmy = dumbledoresArmy;
    }

    public Boolean getDeathEater() {
        return deathEater;
    }

    public void setDeathEater(Boolean deathEater) {
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
