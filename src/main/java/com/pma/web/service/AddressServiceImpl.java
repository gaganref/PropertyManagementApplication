package com.pma.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pma.web.exception.ModelAddException;
import com.pma.web.exception.ModelEmptyListException;
import com.pma.web.exception.ModelNotFoundException;
import com.pma.web.exception.ModelUpdateException;
import com.pma.web.model.Address;
import com.pma.web.repository.AddressRepository;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public Address addAddress(Address address) {
		try {
			return addressRepository.save(address);
		}
		catch (Exception e) {
            throw new ModelAddException("Couldn't add the model Address, please add the proper details");
        }
	}

	@Override
	public void removeAddress(int addressId) {
		try {
		// TODO Auto-generated method stub
			Optional<Address> addressList = this.addressRepository.findByAddressId(addressId);

			if (addressList.isPresent()) {
				this.addressRepository.delete(addressList.get());
			} 
			else{
				throw new ModelNotFoundException("Couldn't find the Address of id: " + addressId);
			}
		} catch (Exception e) {
            throw new ModelNotFoundException("Couldn't remove Address of id: " + addressId + " as it is not present");
        }
	}

	@Override
	public List<Address> getAllAddress(String postcode) {
		try {
			return (List<Address>) addressRepository.findByPostcode(postcode);
		} catch (Exception e) {
            throw new ModelEmptyListException("Error retrieving Address... please try again");
        }
	}

	@Override
	public Address getAddress(int addressId) {
		try {
			Optional<Address> addressList = this.addressRepository.findByAddressId(addressId);
			if (addressList.isPresent()) {
				return addressList.get();
			} 
			else{
                throw new ModelNotFoundException("Couldn't find the Address of id: " + addressId);
            }
        } catch (Exception e) {
            throw new ModelNotFoundException("Couldn't find the Address of id: " + addressId);
        }
	}

	@Override
	public Address updateAddress(Address address, int addressId) {
		try {
			Optional<Address> addresslist = this.addressRepository.findByAddressId(addressId);
			if (addresslist.isPresent()) {
				Address addressUpdate = addresslist.get();
				addressUpdate.setFlatNo(address.getFlatNo());
				addressUpdate.setHouseNo(address.getHouseNo());
				addressUpdate.setStreet(address.getStreet());
				addressUpdate.setCity(address.getCity());
				addressUpdate.setPostcode(address.getPostcode());
				addressRepository.save(addressUpdate);
				return addressUpdate;
			}
			else{
                throw new ModelUpdateException("Couldn't update Address of id: " + addressId);
            }
        } catch (Exception e) {
            throw new ModelUpdateException("Couldn't update Address of id: " + addressId + " as it is not present");
        }
	} 
}
