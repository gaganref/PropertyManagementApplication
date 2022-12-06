package com.pma.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;


@Entity
@Table(name = "Landlord")
public class Landlord {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "landlord_id")
    private long landlordId;

    @Column(name = "name")
	@NotNull(message = "Landlord name cannot be null.")
	@Pattern(regexp = "^[A-Z](?=.{1,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$", message = "Enter a valid name, first letter should be capital for all (first, middle, last names)")
    private String name;

    @Column(name = "email_id")
	@NotNull(message = "email id cannot be null.")
	@Email(message = "Email should be valid")
    private String emailId;

    @Column(name = "phone_no")
	@NotNull(message = "phone number cannot be null.")
	@Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
					+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
					+ "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$",
					message = "Enter a valid phone number")
    private String phoneNo;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "landlord", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnoreProperties("landlord")
	private Set<House> houses;

	public Landlord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Landlord(String name, String emailId,
					String phoneNo, Set<House> houses) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.houses = houses;
	}

	public long getLandlordId() {
		return landlordId;
	}

	public void setLandlordId(long landlordId) {
		this.landlordId = landlordId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailid) {
		this.emailId = emailid;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneno) {
		this.phoneNo = phoneno;
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
		outString += "Email ID: " + emailId + "\n";
		outString += "Phone No: " + phoneNo + "\n";
		outString += "--- --- ---\n";
		return outString;
	}
}
