package com.gxslash.mesajer;

public class ContactsModel {

    private int id;
    private String name;
    private String phoneNum;

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getPhoneNum(){return phoneNum;}

}