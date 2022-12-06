package com.pma.web.service;

import com.pma.web.exception.ModelAddException;
import com.pma.web.exception.ModelEmptyListException;
import com.pma.web.exception.ModelNotFoundException;
import com.pma.web.exception.ModelUpdateException;
import com.pma.web.model.House;
import com.pma.web.model.TenancyInfo;
import com.pma.web.repository.TenancyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TenancyInfoServiceImpl implements TenancyInfoService {

    @Autowired
    private TenancyInfoRepository tenancyInfoRepository;

    @Override
    public TenancyInfo addTenancyInfo(TenancyInfo tenancyInfo) {
        try {
            return tenancyInfoRepository.save(tenancyInfo);
        } catch (Exception e) {
            throw new ModelAddException("Couldn't add.html the model TenancyInfo, please add.html the proper details");
        }
    }

    @Override
    public TenancyInfo getTenancyInfo(long id) {
        try {
            Optional<TenancyInfo> outTenancyInfo = tenancyInfoRepository.findTenancyInfoByTenancyInfoID(id);
            if(outTenancyInfo.isPresent()){
                return outTenancyInfo.get();
            }
            else{
                throw new ModelNotFoundException("Couldn't find tenancy info of id: " + id);
            }
        } catch (Exception e) {
            throw new ModelNotFoundException("Couldn't find tenancy info of id: " + id);
        }
    }

    @Override
    public void removeTenancyInfo(long id) {
        try {
            Optional<TenancyInfo> outTenancyInfo = tenancyInfoRepository.findTenancyInfoByTenancyInfoID(id);
            if(outTenancyInfo.isPresent()){
                tenancyInfoRepository.delete(outTenancyInfo.get());
            }
            else{
                throw new ModelNotFoundException("Couldn't find tenancy info of id: " + id);
            }
        } catch (Exception e) {
            throw new ModelNotFoundException("Couldn't remove tenancy info of id: " + id + " as it is not present");
        }
    }

    @Override
    public TenancyInfo updateTenancyInfo(long id, TenancyInfo tenancyInfo) {
        try {
            Optional<TenancyInfo> outTenancyInfo = tenancyInfoRepository.findTenancyInfoByTenancyInfoID(id);
            if(outTenancyInfo.isPresent()){
                TenancyInfo tenancyInfoToUpdate = outTenancyInfo.get();

                tenancyInfoToUpdate.setHouse(tenancyInfo.getHouse());
                tenancyInfoToUpdate.setStartDate(tenancyInfo.getStartDate());
                tenancyInfoToUpdate.setEndDate(tenancyInfo.getEndDate());
                return tenancyInfoToUpdate;
            }
            else{
                throw new ModelUpdateException("Couldn't update house of id: " + id);
            }
        } catch (Exception e) {
            throw new ModelUpdateException("Couldn't update house of id: " + id + " as it is not present");
        }
    }

    @Override
    public List<TenancyInfo> getAllTenancyInfos() {
        try {
            return tenancyInfoRepository.findAll();
        } catch (Exception e) {
            throw new ModelEmptyListException("Error retrieving tenancies... please try again");
        }
    }

    @Override
    public List<TenancyInfo> getTenancyInfoByHouse(long houseID) {
//        try {
//            List<TenancyInfo> outTenancyInfo = new ArrayList<TenancyInfo>();
//            List<TenancyInfo> tenancyInfoList = getAllTenancyInfos();
//
//            for(TenancyInfo tenancyInfo : tenancyInfoList){
//                if(tenancyInfo.getHouse() == houseID){
//                    outTenancyInfo.add(tenancyInfo);
//                }
//            }
//
//            return outTenancyInfo;
//        } catch (Exception e) {
//            throw new ModelEmptyListException("Error retrieving tenancies... please try again");
//        }
        return null;
    }

    @Override
    public List<TenancyInfo> getTenancyInfoByTenant(long tenantID) {
//        try {
//            List<TenancyInfo> outTenancyInfo = new ArrayList<TenancyInfo>();
//            List<TenancyInfo> tenancyInfoList = getAllTenancyInfos();
//
//            for(TenancyInfo tenancyInfo : tenancyInfoList){
//                if(tenancyInfo.getTenant() == tenantID){
//                    outTenancyInfo.add(tenancyInfo);
//                }
//            }
//
//            return outTenancyInfo;
//        } catch (Exception e) {
//            throw new ModelEmptyListException("Error retrieving tenancies... please try again");
//        }
        return null;
    }

    @Override
    public List<TenancyInfo> getTenancyInfoByStartDate(Date start_date, Date end_date) {
        try {
            return tenancyInfoRepository.findTenancyInfoByStartDateBetween(start_date, end_date);
        } catch (Exception e) {
            throw new ModelEmptyListException("Error retrieving tenancies... please try again");
        }
    }

    @Override
    public List<TenancyInfo> getTenancyInfoByEndDate(Date start_date, Date end_date) {
        try {
            return tenancyInfoRepository.findTenancyInfoByEndDateBetween(start_date, end_date);
        } catch (Exception e) {
            throw new ModelEmptyListException("Error retrieving tenancies... please try again");
        }
    }
}