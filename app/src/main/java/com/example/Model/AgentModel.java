package com.example.Model;

import java.io.Serializable;

public  class AgentModel implements Serializable {
    String name, email, password, uid;
    int cradit, debit, balance;


    public AgentModel() {
    }

    public AgentModel(String name, String email, String password, String uid, int cradit, int debit, int balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.cradit = cradit;
        this.debit = debit;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getCradit() {
        return cradit;
    }

    public void setCradit(int cradit) {
        this.cradit = cradit;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AgentModel{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", uid='" + uid + '\'' +
                ", cradit=" + cradit +
                ", debit=" + debit +
                ", balance=" + balance +
                '}';
    }
}
