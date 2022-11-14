package com.pma.web.model;

import javax.persistence.*;


@Entity
@Table(name = "Address")
public class Address {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Address_ID")
    private Integer addressId;

    @Column(name = "Flat_NO")
    private Integer flatNo = null;

    @Column(name = "House_NO")
    private Integer houseNo;

    @Column(name = "Street")
    private String street;

    @Column(name = "City")
    private String city;

    @Column(name = "postcode")
    private String postcode;
	
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(int flatNo,int houseNo, String street, String city,
			String postcode) {
		super();
		this.flatNo = flatNo;
		this.houseNo = houseNo;
		this.street = street;
		this.city = city;
		this.postcode = postcode;
	}

	public int getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(int flatNo) {
		this.flatNo = flatNo;
	}

	public int getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(int houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	
}