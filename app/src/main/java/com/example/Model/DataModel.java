package com.example.Model;

import java.io.Serializable;

public class DataModel implements Serializable {
    String id;
    String date, name,refno,airline,sector,description ,uid,uplodedby;
    Double debit,cradit,balance;
    String agentName,agentId;

    public DataModel(String id,String description, String date, String name, String refno, String airline, String sector, String uid, String uplodedby, Double debit, Double cradit, Double balance, String agentName, String agentId) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.refno = refno;
        this.airline = airline;
        this.sector = sector;
        this.uid = uid;
        this.uplodedby = uplodedby;
        this.debit = debit;
        this.cradit = cradit;
        this.balance = balance;
        this.agentName = agentName;
        this.agentId = agentId;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataModel() {
    }

    public DataModel(String uplodedby) {
        this.uplodedby = uplodedby;
    }


    public void setUplodedby(String uplodedby) {
        this.uplodedby = uplodedby;
    }

    public String getUplodedby() {
        return uplodedby;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCradit() {
        return cradit;
    }

    public void setCradit(Double cradit) {
        this.cradit = cradit;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", refno='" + refno + '\'' +
                ", airline='" + airline + '\'' +
                ", sector='" + sector + '\'' +
                ", uid='" + uid + '\'' +
                ", uplodedby='" + uplodedby + '\'' +
                ", debit=" + debit +
                ", cradit=" + cradit +
                ", balance=" + balance +
                ", agentName='" + agentName + '\'' +
                ", agentId='" + agentId + '\'' +
                '}';
    }


}
