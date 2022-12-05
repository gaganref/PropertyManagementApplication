package com.pma.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Landlord")
public class Landlord {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "landlord_id")
    private long landlordId;

    @Column(name = "name")
    private String name;

    @Column(name = "email_id")
    private String emailid;

    @Column(name = "phone_no")
    private String phoneno;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "landlord", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnoreProperties("landlord")
	private Set<House> houses;

	public Landlord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Landlord(String name, String emailid,
			String phoneno, Set<House> houses) {
		super();
		this.name = name;
		this.emailid = emailid;
		this.phoneno = phoneno;
		this.houses = houses;
	}

	public long getLandlordId() {
		return landlordId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailid;
	}

	public void setEmailId(String emailid) {
		this.emailid = emailid;
	}

	public String getPhoneNo() {
		return phoneno;
	}

	public void setPhoneNo(String phoneno) {
		this.phoneno = phoneno;
	}

	public Set<House> getHouses() {
		return houses;
	}

	public void setHouses(Set<House> houses) {
		this.houses = houses;
	}

	public String toString(){
		String outString = "";
		outString += "--- Info of Landlord with ID " + landlordId + " ---\n";
		outString += "Name: " + name + "\n";
		outString += "Email ID: " + emailid + "\n";
		outString += "Phone No: " + phoneno + "\n";
		outString += "--- --- ---\n";
		return outString;
	}
}
