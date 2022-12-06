package com.pma.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id")
    private long tenantId;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="flatNo", column=@Column(name="flat_no")),
            @AttributeOverride(name="houseNo", column=@Column(name="house_no")),
            @AttributeOverride(name="street", column=@Column(name="street")),
            @AttributeOverride(name="city", column=@Column(name="city")),
            @AttributeOverride(name="postcode", column=@Column(name="postcode")),
    })
    @NotNull(message = "Address cannot be null")
    @Valid
    private Address previousAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tenancy_info", referencedColumnName = "tenancy_info_id")
    @JsonIgnoreProperties("tenant")
    private TenancyInfo tenancyInfo;
    
    public Tenant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tenant(String name, String emailId,
			String phoneNo,Address previousAddress) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.previousAddress = previousAddress;
	}

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantID) {
        this.tenantId = tenantID;
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

    public void setEmailId(String emailID) {
        this.emailId = emailID;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNO) {
        this.phoneNo = phoneNO;
    }

    public Address getPreviousAddress() {
        return previousAddress;
    }

    public void setPreviousAddress(Address previousAddress) {
        this.previousAddress = previousAddress;
    }

    public TenancyInfo getTenancyInfo() {
        return tenancyInfo;
    }

    public void setTenancyInfo(TenancyInfo tenancyInfo) {
        this.tenancyInfo = tenancyInfo;
    }

    public String toString(){
        String outString = "";
        outString += "| Tenant with ID " + tenantId + " |";
        outString += "| Name: " + name + " |";
        outString += "| Email ID: " + emailId + " |";
        outString += "| Phone NO: " + phoneNo + " |";
        return outString;
    }
}
