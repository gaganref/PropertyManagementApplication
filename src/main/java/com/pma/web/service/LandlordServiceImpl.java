package com.pma.web.service;

import com.pma.web.model.Landlord;
import com.pma.web.repository.LandlordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LandlordServiceImpl implements LandlordService{

    @Autowired
    private LandlordRepository landlordRepository;

    @Override
    public Landlord addLandlord(Landlord landlord) {
    	return landlordRepository.save(landlord);
    }

    @Override
    public Landlord getLandlord(Integer landlordid) {
    	Optional<Landlord> landlordList = this.landlordRepository.findByLandLordId(landlordid);
		if (landlordList.isPresent()) {
			return landlordList.get();
		} 
		return null;
    }

    @Override
    public void removeLandlord(Integer landlordid) {
    	Optional<Landlord> landlordList = this.landlordRepository.findByLandLordId(landlordid);

		if (landlordList.isPresent()) {
			this.landlordRepository.delete(landlordList.get());
		} 
    }

    @Override
    public Landlord updateLandlord(Integer landlordid, Landlord landlord) {
    	Optional<Landlord> landlordList = this.landlordRepository.findByLandLordId(landlordid);
		if (landlordList.isPresent()) {
			Landlord LandLordUpdate = landlordList.get();
			LandLordUpdate.setName(landlord.getName());
			LandLordUpdate.setEmailId(landlord.getEmailId());
			LandLordUpdate.setPhoneNo(landlord.getPhoneNo());
			landlordRepository.save(LandLordUpdate);
			return LandLordUpdate;
		}
		return null;
    }

    @Override
    public List<Landlord> getAllLandlords() {
    	return (List<Landlord>) landlordRepository.findAll();
    }
}
