package com.pma.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "House details cannot be null")
    @Valid
    private House house;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tenancyInfo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnoreProperties("tenancy_info")
    private Set<Tenant> tenant;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public TenancyInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TenancyInfo(House house,Set<Tenant> tenant,Date startDate,Date endDate) {
		super();
		this.house = house;
		this.tenant = tenant;
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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String toString(){
        String outString = "";
        outString += "| Tenancy Id: " + tenancyInfoID + " |";
        outString += "| Start Date: " + startDate.toString() + " |";
        outString += "| End Date: " + endDate.toString() + " |";
        return outString;
    }
}