package com.pma.web.model;

import javax.persistence.*;

@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private long houseID;

    @Column(name = "landlord")
    private Integer landlord;

    @Column(name = "address")
    private Integer address;

    @Column(name = "no_of_rooms")
    private Integer noOfRooms;

    @Column(name = "pppm")
    private float pppm;     // pppm = per person per month i.e rent per person per month


    public House() {
        super();
    }

    public House(Integer landlord, Integer address, Integer noOfRooms, float pppm) {
        super();

        this.landlord = landlord;
        this.address = address;
        this.noOfRooms = noOfRooms;
        this.pppm = pppm;
    }

    public long getHouseID() {
        return houseID;
    }

    public void setHouseID(long houseID) {
        this.houseID = houseID;
    }

    public Integer getLandlord() {
        return landlord;
    }

    public void setLandlord(Integer landlord) {
        this.landlord = landlord;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Integer getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(Integer noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public float get_pppm() {
        return pppm;
    }

    public void set_pppm(float pppm) {
        this.pppm = pppm;
    }

    public String toString(){
        String outString = "";
        outString += "--- Info of House with ID " + houseID + " ---\n";
        outString += "No of Rooms: " + noOfRooms + "\n";
        outString += "Cost per person per month: " + pppm + "\n";
        outString += "--- --- ---\n";
        return outString;
    }
}
