package com.pma.web.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TenancyInfo")
public class TenancyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TenancyInfo_ID")
    private long tenancyInfoID;

    @Column(name = "House")
    private Integer house;

    @Column(name = "Tenant")
    private Integer tenant;

    @Column(name = "Start_Date")
    private LocalDate startDate;

    @Column(name = "End_Date")
    private LocalDate endDate;

    public long getTenancyInfoID() {
        return tenancyInfoID;
    }

    public void setTenancyInfoID(long tenancyInfoID) {
        this.tenancyInfoID = tenancyInfoID;
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public Integer getTenant() {
        return tenant;
    }

    public void setTenant(Integer tenant) {
        this.tenant = tenant;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String toString(){
        String outString = "";
        outString += "--- Info of Tenancy with ID " + tenancyInfoID + " ---\n";
        outString += "Start Date: " + startDate.toString() + "\n";
        outString += "End Date: " + endDate.toString() + "\n";
        outString += "--- --- ---\n";
        return outString;
    }
}