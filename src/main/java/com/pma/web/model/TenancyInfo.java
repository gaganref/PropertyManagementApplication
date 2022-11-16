package com.pma.web.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tenancy_info")
public class TenancyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenancy_info_id")
    private long tenancyInfoID;

    @Column(name = "house")
    private Integer house;

    @Column(name = "tenant")
    private Integer tenant;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
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