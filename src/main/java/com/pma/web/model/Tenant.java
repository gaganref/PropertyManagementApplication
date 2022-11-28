package com.pma.web.model;

import javax.persistence.*;

@Entity
@Table(name = "Tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id")
    private long tenantID;

    @Column(name = "name")
    private String name;

    @Column(name = "email_id")
    private String emailID;

    @Column(name = "phone_no")
    private String phoneNO;

    @Column(name = "previous_address")
    private long previousAddress;
    
    public Tenant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tenant(String name, String emailid,
			String phoneno,long previousAddress) {
		super();
		this.name = name;
		this.emailID = emailid;
		this.phoneNO = phoneno;
		this.previousAddress = previousAddress;
	}

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

    public long getPreviousAddress() {
        return previousAddress;
    }

    public void setPreviousAddress(long previousAddress) {
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
