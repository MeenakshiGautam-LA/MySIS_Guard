package com.sisindia.mysis;

public class HelpContactEntity
{
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    String designation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    String number;
    public HelpContactEntity(String designation, String name, String number) {
this.designation=designation;
this.number=number;
this.name=name;
    }





}
