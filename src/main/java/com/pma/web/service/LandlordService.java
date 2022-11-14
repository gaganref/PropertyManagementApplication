package com.pma.web.service;

import com.pma.web.model.Landlord;

import java.util.List;

public interface LandlordService {

    public Landlord addLandlord(Landlord landlord);

    public Landlord getLandlord(Integer id);

    public void removeLandlord(Integer id);

    public void updateLandlord(Integer id, Landlord landlord);

    public List<Landlord> getAllLandlords();
}
