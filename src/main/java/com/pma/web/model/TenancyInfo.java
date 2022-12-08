package com.pma.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tenancy_info")
public class TenancyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenancy_info_id")
    private long tenancyInfoID;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "house", referencedColumnName = "house_id")
//    @NotNull(message = "House details cannot be null")
    @Valid
    private House house;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tenancyInfo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnoreProperties("tenancy_info")
    private Set<Tenant> tenant;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Start date cannot be null")
    @Valid
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "End date details cannot be null")
    @Valid
    private Date endDate;

    public TenancyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

    public TenancyInfo(long tenancyInfoID, House house, Date startDate,Date endDate) {
        super();
        this.tenancyInfoID = tenancyInfoID;
        this.house = house;
        this.startDate = startDate;
        this.endDate = endDate;
    }

	public TenancyInfo(House house ,Date startDate,Date endDate) {
		super();
		this.house = house;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
    public long getTenancyInfoID() {
        return tenancyInfoID;
    }

    public void setTenancyInfoID(long tenancyInfoID) {
        this.tenancyInfoID = tenancyInfoID;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Set<Tenant> getTenant() {
        return tenant;
    }

    public void setTenant(Set<Tenant> tenant) {
        this.tenant = tenant;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStartDateString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if(endDate != null){
            return dateFormat.format(startDate);
        }
        return "01-01-2021";
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getEndDateString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if(endDate != null){
            return dateFormat.format(endDate);
        }
        return "01-01-2021";
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String toString(){
        String outString = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        outString += "| Tenancy Id: " + tenancyInfoID + " |";
        if(startDate != null){
            outString += "| Start Date: " + dateFormat.format(startDate) + " |";
        }
        if(endDate != null){
            outString += "| End Date: " + dateFormat.format(endDate) + " |";
        }
        return outString;
    }
}