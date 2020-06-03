package com.harryPotterAPI.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

//            "_id": "5a1239130f5ae10021650daa",
//            "name": "Ginevra Weasley"
//        }
public class Members {

    public Members (){

    }
    @SerializedName("_id")
    private String id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Members)) return false;
        Members members = (Members) o;
        return Objects.equals(getId(), members.getId()) &&
                Objects.equals(getName(), members.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Members{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
