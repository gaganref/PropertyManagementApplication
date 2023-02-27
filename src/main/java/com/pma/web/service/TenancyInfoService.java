package com.pma.web.service;

import com.pma.web.model.TenancyInfo;

import java.util.List;

public interface TenancyInfoService {

    public void addTenancyInfo(TenancyInfo tenancyInfo);

    public TenancyInfo getTenancyInfo(long id);

    public void removeTenancyInfo(long id);

    public void updateTenancyInfo(long id, TenancyInfo tenancyInfo);

    public List<TenancyInfo> getAllTenancyInfos();

}
