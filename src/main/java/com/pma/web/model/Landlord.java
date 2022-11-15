package com.pma.web.model;

import javax.persistence.*;

@Entity
@Table(name = "Landlord")
public class Landlord {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Landlord_ID")
    private Integer landlordId;

    @Column(name = "name")
    private String name;

    @Column(name = "email_id")
    private String emailid;

    @Column(name = "phone_no")
    private String phoneno;

	public Landlord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Landlord(String name, String emailid,
			String phoneno) {
		super();
		this.name = name;
		this.emailid = emailid;
		this.phoneno = phoneno;
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
}
