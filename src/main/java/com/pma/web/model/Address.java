package com.pma.web.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Embeddable
public class Address {


    @Column(name = "flat_no", table = "Address")
	@Min(value = 1)
    @Max(value = 1200)
	private Integer flatNo;

    @Column(name = "house_no", table = "Address")
	@NotNull(message = "House Number cannot be null.")
	@Min(value = 1)
	@Max(value = 3000)
    private Integer houseNo;

    @Column(name = "street", table = "Address")
	@NotNull(message = "Street cannot be null.")
	@Size(min = 5, max = 250)
    private String street;

    @Column(name = "city", table = "Address")
	@NotNull(message = "City cannot be null.")
	@Pattern(regexp = "^(?:[A-Za-z]{2,}(?:(\\.\\s|'s\\s|\\s?-\\s?|\\s)?(?=[A-Za-z]+))){1,2}(?:[A-Za-z]+)?$", message = "Enter a valid city name")
    private String city;

    @Column(name = "postcode", table = "Address")
	@NotNull(message = "Post code cannot be null.")
	@Pattern(regexp = "^(([A-Z]{1,2}[0-9][A-Z0-9]?|ASCN|STHL|TDCU|BBND|[BFS]IQQ|PCRN|TKCA) ?[0-9][A-Z]{2}|BFPO ?[0-9]{1,4}|(KY[0-9]|MSR|VG|AI)[ -]?[0-9]{4}|[A-Z]{2} ?[0-9]{2}|GE ?CX|GIR ?0A{2}|SAN ?TA1)$", message = "Enter a valid postcode")
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

	public void setFlatNo(Integer flatNo) {
		this.flatNo = flatNo;
	}

	public Integer getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(Integer houseNo) {
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

	public String toString(){
		String outString = "";
		if(flatNo != null){
			outString += "Flat No: " + flatNo + "\n";
		}
		outString += "House No: " + houseNo + "\n";
		outString += "Street: " + street + "\n";
		outString += "City: " + city + "\n";
		outString += "Postcode: " + postcode + "\n";
		return outString;
	}
	
}