package com.pma.web.service;

import com.pma.web.model.Landlord;
import com.pma.web.repository.LandlordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LandlordServiceImpl implements LandlordService{

    @Autowired
    private LandlordRepository landlordRepository;

    @Override
    public Landlord addLandlord(Landlord landlord) {
        return null;
    }

    @Override
    public Landlord getLandlord(Integer id) {
        return null;
    }

    @Override
    public void removeLandlord(Integer id) {

    }

    @Override
    public void updateLandlord(Integer id, Landlord landlord) {

    }

    @Override
    public List<Landlord> getAllLandlords() {
        return null;
    }
}
