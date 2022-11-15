package com.pma.web.service;

import com.pma.web.model.Landlord;

import java.util.List;

public interface LandlordService {

    public Landlord addLandlord(Landlord landlord);

    public Landlord getLandlord(Integer landlordid);

    public void removeLandlord(Integer landlordid);

    public Landlord updateLandlord(Integer landlordid, Landlord landlord);

    public List<Landlord> getAllLandlords();
}
