package com.pma.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private long houseID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "landlord", referencedColumnName = "landlord_id")
    @JsonIgnoreProperties("houses")
    private Landlord landlord;

    @Column(name = "no_of_rooms")
    @NotNull(message = "No of Rooms cannot be null.")
    @Min(value = 1)
    @Max(value = 10)
    private Integer noOfRooms;

    @Column(name = "cost")
    @NotNull(message = "Cost cannot be null.")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=4, fraction=2, message = "Numerical value should be less than 4 digits and the fractional value should be less than 2 digits")
    private BigDecimal cost;     // pppm = per person per month i.e rent per person per month

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
    private Address address;

    @OneToOne(mappedBy = "house")
    @Valid
    private TenancyInfo tenancyInfo;


    public House() {
        super();
    }

    public House(long houseID, Landlord landlord, Address address, Integer noOfRooms, BigDecimal cost) {
        super();

        this.houseID = houseID;
        this.landlord = landlord;
        this.address = address;
        this.noOfRooms = noOfRooms;
        this.cost = cost;
    }

    public House(Landlord landlord, Address address, Integer noOfRooms, BigDecimal cost) {
        super();

        this.landlord = landlord;
        this.address = address;
        this.noOfRooms = noOfRooms;
        this.cost = cost;
    }

    public long getHouseID() {
        return houseID;
    }

    public void setHouseID(long houseID) {
        this.houseID = houseID;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(Integer noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public TenancyInfo getTenancyInfo() {
        return tenancyInfo;
    }

    public void setTenancyInfo(TenancyInfo tenancyInfo) {
        this.tenancyInfo = tenancyInfo;
    }

    public String toString(){
        String outString = "";
        outString += "| House Id: " + houseID + " |";
        outString += "| No of Rooms: " + noOfRooms + " |";
        outString += "| Cost per person per month: " + cost + " |";
        return outString;
    }
}
