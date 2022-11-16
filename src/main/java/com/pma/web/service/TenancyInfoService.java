package com.pma.web.service;

import com.pma.web.model.TenancyInfo;
import com.pma.web.model.Tenant;

import java.time.LocalDate;
import java.util.List;

public interface TenancyInfoService {

    public TenancyInfo addTenancyInfo(TenancyInfo tenancyInfo);

    public TenancyInfo getTenancyInfo(long id);

    public void removeTenancyInfo(long id);

    public TenancyInfo updateTenancyInfo(long id, TenancyInfo tenancyInfo);

    public List<TenancyInfo> getAllTenancyInfos();

    public List<TenancyInfo> getTenancyInfoByHouse(long houseID);

    public List<TenancyInfo> getTenancyInfoByTenant(long tenantID);

    public List<TenancyInfo> getTenancyInfoByStartDate(LocalDate start_date , LocalDate end_date);

    public List<TenancyInfo> getTenancyInfoByEndDate(LocalDate start_date , LocalDate end_date);

}
