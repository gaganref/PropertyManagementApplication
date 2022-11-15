package com.pma.web.service;

import com.pma.web.model.TenancyInfo;
import com.pma.web.repository.TenancyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TenancyInfoServiceImpl implements TenancyInfoService {

    @Autowired
    private TenancyInfoRepository tenancyInfoRepository;

    @Override
    public TenancyInfo addTenancyInfo(TenancyInfo tenancyInfo) {
        return null;
    }

    @Override
    public TenancyInfo getTenancyInfo(long id) {
        return null;
    }

    @Override
    public void removeTenancyInfo(long id) {

    }

    @Override
    public void updateTenancyInfo(long id, TenancyInfo tenancyInfo) {

    }

    @Override
    public List<TenancyInfo> getAllTenancyInfos() {
        return null;
    }

    @Override
    public List<TenancyInfo> getTenancyInfoByHouse(long houseID) {
        return null;
    }

    @Override
    public List<TenancyInfo> getTenancyInfoByTenant(long tenantID) {
        return null;
    }
}
