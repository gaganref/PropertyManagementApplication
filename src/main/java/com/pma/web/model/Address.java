package com.pma.web.model;

import javax.persistence.*;

@Entity
@Table(name = "Address")
public class Address {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
    private Integer addressId;

    @Column(name = "flat_no")
    private Integer flatNo;

    @Column(name = "house_no")
    private Integer houseNo;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
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

	public Integer getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(int flatNo) {
		this.flatNo = flatNo;
	}

	public Integer getHouseNo() {
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