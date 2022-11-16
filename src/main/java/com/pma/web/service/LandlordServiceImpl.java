package com.pma.web.service;

import com.pma.web.exception.ModelAddException;
import com.pma.web.exception.ModelEmptyListException;
import com.pma.web.exception.ModelNotFoundException;
import com.pma.web.exception.ModelUpdateException;
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
    	 try {
    		 return landlordRepository.save(landlord);
         } catch (Exception e) {
             throw new ModelAddException("Couldn't add the model Landlord, please add the proper details");
         }
    }

    @Override
    public Landlord getLandlord(Integer landlordid) {
		try {
			Optional<Landlord> landlordList = this.landlordRepository.findByLandlordId(landlordid);
			if (landlordList.isPresent()) {
				return landlordList.get();
			} 
            else{
                throw new ModelNotFoundException("Couldn't find the Landlord of id: " + landlordid);
            }
        } catch (Exception e) {
            throw new ModelNotFoundException("Couldn't find the Landlord of id: " + landlordid);
        }
    }

    @Override
    public void removeLandlord(Integer landlordid) {
    	
		try {
			Optional<Landlord> landlordList = this.landlordRepository.findByLandlordId(landlordid);
			if (landlordList.isPresent()) {
				this.landlordRepository.delete(landlordList.get());
			} 
            else{
                throw new ModelNotFoundException("Couldn't find the Landlord of id: " + landlordid);
            }
        } catch (Exception e) {
            throw new ModelNotFoundException("Couldn't remove Landlord of id: " + landlordid + " as it is not present");
        }
    }

    @Override
    public Landlord updateLandlord(Integer landlordid, Landlord landlord) {
		try {
			Optional<Landlord> landlordList = this.landlordRepository.findByLandlordId(landlordid);
			if (landlordList.isPresent()) {
				Landlord LandLordUpdate = landlordList.get();
				LandLordUpdate.setName(landlord.getName());
				LandLordUpdate.setEmailId(landlord.getEmailId());
				LandLordUpdate.setPhoneNo(landlord.getPhoneNo());
				landlordRepository.save(LandLordUpdate);
				return LandLordUpdate;
			}
            else{
                throw new ModelUpdateException("Couldn't update Landlord of id: " + landlordid);
            }
        } catch (Exception e) {
            throw new ModelUpdateException("Couldn't update Landlord of id: " + landlordid + " as it is not present");
        }
    }

    @Override
    public List<Landlord> getAllLandlords() {
    	try {
    		return (List<Landlord>) landlordRepository.findAll();
        } catch (Exception e) {
            throw new ModelEmptyListException("Error retrieving houses... please try again");
        }
    }
}
