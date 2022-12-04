package com.pma.web.service;

import com.pma.web.model.Landlord;

import java.util.List;

public interface LandlordService {

    public Landlord addLandlord(Landlord landlord);

    public Landlord getLandlord(long landlordid);

    public void removeLandlord(long landlordid);

    public Landlord updateLandlord(long landlordid, Landlord landlord);

    public List<Landlord> getAllLandlords();
}
