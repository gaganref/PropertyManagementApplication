package com.pma.web.service;

import com.pma.web.model.TenancyInfo;
import com.pma.web.model.Tenant;

import java.util.List;

public interface TenancyInfoService {

    public TenancyInfo addTenancyInfo(TenancyInfo tenancyInfo);

    public TenancyInfo getTenancyInfo(long id);

    public void removeTenancyInfo(long id);

    public void updateTenancyInfo(long id, TenancyInfo tenancyInfo);

    public List<TenancyInfo> getAllTenancyInfos();

    public List<TenancyInfo> getTenancyInfoByHouse(long houseID);

    public List<TenancyInfo> getTenancyInfoByTenant(long tenantID);

}
