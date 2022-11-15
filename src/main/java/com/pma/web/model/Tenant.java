package com.pma.web.model;

import javax.persistence.*;

@Entity
@Table(name = "Tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tenant_ID")
    private long tenantID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email_ID")
    private String emailID;

    @Column(name = "Phone_NO")
    private String phoneNO;

    @Column(name = "Previous_Address")
    private String previousAddress;

    public long getTenantID() {
        return tenantID;
    }

    public void setTenantID(long tenantID) {
        this.tenantID = tenantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }

    public String getPreviousAddress() {
        return previousAddress;
    }

    public void setPreviousAddress(String previousAddress) {
        this.previousAddress = previousAddress;
    }

    public String toString(){
        String outString = "";
        outString += "--- Info of Tenant with ID " + tenantID + " ---\n";
        outString += "Name: " + name + "\n";
        outString += "Email ID: " + emailID + "\n";
        outString += "Phone NO: " + phoneNO + "\n";
        outString += "--- --- ---\n";
        return outString;
    }
}
